package com.test.testapplication.application.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.test.testapplication.R
import com.test.testapplication.application.viewholder.PagingViewHolder
import com.test.testapplication.data.dto.Image

class PagingAdapter(
    diffCallback: DiffUtil.ItemCallback<Image>,
    private val clickItem: ((Image, () -> Unit) -> Unit)
) : PagedListAdapter<Image, PagingViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagingViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_image, parent, false)
        return PagingViewHolder(view)
    }

    override fun onBindViewHolder(holder: PagingViewHolder, position: Int) {
        getItem(position)?.let { holder.bindData(it, clickItem) }
    }
}