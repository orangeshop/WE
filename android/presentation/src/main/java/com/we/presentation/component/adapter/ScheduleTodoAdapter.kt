package com.we.presentation.component.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.we.model.ScheduleData
import com.we.presentation.base.BaseDiffUtil
import com.we.presentation.databinding.ItemScheduleTodoBinding
import timber.log.Timber

class ScheduleTodoAdapter : ListAdapter<ScheduleData, ScheduleTodoAdapter.ScheduleTodoViewHolder>(
    BaseDiffUtil<ScheduleData>()
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ScheduleTodoViewHolder {
        val binding =
            ItemScheduleTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ScheduleTodoViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ScheduleTodoViewHolder,
        position: Int
    ) {
        holder.bind(getItem(holder.adapterPosition))
    }

    class ScheduleTodoViewHolder(
        val binding: ItemScheduleTodoBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(scheduleData: ScheduleData) {
            binding.apply {
                Timber.tag("할일").d("$scheduleData")
                this.ivCheck.isSelected = scheduleData.done
                this.scheduleData = scheduleData
            }
        }
    }
}