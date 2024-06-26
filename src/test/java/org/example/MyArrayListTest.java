package org.example;

import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Класс для тестирования пользовательской реализации списка массивов {@link MyArrayList}.
 * Содержит набор тестовых методов для проверки функциональности списка, таких как добавление, удаление, получение элементов,
 * а также сортировка списка с использованием естественного порядка или пользовательского компаратора.
 */
public class MyArrayListTest {
    /**
     * Тестирует добавление одного элемента в список.
     * Проверяет, что после добавления элемент присутствует в списке на ожидаемой позиции.
     */
    @Test
    public void testAddSingleElement() {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.add(1);
        assertEquals("Элемент должен быть добавлен в список.", (Integer) 1, list.get(0));
    }

    /**
     * Тестирует добавление нескольких элементов в список.
     * Проверяет, что последний добавленный элемент находится на правильной позиции в списке.
     */
    @Test
    public void testAddMultipleElements() {
        MyArrayList<String> list = new MyArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        assertEquals("Последний добавленный элемент должен находиться на третьем месте.", "c", list.get(2));
    }

    /**
     * Тестирует добавление элемента на конкретный индекс в списке.
     * Проверяет, что элемент был вставлен на указанную позицию.
     */
    @Test
    public void testAddAtSpecificIndex() {
        MyArrayList<Double> list = new MyArrayList<>();
        list.add(1.1);
        list.add(2.2);
        list.add(1, 3.3);
        assertEquals("Элемент должен быть вставлен на указанный индекс.", 3.3, list.get(1));
    }

    /**
     * Тестирует добавление элемента в начало списка.
     * Проверяет, что после добавления новый элемент находится на первой позиции.
     */
    @Test
    public void testAddAtStart() {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.add(1);
        list.add(0, 2);
        assertEquals("Элемент должен быть добавлен в начало списка.", (Integer) 2, list.get(0));
    }

    /**
     * Тестирует добавление элемента в конец списка.
     * Проверяет, что после добавления новый элемент находится на последней позиции.
     */
    @Test
    public void testAddAtEnd() {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.add(1);
        list.add(2);
        list.add(list.getSize(), 3);
        assertEquals("Элемент должен быть добавлен в конец списка.", (Integer) 3, list.get(list.getSize() - 1));
    }

    /**
     * Тестирует добавление большого количества элементов в список.
     * Проверяет, что последний добавленный элемент соответствует ожиданиям и находится на правильной позиции.
     */
    @Test
    public void testAddLargeNumberOfElements() {
        MyArrayList<Integer> list = new MyArrayList<>();
        int elementsCount = 10000;
        for (int i = 0; i < elementsCount; i++) {
            list.add(i);
        }
        assertEquals("Последний добавленный элемент должен соответствовать ожиданиям.", (Integer) (elementsCount - 1), list.get(elementsCount - 1));
    }

    /**
     * Тестирует попытку добавления элемента за пределы допустимого диапазона индексов.
     * Ожидается получение {@link IndexOutOfBoundsException}.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddOutOfBounds() {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.add(-1, 1);
    }

    /**
     * Тестирует получение элемента из списка за пределами допустимого диапазона индексов.
     * Ожидается получение {@link IndexOutOfBoundsException}.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetOutOfBounds() {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.add(1);
        list.add(2);
        list.get(2);
    }

    /**
     * Тестирует попытку получения элемента из пустого списка.
     * Ожидается получение {@link IndexOutOfBoundsException}.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetFromEmptyList() {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.get(0);
    }

    /**
     * Тестирует удаление одного элемента из списка.
     * Проверяет, что список становится пустым после удаления единственного элемента.
     */
    @Test
    public void testRemoveSingleElement() {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.add(1);
        list.remove(0);
        assertEquals("Список должен быть пуст после удаления элемента.", 0, list.getSize());
    }

    /**
     * Тестирует удаление конкретного элемента из списка по индексу.
     * Проверяет, что элемент был удален и оставшиеся элементы сдвинулись на одну позицию вверх.
     */
    @Test
    public void testRemoveSpecificElement() {
        MyArrayList<String> list = new MyArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.remove(1);
        assertEquals("Элемент на индексе 1 должен быть удален.", "c", list.get(1));
    }

    /**
     * Тестирует попытку удаления элемента из пустого списка.
     * Ожидается получение {@link IndexOutOfBoundsException}.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveFromEmptyList() {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.remove(0);
    }

    /**
     * Тестирует удаление элемента с индексом, выходящим за пределы допустимого диапазона.
     * Ожидается выброс исключения IndexOutOfBoundsException.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveOutOfBounds() {
        MyArrayList<Integer> list = new MyArrayList<>();
        for (int i = 0; i < 3; i++) {
            list.add(i);
        }
        list.remove(-1);
    }

    /**
     * Тестирует удаление всех элементов из списка.
     * После каждого удаления проверяется, что размер списка уменьшается на единицу,
     * и в конце теста список должен быть пустым.
     */
    @Test
    public void testRemoveAllElements() {
        MyArrayList<Integer> list = new MyArrayList<>();
        int elementsCount = 3;
        for (int i = 0; i < elementsCount; i++) {
            list.add(i);
        }

        for (int i = elementsCount - 1; i >= 0; i--) {
            list.remove(i);
            assertEquals("Размер списка должен уменьшаться после каждого удаления.", i, list.getSize());
        }

        assertEquals("Список должен быть пуст после удаления всех элементов.", 0, list.getSize());
    }

    /**
     * Тестирует очистку пустого списка.
     * После вызова метода clear() размер списка должен быть равен нулю.
     */
    @Test
    public void testClearEmptyList() {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.clear();
        assertEquals("Список должен быть пуст после очистки.", 0, list.getSize());
    }

    /**
     * Тестирует очистку списка с элементами.
     * После вызова метода clear() размер списка должен быть равен нулю.
     */
    @Test
    public void testClearNonEmptyList() {
        MyArrayList<String> list = new MyArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.clear();
        assertEquals("Список должен быть пуст после очистки.", 0, list.getSize());
    }

    /**
     * Тестирует сортировку слиянием списка целых чисел.
     * После сортировки элементы списка должны следовать в возрастающем порядке.
     */
    @Test
    public void testSortWithIntegers() {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.add(3);
        list.add(1);
        list.add(2);
        list.sort();
        assertTrue("Список должен быть отсортирован.", Arrays.equals(list.toArray(), new Integer[]{1, 2, 3}));
    }

    /**
     * Тестирует сортировку слиянием большого количества данных.
     * После сортировки элементы списка должны следовать в возрастающем порядке.
     */
    @Test
    public void testSortWithLargeData() {
        MyArrayList<Integer> list = new MyArrayList<>();
        Integer[] data = new Integer[10000000];
        for (int i = 10000000; i > 0; i--) {
            list.add(i - 1);
            data[10000000 - i] = 10000000 - i;
        }
        list.sort();
        assertTrue("Список с большим кол-вом элементов должен быть отсортирован.", Arrays.equals(list.toArray(), data));
    }

    /**
     * Тестирует сортировку слиянием списка строк с использованием компаратора обратного порядка.
     * После сортировки элементы списка должны следовать в убывающем лексикографическом порядке.
     */
    @Test
    public void testSortWithReverseOrderComparator() {
        MyArrayList<String> list = new MyArrayList<>();
        list.add("b");
        list.add("c");
        list.add("a");
        Comparator<String> reverseOrder = Comparator.reverseOrder();
        list.sort(reverseOrder);
        assertTrue("Список должен быть отсортирован в обратном порядке.", Arrays.equals(list.toArray(), new String[]{"c", "b", "a"}));
    }

    /**
     * Тестирует сортировку слиянием списка строк с использованием пользовательского компаратора по длине строки в обратном порядке.
     * После сортировки элементы списка должны следовать в порядке убывания длины строки.
     */
    @Test
    public void testSortWithCustomReverseComparator() {
        MyArrayList<String> list = new MyArrayList<>();
        list.add("bbb");
        list.add("aaaa");
        list.add("cc");
        Comparator<String> lengthComparator = (s1, s2) -> Integer.compare(s2.length(), s1.length());
        list.sort(lengthComparator);
        assertTrue("Список должен быть отсортирован по длине строки в обратном порядке.", Arrays.equals(list.toArray(), new String[]{"aaaa", "bbb", "cc"}));
    }
    /**
     * Тестирует быструю сортировку списка целых чисел.
     * После сортировки элементы списка должны следовать в возрастающем порядке.
     */
    @Test
    public void testQuickSortWithIntegers() {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.add(3);
        list.add(1);
        list.add(2);
        list.quickSort();
        assertTrue("Список должен быть отсортирован.", Arrays.equals(list.toArray(), new Integer[]{1, 2, 3}));
    }

    /**
     * Тестирует быструю сортировку большого количества данных.
     * После сортировки элементы списка должны следовать в возрастающем порядке.
     */
    @Test
    public void testQuickSortWithLargeData() {
        MyArrayList<Integer> list = new MyArrayList<>();
        Integer[] data = new Integer[10000];
        for (int i = 10000; i > 0; i--) {
            list.add(i - 1);
            data[10000 - i] = 10000 - i;
        }
        list.quickSort();
        assertTrue("Список с большим кол-вом элементов должен быть отсортирован.", Arrays.equals(list.toArray(), data));
    }

    /**
     * Тестирует быструю сортировку списка строк с использованием компаратора обратного порядка.
     * После сортировки элементы списка должны следовать в убывающем лексикографическом порядке.
     */
    @Test
    public void testQuickSortWithReverseOrderComparator() {
        MyArrayList<String> list = new MyArrayList<>();
        list.add("b");
        list.add("c");
        list.add("a");
        Comparator<String> reverseOrder = Comparator.reverseOrder();
        list.quickSort(reverseOrder);
        assertTrue("Список должен быть отсортирован в обратном порядке.", Arrays.equals(list.toArray(), new String[]{"c", "b", "a"}));
    }

    /**
     * Тестирует быструю сортировку списка строк с использованием пользовательского компаратора по длине строки в обратном порядке.
     * После сортировки элементы списка должны следовать в порядке убывания длины строки.
     */
    @Test
    public void testQuickSortWithCustomReverseComparator() {
        MyArrayList<String> list = new MyArrayList<>();
        list.add("bbb");
        list.add("aaaa");
        list.add("cc");
        Comparator<String> lengthComparator = (s1, s2) -> Integer.compare(s2.length(), s1.length());
        list.quickSort(lengthComparator);
        assertTrue("Список должен быть отсортирован по длине строки в обратном порядке.", Arrays.equals(list.toArray(), new String[]{"aaaa", "bbb", "cc"}));
    }
}
