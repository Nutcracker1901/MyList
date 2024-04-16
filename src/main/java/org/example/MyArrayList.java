package org.example;

import java.util.Comparator;

/**
 * Класс MyArrayList представляет собой упрощенную реализацию списка массивов,
 * который может автоматически расширять свой размер при добавлении элементов.
 *
 * @param <T> Тип элементов, которые будут храниться в списке. Должен быть сравнимым.
 */
public class MyArrayList<T extends Comparable<? super T>> {
    private Object[] elements;
    private int size;

    /**
     * Конструктор для создания пустого списка MyArrayList с начальной размером.
     */
    public MyArrayList() {
        elements = new Object[10];
        size = 0;
    }

    /**
     * Добавляет элемент в конец списка.
     *
     * @param element Элемент для добавления.
     */
    public void add(T element) {
        if (size == elements.length) {
            increaseCapacity();
        }
        elements[size++] = element;
    }

    /**
     * Добавляет элемент в указанную позицию в списке.
     *
     * @param index   Индекс, куда должен быть добавлен элемент.
     * @param element Элемент для добавления.
     * @throws IndexOutOfBoundsException если индекс находится вне диапазона (index &lt; 0 || index > size).
     */
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

    /**
     * Возвращает элемент по указанному индексу.
     *
     * @param index Индекс запрашиваемого элемента.
     * @return Элемент на указанной позиции в списке.
     * @throws IndexOutOfBoundsException если индекс находится вне диапазона (index &lt; 0 || index >= size).
     */
    @SuppressWarnings("unchecked")
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        return (T) elements[index];
    }

    /**
     * Возвращает количество элементов в списке.
     *
     * @return Количество элементов в списке.
     */
    public int getSize() {
        return size;
    }

    /**
     * Удаляет элемент по указанному индексу из списка.
     *
     * @param index Индекс удаляемого элемента.
     * @throws IndexOutOfBoundsException если индекс находится вне диапазона (index &lt; 0 || index >= size).
     */
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

    /**
     * Очищает список, удаляя все элементы из него.
     */
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    /**
     * Сортирует список сортировкой слиянием с использованием естественного порядка элементов.
     */
    public void sort() {
        sort(Comparator.naturalOrder());
    }

    /**
     * Сортирует список сортировкой слиянием с использованием заданного компаратора.
     *
     * @param comparator Компаратор, используемый для сравнения элементов списка.
     */
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

    /**
     * Сливает две отсортированные последовательности в одну отсортированную последовательность.
     *
     * @param elements   Массив элементов для слияния.
     * @param left       Начальный индекс левой последовательности.
     * @param right      Начальный индекс правой последовательности.
     * @param end        Конечный индекс правой последовательности.
     * @param comparator Компаратор для сравнения элементов.
     */
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

    /**
     * Увеличивает емкость внутреннего массива элементов, чтобы вместить больше элементов.
     */
    private void increaseCapacity() {
        int newSize = elements.length * 2;
        Object[] copy = new Object[newSize];
        for (int i = 0; i < elements.length; i++) {
            copy[i] = elements[i];
        }
        elements = copy;
    }

    /**
     * Копирует элементы из одного массива в другой.
     *
     * @param src     Исходный массив.
     * @param srcPos  Начальная позиция в исходном массиве.
     * @param dest    Целевой массив.
     * @param destPos Начальная позиция в целевом массиве.
     * @param length  Количество копируемых элементов.
     */
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

    /**
     * Выполняет быструю сортировку списка, используя естественный порядок элементов.
     */
    public void quickSort() {
        quickSort(0, size - 1, Comparator.naturalOrder());
    }

    /**
     * Выполняет быструю сортировку списка, используя заданный компаратор.
     *
     * @param comparator Компаратор, используемый для сравнения элементов списка.
     */
    public void quickSort(Comparator<? super T> comparator) {
        quickSort(0, size - 1, comparator);
    }

    /**
     * Выполняет быструю сортировку части списка.
     *
     * @param low        Нижний индекс диапазона для сортировки.
     * @param high       Верхний индекс диапазона для сортировки.
     * @param comparator Компаратор для сравнения элементов.
     */
    private void quickSort(int low, int high, Comparator<? super T> comparator) {
        if (low < high) {
            int pivotIndex = partition(low, high, comparator);
            quickSort(low, pivotIndex - 1, comparator);
            quickSort(pivotIndex + 1, high, comparator);
        }
    }

    /**
     * Разделяет часть списка на две части: элементы меньше опорного и элементы больше опорного.
     *
     * @param low        Нижний индекс диапазона для разделения.
     * @param high       Верхний индекс диапазона для разделения.
     * @param comparator Компаратор для сравнения элементов.
     * @return Индекс опорного элемента после разделения.
     */
    @SuppressWarnings("unchecked")
    private int partition(int low, int high, Comparator<? super T> comparator) {
        T pivot = (T) elements[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (comparator.compare((T) elements[j], pivot) <= 0) {
                i++;
                swap(i, j);
            }
        }
        swap(i + 1, high);
        return i + 1;
    }

    /**
     * Меняет местами два элемента в списке.
     *
     * @param i Индекс первого элемента для обмена.
     * @param j Индекс второго элемента для обмена.
     */
    private void swap(int i, int j) {
        Object temp = elements[i];
        elements[i] = elements[j];
        elements[j] = temp;
    }

    /**
     * Возвращает массив всех элементов списка.
     *
     * @return Массив содержащий все элементы списка.
     */
    @SuppressWarnings("unchecked")
    public T[] toArray() {
        T[] result = (T[]) new Comparable[size];
        for (int i = 0; i < size; i++) {
            result[i] = (T) elements[i];
        }
        return result;
    }
}
