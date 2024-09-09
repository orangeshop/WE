package com.we.presentation.component.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.we.presentation.base.BaseDiffUtil
import com.we.presentation.databinding.ItemScheduleBinding
import com.we.presentation.schedule.model.ScheduleUiState

class ScheduleCalendarAdapter :
    ListAdapter<ScheduleUiState.ScheduleSet, ScheduleCalendarAdapter.ScheduleViewHolder>(
        BaseDiffUtil<ScheduleUiState.ScheduleSet>()
    ) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ScheduleViewHolder {
        val binding =
            ItemScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ScheduleViewHolder(binding)
    }


    override fun onBindViewHolder(
        holder: ScheduleViewHolder,
        position: Int
    ) {
        holder.bind()
    }

    class ScheduleViewHolder(
        val binding: ItemScheduleBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {

        }
    }
}