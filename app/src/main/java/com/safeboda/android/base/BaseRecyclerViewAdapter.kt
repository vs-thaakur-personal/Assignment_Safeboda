package com.safeboda.android.base

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewAdapter<T, V : RecyclerView.ViewHolder?>(val itemList: MutableList<T> = mutableListOf())
    : RecyclerView.Adapter<V>() {

    var itemClickListener: ItemClickListener<T>? = null

    fun setItems(items: List<T>) {
        itemList.clear()
        itemList.addAll(items)
        notifyDataSetChanged()
    }

    open fun addItems(items: List<T>) {
        val lastItemPosition = itemList.size
        itemList.addAll(items)
        notifyItemRangeInserted(lastItemPosition, items.size)
    }

    fun addItem(item: T) {
        itemList.add(item)
        notifyItemInserted(itemList.size - 1)
    }

    fun addItem(position: Int, item: T) {
        itemList.add(position, item)
        notifyItemInserted(position)
    }

    fun removeItem(position: Int) {
        itemList.removeAt(position)
        notifyItemRemoved(position)
    }

    open fun getItem(position: Int): T {
        return itemList[position]
    }

    fun clear() {
        itemList.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}

abstract class BaseItemViewHolder<T>(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(itemData: T, position: Int)
}

interface ItemClickListener<T> {
    fun onItemClick(data: T, position: Int)
}