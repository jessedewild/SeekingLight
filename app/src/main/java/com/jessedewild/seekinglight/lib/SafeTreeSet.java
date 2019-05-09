package com.jessedewild.seekinglight.lib;

import java.util.TreeSet;

/**
 * Extends TreeSet to have fail-safe iterators, allowing modifications to the
 * tree while iterating it.
 * It works by selecting the next-highest item in the TreeSet at every
 * iteration. So items inserted during the iteration are iterated in case they
 * come after the current item. Similarly, removal is also effective immediately.
 */
public class SafeTreeSet<T> extends TreeSet<T> {

    @Override
    public java.util.Iterator<T> iterator() {
        return new Iterator<T>(this);
    }

    @Override
    public java.util.Iterator<T> descendingIterator() {
        return new DescendingIterator<T>(this);
    }

    class Iterator<T> implements java.util.Iterator<T> {
        TreeSet<T> tree;
        T current;

        Iterator(TreeSet<T> tree) {
            this.tree = tree;
        }

        @Override
        public boolean hasNext() {
            if (current == null) { // first time
                if (tree.isEmpty()) return false;
                current = tree.first();
                return true;
            }
            T next = tree.higher(current);
            if (next == null) return false;
            current = next;
            return true;
        }

        @Override
        public T next() {
            return current;
        }
    }

    class DescendingIterator<T> implements java.util.Iterator<T> {
        TreeSet<T> tree;
        T current;

        DescendingIterator(TreeSet<T> tree) {
            this.tree = tree;
        }

        @Override
        public boolean hasNext() {
            if (current == null) { // first time
                if (tree.isEmpty()) return false;
                current = tree.last();
                return true;
            }
            T next = tree.lower(current);
            if (next == null) return false;
            current = next;
            return true;
        }

        @Override
        public T next() {
            return current;
        }
    }
}
