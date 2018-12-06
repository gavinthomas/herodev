package com.gavinthomas.herodatakick.ui

import android.content.res.Configuration
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import com.gavinthomas.herodatakick.R
import com.gavinthomas.herodatakick.network.DataKickApi
import com.gavinthomas.herodatakick.network.Product
import com.gavinthomas.herodatakick.presenter.ProductMvp
import com.gavinthomas.herodatakick.presenter.ProductPresenter
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.AbsListView
import com.gavinthomas.herodatakick.presenter.SortType
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), ProductMvp.View {

    private var presenter: ProductPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        initFabLayout()
        initList()
        attachPresenter()
    }

    override fun onDestroy() {
        super.onDestroy()
        //tie presenter to lifecycle
        presenter?.detachView()
    }

    private fun attachPresenter() {
        presenter = ProductPresenter(this, DataKickApi.makeRetrofitService())
        presenter?.fetchData()
        swipeContainer.isRefreshing = true
    }

    private fun initFabLayout() {
        fabSortMenuView.setSortTypePressed(object : FabSortMenuView.OnSortTypePressed {
            override fun onSortTypePressed(type: SortType) {
                presenter?.setSortType(type)
            }
        })
    }

    private fun initList() {
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) rvProductList.layoutManager =
                GridLayoutManager(this, 2)
        else rvProductList.layoutManager = LinearLayoutManager(this)

        rvProductList.adapter = ProductAdapter(arrayListOf(), this)

        rvProductList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_FLING && fabSortMenuView.isFabOpen()) {
                    fabSortMenuView.closeFABMenu()
                }
            }
        })

        swipeContainer.setOnRefreshListener { presenter?.fetchData() }
    }

    override fun updateList(items: List<Product>) {
        (rvProductList.adapter as ProductAdapter).updateList(items)
        rvProductList.scrollToPosition(0)
        rvProductList.visibility = View.VISIBLE

        errorTextView.visibility = View.GONE
    }

    override fun hideProgress() {
        swipeContainer.isRefreshing = false
    }

    override fun showError(errorMessage: String) {
        errorTextView.text = errorMessage
        errorTextView.visibility = View.VISIBLE
        rvProductList.visibility = View.GONE
    }
}
