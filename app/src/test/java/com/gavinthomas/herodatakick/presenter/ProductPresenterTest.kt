package com.gavinthomas.herodatakick.presenter

import com.gavinthomas.herodatakick.network.Product
import com.nhaarman.mockitokotlin2.mock
import org.junit.Assert.*
import org.junit.Test

class ProductPresenterTest {

    private val presenter = ProductPresenter(mock {}, mock {})

    @Test
    fun `sanitise data with correct values`() {
        val data = listOf(Product("1", "cheese1", "50 oz", 0.0))
        val list = presenter.sanitiseData(data)
        assertEquals(1, list.size)
    }

    @Test
    fun `sanitise data with null values`() {
        val data = listOf(Product("1", "cheese1", "", 0.0))
        val list = presenter.sanitiseData(data)
        assertEquals(0, list.size)
    }

    @Test
    fun `sanitise data with incorrect size values`() {
        val data = listOf(Product("1", "cheese1", "10.50 fl oz", 0.0))
        val list = presenter.sanitiseData(data)
        assertEquals(0, list.size)
    }

    @Test
    fun `sanitise data with more incorrect size values`() {
        val data = listOf(Product("1", "cheese1", "10 1/2 oz", 0.0))
        val list = presenter.sanitiseData(data)
        assertEquals(0, list.size)
    }

    @Test
    fun `sort array by alphabetical order`() {
        val unsortedList = listOf(
            Product("1", "Cheddar", "10 oz", 10.0),
            Product("2", "Gouda", "10 oz", 10.0),
            Product("3", "Camembert", "10 oz", 10.0),
            Product("4", "Brie", "10 oz", 10.0)
        )

        val expectedList = listOf(
            Product("4", "Brie", "10 oz", 10.0),
            Product("3", "Camembert", "10 oz", 10.0),
            Product("1", "Cheddar", "10 oz", 10.0),
            Product("2", "Gouda", "10 oz", 10.0)
        )

        assertEquals(expectedList, presenter.sortAndReturnData(unsortedList, SortType.ALPHABETIC))
    }

    @Test
    fun `sort array by reversed alphabetical order`() {
        val unsortedList = listOf(
            Product("1", "Cheddar", "10 oz", 10.0),
            Product("2", "Gouda", "10 oz", 10.0),
            Product("3", "Camembert", "10 oz", 10.0),
            Product("4", "Brie", "10 oz", 10.0)
        )

        val expectedList = listOf(
            Product("2", "Gouda", "10 oz", 10.0),
            Product("1", "Cheddar", "10 oz", 10.0),
            Product("3", "Camembert", "10 oz", 10.0),
            Product("4", "Brie", "10 oz", 10.0)
        )

        assertEquals(expectedList, presenter.sortAndReturnData(unsortedList, SortType.ALPHABETIC_REVERSE))
    }

    @Test
    fun `sort array by numeric size order`() {
        val unsortedList = listOf(
            Product("3", "Camembert", "15 oz", 15.0),
            Product("4", "Brie", "10 oz", 10.0),
            Product("1", "Cheddar", "100 oz", 100.0),
            Product("2", "Gouda", "1 oz", 1.0)
        )

        val expectedList = listOf(
            Product("2", "Gouda", "1 oz", 1.0),
            Product("4", "Brie", "10 oz", 10.0),
            Product("3", "Camembert", "15 oz", 15.0),
            Product("1", "Cheddar", "100 oz", 100.0)
        )

        assertEquals(expectedList, presenter.sortAndReturnData(unsortedList, SortType.NUMERIC))
    }

    @Test
    fun `sort array by reverse numeric size order`() {
        val unsortedList = listOf(
            Product("3", "Camembert", "15 oz", 15.0),
            Product("4", "Brie", "10 oz", 10.0),
            Product("1", "Cheddar", "100 oz", 100.0),
            Product("2", "Gouda", "1 oz", 1.0)
        )

        val expectedList = listOf(
            Product("1", "Cheddar", "100 oz", 100.0),
            Product("3", "Camembert", "15 oz", 15.0),
            Product("4", "Brie", "10 oz", 10.0),
            Product("2", "Gouda", "1 oz", 1.0)
        )

        assertEquals(expectedList, presenter.sortAndReturnData(unsortedList, SortType.NUMERIC_REVERSE))
    }

}