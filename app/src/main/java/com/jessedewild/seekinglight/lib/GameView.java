package com.jessedewild.seekinglight.lib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.jessedewild.seekinglight.entities.Scroller;
import com.jessedewild.seekinglight.entities.characters.Seeker;

public class GameView extends View implements View.OnTouchListener {

    // While in an `onDraw` method, this references the `Canvas` to be painted to.
    private Canvas canvas;

    // The `GameModel` we're currently showing and relaying touch events to.
    private GameModel gameModel;

    // The `Matrix` representing scale and translate operations to apply on the canvas
    // in order to fit the virtual screen on the actual screen.
    private Matrix viewMatrix;

    // The clipping rectangle, such that drawing outside of the virtual screen is not visible.
    // Set to null in case clipping is not required, when the virtual screen fills the actual
    // screen.
    private RectF clipRect;

    // The inverse of `viewMatrix`, allowing us to map touch event coordinates to virtual
    // screen coordinates.
    private Matrix touchMatrix;

    // These are used to log the FPS counter every 3s.
    private int frameCount;
    private double lastFpsLogTime;

    // Used by `drawBitmap` to paint translucent images.
    private Paint alphaPaint = new Paint();

    // A canvas `Paint` to fill the black bars outside the virtual screen with.
    private Paint blackPaint = new Paint();

    // The time in ms at which the tick() methods were last invoked.
    // When set to 0, the game is paused.
    private transient double lastTickTime = 0;

    // `true` after `start()` has been called.
    private boolean started = false;


    public GameView(Context context) {
        super(context);
        init();
    }

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOnTouchListener(this);
        blackPaint.setColor(Color.BLACK);
        blackPaint.setStyle(Paint.Style.FILL);
    }

    /**
     * Pause or unpause the game. This happens automatically from `setListener`.
     *
     * @param paused When true, stop ticks. When false, enable ticks.
     */
    public void setPaused(boolean paused) {
        lastTickTime = paused ? 0 : System.currentTimeMillis();
    }

    // Based on the current system time, call the tick() methods an appropriate
    // number of times.
    private void emitTicks() {
        if (gameModel == null || lastTickTime == 0) return; // the game is paused

        while (lastTickTime < System.currentTimeMillis()) {
            lastTickTime += 1000f / gameModel.ticksPerSecond();
            ;
            for (Entity go : gameModel.entities) go.tick();
        }
    }

    /**
     * Configure which GameModel to show. Also unpaused (or pauses, in case of `null`) the game.
     *
     * @param gameModel The `GameModel` to show. Can be `null`. A `GameModel` should not have
     *                  more than one `GameView` attached simultaneously, as each `GameView`
     *                  would be calling `tick()`s, causing the game to speed up.
     */
    public void setGame(GameModel gameModel) {
        this.gameModel = gameModel;
        viewMatrix = null; // needs to be recalculated
        setPaused(gameModel == null);
        invalidate();
    }

    /**
     * Obtain a reference to the Canvas, to manipulate it without the use of `drawBitmap`.
     */
    public Canvas getCanvas() {
        return canvas;
    }

    public void drawBitmap(Bitmap bitmap, float left, float top) {
        drawBitmap(bitmap, left, top, -1, -1, 0, 255);
    }

    public void drawBitmap(Bitmap bitmap, float left, float top, float width) {
        drawBitmap(bitmap, left, top, width, -1, 0, 255);
    }

    public void drawBitmap(Bitmap bitmap, float left, float top, float width, float height) {
        drawBitmap(bitmap, left, top, width, height, 0, 255);
    }

    public void drawBitmap(Bitmap bitmap, float left, float top, float width, float height, float angle) {
        drawBitmap(bitmap, left, top, width, height, angle, 255);
    }

    /**
     * Draw a bitmap to the GameCanvas canvas.
     *
     * @param bitmap The Bitmap to draw. Can be loaded through `getBitmapFromResource()`, for instance.
     * @param left   Distance from the left of the canvas in virtual pixels.
     * @param top    Distance from the top of the canvas in virtual pixels.
     * @param width  Width in virtual pixels. When -1, the width is derived from the height, or from the natural size of the bitmap.
     * @param height Height in virtual pixels. When -1, the height is derived from the width.
     * @param angle  Angle in degrees (0-360).
     * @param alpha  Opacity to draw with (0 is fully transparent, 255 is fully visible).
     *               <p>
     *               In case you only want to draw a part of the Bitmap (for instance when using sprite sheets),
     *               you can create a new Bitmap containing only the intended part using Bitmap.createBitmap(..)
     *               https://developer.android.com/reference/android/graphics/Bitmap.html#createBitmap(android.graphics.Bitmap,%20int,%20int,%20int,%20int)
     */
    public void drawBitmap(Bitmap bitmap, float left, float top, float width, float height, float angle, int alpha) {

        if (width < 0) width = bitmap.getWidth() * (height < 0 ? 1 : (height / bitmap.getHeight()));
        if (height < 0) height = bitmap.getHeight() * (width / bitmap.getWidth());

        canvas.save();

        if (angle != 0) {
            canvas.rotate(angle, left + width / 2, top + height / 2);
        }

        canvas.translate(left, top);

        canvas.scale(width / bitmap.getWidth(), height / bitmap.getHeight());

        if (alpha < 255) {
            alphaPaint.setAlpha(alpha);
        }
        canvas.drawBitmap(bitmap, 0, 0, alpha < 255 ? alphaPaint : null);

        canvas.restore();
    }

    /*
     * Load/decode an image. This is pretty slow.
     * For instance: `Bitmap b = gameModel.getBitmapFromResource(R.drawable.my_image);`
     */
    public Bitmap getBitmapFromResource(int resourceId) {
        return BitmapFactory.decodeResource(getContext().getResources(), resourceId);
    }

    // Called when the actual screen dimensions change. For instance on orientation change.
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewMatrix = null;
    }

    // Calculates how to scale the virtual viewport (as defined in Game), such that it
    // fits nicely in the center of the actual screen.
    private void calculateMatrices() {
        viewMatrix = new Matrix();
        clipRect = null;

        float actualWidth = gameModel.actualWidth = getWidth();
        float actualHeight = gameModel.actualHeight = getHeight();
        float virtualWidth = gameModel.getWidth();
        float virtualHeight = gameModel.getHeight();

        float scale = Math.max(virtualWidth / actualWidth, virtualHeight / actualHeight);
        if (scale < 0.99 || scale > 1.01) {
            viewMatrix.postScale(1f / scale, 1f / scale);

            float extraW = actualWidth * scale - virtualWidth;
            float extraH = actualHeight * scale - virtualHeight;

            if (extraW > 1f || extraH > 1f) {
                viewMatrix.postTranslate(extraW / scale / 2f, extraH / scale / 2f);
                clipRect = new RectF(0, 0, virtualWidth, virtualHeight);
            }
        }

        // Invert the view matrix, to obtain a matrix that can be used for translating
        // touch events back to virtual coordinates.
        touchMatrix = new Matrix(viewMatrix);
        touchMatrix.invert(touchMatrix);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (gameModel == null) return false;

        // First fire any outstanding ticks, to make sure objects are in their current place before
        // using their coordinates.
        emitTicks();

        // Translate actual coordinates to virtual coordinates:
        event.transform(touchMatrix);

        gameModel.handleTouch(event);
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (gameModel == null) return;

        // The first draw (after orientation changes) we need to calculate the viewMatrix and clipRect.
        if (viewMatrix == null) calculateMatrices();

        // Call `start()` once, before the first draw but after widths/heights have been calculated.
        if (!started) {
            started = true;
            gameModel.start();
        }

        // Paint the whole of the canvas (including black bars) black
        canvas.drawPaint(blackPaint);

        // Based on these calculations, we can configure the canvas.
        canvas.setMatrix(viewMatrix);
        if (clipRect != null) {
            // Make sure nobody can draw over the black bars
            canvas.clipRect(clipRect);
        }

        // Make sure the game state is up-to-date with the system time.
        emitTicks();

        // Allow draw methods to access our canvas
        this.canvas = canvas;

        // We'll iterate using first/higher, as it will allow the TreeSet
        // to be modified while iterating it.
        for (Entity go : gameModel.entities) go.draw(this);

        // After this, nobody should be drawing to the canvas anymore.
        this.canvas = null;

        // If the game is not paused, schedule the next redraw immediately.
        // Android will limit redraws to 60 times per second.
        if (lastTickTime != 0) invalidate();

        // Log FPS counter and hardware acceleration status.
        frameCount++;
        double now = System.currentTimeMillis();
        if (lastFpsLogTime == 0d) {
            lastFpsLogTime = now;
            Log.i("GameCanvas", "hardware acceleration: " + (canvas.isHardwareAccelerated() ? "enabled" : "*DISABLED*"));
        }
        double delta = (now - lastFpsLogTime) / 1000d;
        if (delta >= 3) {
            Log.i("GameCanvas", String.format("%.1f fps", frameCount / delta));
            frameCount = 0;
            lastFpsLogTime = now;
        }

//        Log.i("XandY", "X: " + gameModel.getEntity(Seeker.class).getXPos() + "  - Y: " + gameModel.getEntity(Seeker.class).getYPos());
    }
}
