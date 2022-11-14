package edu.touro.cs.mcon364;

import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MyArrayListTest {

    private static final MyArrayList MA = new MyArrayList();
    private static List<String> OTHER_LIST;

    @Test
    @Order(0)
    void empty() {
        assertTrue(MA.isEmpty());
    }

    @Test
    @Order(1)
    void add() {
        assertTrue(MA.add("a"));
        assertTrue(MA.add("b"));
    }

    @Test
    @Order(2)
    void size() {
        assertEquals(2, MA.size());
    }

    @Test
    @Order(2)
    void notEmpty() {
        assertFalse(MA.isEmpty());
    }

    @Test
    @Order(3)
    void contains() {
        assertTrue(MA.contains("a"));
        assertTrue(MA.contains("b"));
        assertFalse(MA.contains("c"));
        assertFalse(MA.contains(Integer.valueOf(5)));
    }

    @Test
    @Order(4)
    void addAt() {
        MA.add(1, "c");
        assertEquals("c", MA.get(1));
        assertEquals("b", MA.get(2));

        MA.add(3, "d");
        assertEquals("d", MA.get(3));
        assertEquals("b", MA.get(2));
    }

    @Test
    @Order(4)
    void addAtFail() {
        assertThrows(IndexOutOfBoundsException.class, () -> MA.add(7, "e"));
    }

    @Test
    @Order(5)
    void get() {
        assertEquals("a", MA.get(0));
        assertEquals("c", MA.get(1));
    }

    @Test
    @Order(5)
    void getFail() {
        assertThrows(IndexOutOfBoundsException.class, () -> MA.get(7));
    }

    @Test
    @Order(6)
    void set() {
        assertEquals("a", MA.set(0, "f"));
        assertEquals("f", MA.set(0, "a"));
    }

    @Test
    @Order(6)
    void setFail() {
        assertThrows(IndexOutOfBoundsException.class, () -> MA.set(7, "g"));
    }

    @Test
    @Order(7)
    void removeIndex() {
        assertEquals("d", MA.remove(3));
        assertEquals(3, MA.size());
    }

    @Test
    @Order(7)
    void removeIndexFail() {
        assertThrows(IndexOutOfBoundsException.class, () -> MA.remove(4));
    }

    @Test
    @Order(8)
    void toArray() {
        assertArrayEquals(new String[]{"a", "c", "b"}, MA.toArray());
    }

    @Test
    @Order(8)
    void toArrayBackDoor() {
        Object[] a = MA.toArray();
        a[0] = "z";
        assertEquals("a", MA.get(0));
        assertFalse(MA.contains("z"));
    }

    @Test
    @Order(9)
    void indexOf() {
        MA.add("c");
        assertEquals(1, MA.indexOf("c"));
        assertEquals(-1, MA.indexOf("z"));
    }

    @Test
    @Order(10)
    void lastIndexOf() {
        assertEquals(3, MA.lastIndexOf("c"));
        assertEquals(-1, MA.lastIndexOf("z"));
    }

    @Test
    @Order(11)
    void removeObject() {
        assertTrue(MA.remove("c"));
        assertEquals("a", MA.get(0));
        assertEquals("b", MA.get(1));
        assertEquals("c", MA.get(2));
        assertEquals(3, MA.size());

        assertFalse(MA.remove("z"));
    }

    @Test
    @Order(12)
    void addAll() {
        OTHER_LIST = Arrays.asList("x", "y", "z");
        assertTrue(MA.addAll(OTHER_LIST));
        assertEquals(6, MA.size());
    }

    @Test
    @Order(12)
    void addAllFail() {
        assertThrows(NullPointerException.class, () -> MA.addAll(null));
    }

    @Test
    @Order(13)
    void containsAll() {
        assertTrue(MA.containsAll(OTHER_LIST));
    }

    @Test
    @Order(13)
    void containsAllFail() {
        assertThrows(NullPointerException.class, () -> MA.containsAll(null));
    }

    @Test
    @Order(14)
    void removeAll() {
        assertTrue(MA.removeAll(OTHER_LIST));
        assertEquals("a", MA.get(0));
        assertEquals("b", MA.get(1));
        assertEquals("c", MA.get(2));
        assertEquals(3, MA.size());
    }

    @Test
    @Order(14)
    void removeAllFail() {
        assertThrows(NullPointerException.class, () -> MA.removeAll(null));
    }

    @Test
    @Order(15)
    void addAllIndex() {
        assertTrue(MA.addAll(1, OTHER_LIST));
        assertEquals(6, MA.size());
    }

    @Test
    @Order(15)
    void addAllIndexFail() {
        assertThrows(NullPointerException.class, () -> MA.addAll(1, null));
        assertThrows(IndexOutOfBoundsException.class, () -> MA.addAll(10, OTHER_LIST));
        assertThrows(IndexOutOfBoundsException.class, () -> MA.addAll(-1, OTHER_LIST));
    }

    @Test
    @Order(16)
    void retainAll() {
        assertTrue(MA.retainAll(OTHER_LIST));
        assertEquals(3, MA.size());
    }

    @Test
    @Order(16)
    void retainAllFail() {
        assertThrows(NullPointerException.class, () -> MA.retainAll(null));
    }

    @Test
    @Order(17)
    void iterator() {
        int i = 0;
        for (String s : MA) {
            assertEquals(OTHER_LIST.get(i++), s);
        }
    }

    @Test
    @Order(17)
    void iteratorFail() {
        Iterator<String> it = MA.iterator();
        it.next();
        it.next();
        it.next();
        assertThrows(NoSuchElementException.class, it::next);
    }

    @Test
    @Order(18)
    void clear() {
        MA.clear();
        assertTrue(MA.isEmpty());
        assertEquals(0, MA.size());
    }
}