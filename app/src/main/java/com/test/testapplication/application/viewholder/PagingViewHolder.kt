package com.test.testapplication.application.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.test.testapplication.data.dto.Image
import kotlinx.android.synthetic.main.item_image.view.*

class PagingViewHolder(
    private val view: View
) : RecyclerView.ViewHolder(view) {

    fun bindData(
        item: Image,
        clickItem: ((Image, () -> Unit) -> Unit)
    ) {
        Glide.with(view.image_view)
            .load(item.image)
            .apply(RequestOptions().override(450, 500))
            .into(view.image_view)

        val loadImage = {
            view.progress_bar.visibility = View.GONE
        }

        view.progress_bar.visibility = View.GONE
        view.setOnClickListener {
            view.progress_bar.visibility = View.VISIBLE
            clickItem.invoke(item, loadImage)
        }
    }
}