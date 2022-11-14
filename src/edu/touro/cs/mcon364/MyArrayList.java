package edu.touro.cs.mcon364;

import java.util.*;

public class MyArrayList implements List<String> {
    private String[] backingStore = new String[10];
    private int ip = 0;

    @Override
    public int size() {
        return ip;
    }

    @Override
    public boolean isEmpty() {
        return ip == 0;
    }

    @Override
    public void clear() {
        backingStore = new String[10];
        ip = 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    private void OOBCheck(int i, boolean forAdd) {
        if (i < 0 || (forAdd ? i > ip : i >= ip)) {
            throw new IndexOutOfBoundsException(String.format("Index %d is out of bounds for length %d.", i, ip));
        }
    }

    @Override
    public String get(int index) {
        OOBCheck(index, false);
        return backingStore[index];
    }

    @Override
    public String set(int index, String element) {
        OOBCheck(index, false);

        String old = backingStore[index];
        backingStore[index] = element;
        return old;
    }

    private void growBackingStore(int min_capacity) {
        String[] newBs = new String[min_capacity];
        System.arraycopy(backingStore, 0, newBs, 0, backingStore.length);
        backingStore = newBs;
    }

    private void defaultGrowBackingStore() {
        growBackingStore(2 * backingStore.length + 1);
    }

    @Override
    public void add(int index, String element) {
        OOBCheck(index, true);

        if (ip >= backingStore.length) {
            defaultGrowBackingStore();
        }

        System.arraycopy(backingStore, index, backingStore, index + 1, ip - index);
        backingStore[index] = element;
        ip++;
    }

    @Override
    public boolean add(String s) {
        if (ip >= backingStore.length) {
            defaultGrowBackingStore();
        }

        backingStore[ip++] = s;
        return true;
    }

    private void removeIndex(int index) {
        if (index != ip - 1) { // if index is the last element, no need to copy anything
            System.arraycopy(backingStore, index + 1, backingStore, index,
                    ip - index - 1);
        }
        backingStore[--ip] = null;
    }

    @Override
    public String remove(int index) {
        OOBCheck(index, false);

        String s = backingStore[index];
        removeIndex(index);
        return s;
    }

    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);
        if (index != -1) {
            removeIndex(index);
            return true;
        }
        return false;
    }

    @Override
    public Object[] toArray() {
        String[] copy = new String[ip];
        System.arraycopy(backingStore, 0, copy, 0, ip);
        return copy;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();
    }

    private void nullCheck(Collection<?> c) {
        if (c == null) {
            throw new NullPointerException("Collection c may not be null.");
        }
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        nullCheck(c);

        for (Object el : c) {
            if (!contains(el)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends String> c) {
        return addAll(ip, c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends String> c) {
        nullCheck(c);
        OOBCheck(index, true);

        if (c.isEmpty()) {
            return false;
        }

        if (ip + c.size() > backingStore.length) {
            growBackingStore(backingStore.length + c.size());
        }

        System.arraycopy(backingStore, index, backingStore, index + c.size(), ip - index);

        int indexDifference = 0;
        for (String s : c) {
            backingStore[index + indexDifference] = s;
            indexDifference++;
        }

        ip += c.size();

        return true;
    }

    private boolean removeSome(Collection<?> c, boolean remove) { //remove vs retain
        nullCheck(c);

        if (c.isEmpty() || ip == 0) {
            return false;
        }

        int newIP = 0;

        for (int i = 0; i < ip; i++) {
            // If you're removing, then you only want to keep elements not in c.
            // If you're retaining (remove is false), then you only want elements that are in c
            if (remove != c.contains(backingStore[i])) {
                backingStore[newIP++] = backingStore[i];
            }
        }

        for (int i = newIP; i < ip; i++) {
            backingStore[i] = null;
        }

        boolean listWasChanged = ip != newIP; // For example, removing but all the elements are different.
        ip = newIP;
        return listWasChanged;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return removeSome(c, true);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return removeSome(c, false);
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < ip; i++) {
            if (Objects.equals(o, backingStore[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = ip - 1; i > -1; i--) {
            if (Objects.equals(o, backingStore[i])) {
                return i;
            }
        }
        return -1;
    }

    private class MyArrayListIterator implements Iterator<String> {
        private int currIndex = 0;

        @Override
        public boolean hasNext() {
            return currIndex < ip;
        }

        @Override
        public String next() {
            if (currIndex == ip) {
                throw new NoSuchElementException();
            }

            return backingStore[currIndex++];
        }
    }

    @Override
    public Iterator<String> iterator() {
        return new MyArrayListIterator();
    }

    @Override
    public ListIterator<String> listIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<String> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }
}