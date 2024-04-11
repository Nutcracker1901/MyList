/**
 * Пользовательская реализация связного списка, который хранит элементы в линейном порядке.
 * Элементы могут быть добавлены, удалены, получены, и список может быть отсортирован с использованием естественного порядка или пользовательского компаратора.
 * Список является обобщенным и может содержать объекты любого типа, расширяющие Comparable.
 *
 * @param <T> тип элементов, хранящихся в этом списке
 */
package org.example;

import java.util.Comparator;

public class MyList<T extends Comparable<? super T>> {

    private Node<T> head;

    private int size;

    /**
     * Конструктор создает пустой список.
     */
    public MyList() {
        head = null;
        size = 0;
    }

    /**
     * Добавляет элемент в конец списка.
     *
     * @param element элемент для добавления
     */
    public void add(T element) {
        add(size, element);
    }

    /**
     * Вставляет указанный элемент в указанную позицию в этом списке.
     *
     * @param index   индекс, по которому нужно вставить указанный элемент
     * @param element элемент для вставки
     * @throws IndexOutOfBoundsException если индекс выходит за пределы диапазона (index &lt; 0 || index > size())
     */
    public void add(long index, T element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        Node<T> newNode = new Node<>(element);
        if (index == 0) {
            newNode.next = head;
            head = newNode;
        } else {
            Node<T> current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            newNode.next = current.next;
            current.next = newNode;
        }
        size++;
    }

    /**
     * Возвращает элемент в указанной позиции в этом списке.
     *
     * @param index индекс элемента для возврата
     * @return элемент в указанной позиции в этом списке
     * @throws IndexOutOfBoundsException если индекс выходит за пределы диапазона (index &lt; 0 || index >= size())
     */
    public T get(long index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        return current.data;
    }

    /**
     * Возвращает количество элементов в этом списке.
     *
     * @return количество элементов в этом списке
     */
    public int getSize() {
        return size;
    }

    /**
     * Удаляет элемент в указанной позиции из этого списка.
     *
     * @param index индекс удаляемого элемента
     * @throws IndexOutOfBoundsException если индекс выходит за пределы диапазона (index &lt; 0 || index >= size())
     */
    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        if (index == 0) {
            head = head.next;
        } else {
            Node<T> current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            current.next = current.next.next;
        }
        size--;
    }

    /**
     * Удаляет все элементы из этого списка. Список будет пуст после выполнения этого вызова.
     */
    public void clear() {
        head = null;
        size = 0;
    }

    /**
     * Сортирует этот список согласно естественному порядку его элементов.
     */
    public void sort() {
        sort(Comparator.naturalOrder());
    }

    /**
     * Сортирует этот список согласно порядку, определенному указанным компаратором.
     *
     * @param comparator компаратор для определения порядка списка. Значение null указывает на то, что должен быть использован естественный порядок элементов
     */
    public void sort(Comparator<? super T> comparator) {
        if (size > 1) {
            Node<T>[] nodeArray = (Node<T>[]) new Node[size];
            Node<T> current = head;
            for (int i = 0; i < size; i++) {
                nodeArray[i] = current;
                current = current.next;
            }

            for (int width = 1; width < size; width = 2 * width) {
                for (int i = 0; i < size; i = i + 2 * width) {
                    int left = i;
                    int right = Math.min(i + width, (int) size);
                    int end = Math.min(i + 2 * width, (int) size);

                    merge(nodeArray, left, right, end, comparator);
                }
            }

            head = nodeArray[0];
            current = head;
            for (int i = 1; i < size; i++) {
                current.next = nodeArray[i];
                current = current.next;
            }
            current.next = null;
        }
    }

    /**
     * Объединяет два подмассива во время сортировки.
     *
     * @param nodeArray  массив узлов, представляющих подмассивы для объединения
     * @param left       начальный индекс левого подмассива
     * @param right      начальный индекс правого подмассива
     * @param end        конечный индекс правого подмассива
     * @param comparator компаратор для определения порядка элементов
     */
    private void merge(Node<T>[] nodeArray, int left, int right, int end, Comparator<? super T> comparator) {
        int leftEnd = right - 1;
        int tempPos = left;
        Node<T>[] tempArray = (Node<T>[]) new Node[end - left];

        int i = left;
        int j = right;
        int k = 0;

        while (i <= leftEnd && j < end) {
            if (comparator.compare(nodeArray[i].data, nodeArray[j].data) <= 0) {
                tempArray[k++] = nodeArray[i++];
            } else {
                tempArray[k++] = nodeArray[j++];
            }
        }

        while (i <= leftEnd) {
            tempArray[k++] = nodeArray[i++];
        }

        while (j < end) {
            tempArray[k++] = nodeArray[j++];
        }

        for (i = 0; i < k; i++, tempPos++) {
            nodeArray[tempPos] = tempArray[i];
        }
    }

    /**
     * Возвращает массив, содержащий все элементы этого списка в правильной последовательности (от первого до последнего элемента).
     * Этот метод служит мостом между API, основанными на массивах, и API, основанными на коллекциях.
     *
     * Возвращаемый массив будет "безопасным" в том смысле, что этот список не будет содержать ссылок на него.
     * (Другими словами, этот метод должен выделить новый массив). Таким образом, вызывающий может свободно изменять
     * возвращаемый массив.
     *
     * @return массив, содержащий все элементы этого списка в правильной последовательности
     */
    public T[] toArray() {
        T[] result = (T[]) new Comparable[size];
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            result[i] = current.data;
            current = current.next;
        }
        return result;
    }

    /**
     * Узел, используемый в связном списке для хранения элементов.
     *
     * @param <T> тип элемента, хранящегося в узле
     */
    private static class Node<T> {
        T data;
        Node<T> next;

        /**
         * Конструктор создает узел с указанным элементом и следующим узлом.
         *
         * @param data элемент, который будет храниться в узле
         */
        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }
}
