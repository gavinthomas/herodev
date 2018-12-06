package com.gavinthomas.herodatakick.presenter

import com.gavinthomas.herodatakick.network.Product

interface ProductMvp {

    interface View {

        fun updateList(items: List<Product>)

        fun hideProgress()

        fun showError(errorMessage: String)

    }

    interface Presenter {

        fun detachView()

        fun fetchData()

        fun setSortType(type: SortType)

    }
}