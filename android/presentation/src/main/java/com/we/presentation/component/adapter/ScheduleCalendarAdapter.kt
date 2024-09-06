package com.we.presentation.component.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.we.presentation.base.BaseDiffUtil

class ScheduleCalendarAdapter : ListAdapter<String, RecyclerView.ViewHolder>(
    BaseDiffUtil<String>()
) {

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        TODO("Not yet implemented")
    }
}