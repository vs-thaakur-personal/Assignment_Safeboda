package com.safeboda.android.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.safeboda.android.R
import com.safeboda.android.base.BaseItemViewHolder
import com.safeboda.android.base.BaseRecyclerViewAdapter
import com.safeboda.android.databinding.ItemUserBinding
import com.safeboda.android.model.Item

abstract class BaseUserListAdapter : BaseRecyclerViewAdapter<Item, BaseItemViewHolder<Item>>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseItemViewHolder<Item> {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_user, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: BaseItemViewHolder<Item>, position: Int) {
        holder.bind(itemList[position], position)
    }

    inner class ItemViewHolder(view: View) : BaseItemViewHolder<Item>(view) {
        private val binding = ItemUserBinding.bind(view)
        override fun bind(itemData: Item, position: Int) {
            with(binding) {
                Glide.with(root.context)
                    .load(itemData.avatar_url)
                    .apply(RequestOptions.circleCropTransform())
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(avatarView)
                name.text = itemData.login
                binding.root.setOnClickListener {
                    itemClickListener?.onItemClick(itemData, position)
                }
            }
        }

    }
}