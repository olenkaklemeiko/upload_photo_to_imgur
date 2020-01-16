package com.test.testapplication.application.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_link.view.*


class LinksViewHolder(
    private val view: View
) : RecyclerView.ViewHolder(view) {

    fun bindData(
        item: String,
        clickItem: ((String) -> Unit)
    ) {
        Glide.with(view.image_view)
            .load(item)
            .apply(RequestOptions().override(150, 200))
            .into(view.image_view)

        view.tv_link.text = item
        view.tv_link.setOnClickListener {
            clickItem.invoke(item.apply { (item) })
        }
    }
}