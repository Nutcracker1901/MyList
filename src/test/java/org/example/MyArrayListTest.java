package org.example;

import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class MyArrayListTest {

    @Test
    public void testAddSingleElement() {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.add(1);
        assertEquals("Элемент должен быть добавлен в список.", (Integer) 1, list.get(0));
    }

    @Test
    public void testAddMultipleElements() {
        MyArrayList<String> list = new MyArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        assertEquals("Последний добавленный элемент должен находиться на третьем месте.", "c", list.get(2));
    }

    @Test
    public void testAddAtSpecificIndex() {
        MyArrayList<Double> list = new MyArrayList<>();
        list.add(1.1);
        list.add(2.2);
        list.add(1, 3.3);
        assertEquals("Элемент должен быть вставлен на указанный индекс.", (Double) 3.3, list.get(1));
    }

    @Test
    public void testAddAtStart() {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.add(1);
        list.add(0, 2);
        assertEquals("Элемент должен быть добавлен в начало списка.", (Integer) 2, list.get(0));
    }

    @Test
    public void testAddAtEnd() {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.add(1);
        list.add(2);
        list.add(list.getSize(), 3);
        assertEquals("Элемент должен быть добавлен в конец списка.", (Integer) 3, list.get(list.getSize() - 1));
    }

    @Test
    public void testAddLargeNumberOfElements() {
        MyArrayList<Integer> list = new MyArrayList<>();
        int elementsCount = 10000;
        for (int i = 0; i < elementsCount; i++) {
            list.add(i);
        }
        assertEquals("Последний добавленный элемент должен соответствовать ожиданиям.", (Integer) (elementsCount - 1), list.get(elementsCount - 1));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddOutOfBounds() {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.add(-1, 1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetOutOfBounds() {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.add(1);
        list.add(2);
        list.get(2);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetFromEmptyList() {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.get(0);
    }

    @Test
    public void testRemoveSingleElement() {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.add(1);
        list.remove(0);
        assertEquals("Список должен быть пуст после удаления элемента.", 0, list.getSize());
    }

    @Test
    public void testRemoveSpecificElement() {
        MyArrayList<String> list = new MyArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.remove(1);
        assertEquals("Элемент на индексе 1 должен быть удален.", "c", list.get(1));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveFromEmptyList() {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.remove(0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveOutOfBounds() {
        MyArrayList<Integer> list = new MyArrayList<>();
        for (int i = 0; i < 3; i++) {
            list.add(i);
        }
        list.remove(-1);
    }

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

    @Test
    public void testClearEmptyList() {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.clear();
        assertEquals("Список должен быть пуст после очистки.", 0, list.getSize());
    }

    @Test
    public void testClearNonEmptyList() {
        MyArrayList<String> list = new MyArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.clear();
        assertEquals("Список должен быть пуст после очистки.", 0, list.getSize());
    }

    @Test
    public void testSortWithIntegers() {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.add(3);
        list.add(1);
        list.add(2);
        list.sort();
        assertTrue("Список должен быть отсортирован.", Arrays.equals(list.toArray(), new Integer[]{1, 2, 3}));
    }

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
}
