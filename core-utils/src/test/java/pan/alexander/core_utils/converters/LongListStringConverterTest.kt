package pan.alexander.core_utils.converters

import org.junit.Assert.assertEquals
import org.junit.Test

class LongListStringConverterTest {

    private val string = "10,200,3000"
    private val list = listOf<Long>(10, 200, 3000)

    @Test
    fun listToString_ReturnsCorrectResult() {
        assertEquals(string, LongListStringConverter.listToString(list))
    }

    @Test
    fun stringToList_CorrectString_ReturnsCorrectResult() {
        assertEquals(list, LongListStringConverter.stringToList(string))
    }

    @Test
    fun stringToList_WrongString_ReturnsCorrectResult() {
        val wrongString = "10,200,3000a"
        val expectedList = listOf<Long>(10, 200)
        assertEquals(expectedList, LongListStringConverter.stringToList(wrongString))
    }

    @Test
    fun stringToList_WrongString_ReturnsEmptyResult() {
        val wrongString = "1w0,20d0,3000a"
        val expectedList = listOf<Long>()
        assertEquals(expectedList, LongListStringConverter.stringToList(wrongString))
    }
}
