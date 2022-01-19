package pan.alexander.core_utils

import org.junit.Assert.*
import org.junit.Test

class RandomTests {

    @Test
    fun testNotNull() {
        val a = 1
        assertNotNull(a)
    }

    @Test
    fun testNull() {
        val a: Any? = null
        assertNull(a)
    }

    @Test
    fun testEquals() {
        val a = "one"
        val b = "one"
        assertEquals(a, b)
    }

    @Test
    fun testNotEquals() {
        val a = "one"
        val b = "two"
        assertNotEquals(a,b)
    }

    @Test
    fun testArrayEquals() {
        val a = arrayOf(1, 2, 3)
        val b = arrayOf(1, 2, 3)
        assertArrayEquals(a, b)
    }

    @Test
    fun testSame() {
        val a = listOf<Int>()
        val b = a
        assertSame(a, b)
    }

}
