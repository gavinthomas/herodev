package com.gavinthomas.herodatakick.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gavinthomas.herodatakick.R
import com.gavinthomas.herodatakick.network.Product
import kotlinx.android.synthetic.main.product_item_view.view.*

class ProductAdapter(var items: List<Product>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(context).inflate(
            R.layout.product_item_view, parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindProduct(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateList(updatedList: List<Product>) {
        items = updatedList
        notifyDataSetChanged()
    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val tvTitle = view.product_title_text
    private val tvWeight = view.product_weight_text

    fun bindProduct(item: Product) {
        tvTitle.text = item.name
        tvWeight.text = item.size;
    }
}