package com.test.testapplication.application.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.test.testapplication.R
import com.test.testapplication.application.viewholder.LinksViewHolder

class LinksAdapter(
    private val clickItem: ((String) -> Unit)
) : androidx.recyclerview.widget.RecyclerView.Adapter<LinksViewHolder>() {

    private var linksList: List<String> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinksViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_link, parent, false)
        return LinksViewHolder(view)
    }

    override fun getItemCount(): Int {
        return linksList.size
    }

    override fun onBindViewHolder(holder: LinksViewHolder, position: Int) {
        holder.bindData(linksList[position], clickItem)
    }

    fun updateData(input: List<String>) {
        linksList = input
        notifyDataSetChanged()
    }
}
