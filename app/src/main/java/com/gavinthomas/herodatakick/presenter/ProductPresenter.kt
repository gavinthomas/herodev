package com.gavinthomas.herodatakick.presenter

import com.gavinthomas.herodatakick.network.DataKickService
import com.gavinthomas.herodatakick.network.Product
import com.gavinthomas.herodatakick.network.nameSelector
import com.gavinthomas.herodatakick.network.sizeSelector
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.Main
import kotlinx.coroutines.experimental.launch
import java.lang.Exception

class ProductPresenter(var view: ProductMvp.View, private var service: DataKickService) : ProductMvp.Presenter {

    private lateinit var job: Job
    private var productView: ProductMvp.View? = view
    private var listData = listOf<Product>()
    private var sortType = SortType.ALPHABETIC

    override fun detachView() {
        //on detach remove reference to view and cancel Job
        productView = null
        job.cancel()
    }

    override fun fetchData() {
        job = GlobalScope.launch(Dispatchers.Main) {
            val request = service.getItems()
            try {
                val response = request.await()

                //check if response is successful and response.body is not null before updating list
                if (response.isSuccessful) {
                    response.body()?.let { list ->
                        //hide progress
                        productView?.hideProgress()
                        //store list data in member variable to allow user sorting
                        listData = sanitiseData(list)
                        productView?.updateList(sortAndReturnData(listData, sortType))
                        return@launch
                    }
                }
            } catch (e: Exception) {
                handleError()
            }
            handleError()
        }
    }

    /**
     * On error, clear locally stored list data, hide progress and show error view
     */
    private fun handleError() {
        listData = listOf()
        productView?.hideProgress()
        productView?.showError("Error retrieving products")
    }

    override fun setSortType(type: SortType) {
        sortType = type
        if (listData.isNotEmpty()) productView?.updateList(sortAndReturnData(listData, sortType))
        else handleError()
    }

    /**
     * API has no strict requirements for what users can submit so there are a lot of different formats returned.
     * This method basically filters out (hopefully) all of the nonsensical or missing entries and returns a double "oz" value
     */
    fun sanitiseData(items: List<Product>): List<Product> {
        val data = items
            .asSequence()
            .filter { !it.size.isNullOrEmpty() }
            .filter { it.size!!.contains("oz") }
            .filter { !it.size!!.contains("fl") }
            .filter { !it.size!!.contains("/") }.toList()

        data.forEach {
            it.sizeValue = it.size!!.split("oz")[0].trim().toDouble()
        }
        return data
    }

    /**
     * Sorts elements in the array in-place according to natural sort order of the value returned by specified [selector] function.
     * Returns sorted list of products
     */
    fun sortAndReturnData(items: List<Product>, type: SortType): List<Product> = when (type) {
        SortType.ALPHABETIC -> items.sortedBy { nameSelector(it) }
        SortType.ALPHABETIC_REVERSE -> items.sortedByDescending { nameSelector(it) }
        SortType.NUMERIC -> items.sortedBy { sizeSelector(it) }
        SortType.NUMERIC_REVERSE -> items.sortedByDescending { sizeSelector(it) }
    }


}