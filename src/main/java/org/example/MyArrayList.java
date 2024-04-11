package org.example;

import java.util.Arrays;
import java.util.Comparator;

public class MyArrayList<T extends Comparable<? super T>> {
    private Object[] elements;
    private int size;

    public MyArrayList() {
        elements = new Object[10];
        size = 0;
    }

    public void add(T element) {
        if (size == elements.length) {
            increaseCapacity();
        }
        elements[size++] = element;
    }

    public void add(int index, T element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        if (size == elements.length) {
            increaseCapacity();
        }
        arrayCopy(elements, index, elements, index + 1, size - index);
        elements[index] = element;
        size++;
    }

    @SuppressWarnings("unchecked")
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        return (T) elements[index];
    }

    public int getSize() {
        return size;
    }

    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        int numMoved = size - index - 1;
        if (numMoved > 0) {
            arrayCopy(elements, index + 1, elements, index, numMoved);
        }
        elements[--size] = null;
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    @SuppressWarnings("unchecked")
    public void sort() {
        sort(Comparator.naturalOrder());
    }

    @SuppressWarnings("unchecked")
    public void sort(Comparator<? super T> comparator) {
        if (size > 1) {
            for (int width = 1; width < size; width = 2 * width) {
                for (int i = 0; i < size; i = i + 2 * width) {
                    int left = i;
                    int right = Math.min(i + width, size);
                    int end = Math.min(i + 2 * width, size);

                    merge(elements, left, right, end, comparator);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void merge(Object[] elements, int left, int right, int end, Comparator<? super T> comparator) {
        int leftEnd = right - 1;
        int tempPos = left;
        Object[] tempArray = new Object[end - left];

        int i = left;
        int j = right;
        int k = 0;

        while (i <= leftEnd && j < end) {
            if (comparator.compare((T) elements[i], (T) elements[j]) <= 0) {
                tempArray[k++] = elements[i++];
            } else {
                tempArray[k++] = elements[j++];
            }
        }

        while (i <= leftEnd) {
            tempArray[k++] = elements[i++];
        }

        while (j < end) {
            tempArray[k++] = elements[j++];
        }

        for (i = 0; i < k; i++, tempPos++) {
            elements[tempPos] = tempArray[i];
        }
    }

    private void increaseCapacity() {
        int newSize = elements.length * 2;
        Object[] copy = new Object[newSize];
        for (int i = 0; i < elements.length; i++) {
            copy[i] = elements[i];
        }
        elements = copy;
    }

//    private Object[] copyOf(Object[] original, int newLength) {
//        Object[] copy = new Object[newLength];
//        arrayCopy(original, 0, copy, 0, Math.min(original.length, newLength));
//        return copy;
//    }

    private void arrayCopy(Object[] src, int srcPos, Object[] dest, int destPos, int length) {
        if (src == dest) {
            Object[] temp = new Object[length];
            for (int i = 0; i < length; i++) {
                temp[i] = src[srcPos + i];
            }
            for (int i = 0; i < length; i++) {
                dest[destPos + i] = temp[i];
            }
        } else {
            for (int i = 0; i < length; i++) {
                dest[destPos + i] = src[srcPos + i];
            }
        }
    }

    @SuppressWarnings("unchecked")
    public T[] toArray() {
        T[] result = (T[]) new Comparable[size];
        for (int i = 0; i < size; i++) {
            result[i] = (T) elements[i];
        }
        return result;
    }
}
