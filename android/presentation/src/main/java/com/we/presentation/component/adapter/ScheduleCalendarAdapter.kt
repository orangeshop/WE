package com.we.presentation.component.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.we.presentation.R
import com.we.presentation.base.BaseDiffUtil
import com.we.presentation.databinding.ItemScheduleBinding
import com.we.presentation.schedule.model.CalendarItem
import com.we.presentation.util.CalendarType

class ScheduleCalendarAdapter :
    ListAdapter<CalendarItem, ScheduleCalendarAdapter.ScheduleViewHolder>(
        BaseDiffUtil<CalendarItem>()
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
        holder.bind(getItem(holder.adapterPosition))
    }

    class ScheduleViewHolder(
        val binding: ItemScheduleBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(calendarItem: CalendarItem) {
            binding.apply {
                date = calendarItem.date.dayOfMonth.toString()
                val context = binding.root.context
                var drawable: Int? = null
                var color: Int? = null
                when (calendarItem.calendarType) {
                    CalendarType.BEFORE -> {
                        color = context.getColor(R.color.dark_gray)
                    }

                    CalendarType.CURRENT -> {
                        color = context.getColor(R.color.black)
                    }

                    CalendarType.TODAY -> {
                        drawable = R.drawable.rectangle_we_pink_10
                        color = context.getColor(R.color.white)
                    }

                    CalendarType.AFTER -> {
                        color = context.getColor(R.color.dark_gray)
                    }
                }
                this.tvScheduleDate.apply {
                    if (drawable != null) {
                        background = ContextCompat.getDrawable(context, drawable)
                    }
                    setTextColor(color)
                }
            }
        }
    }
}