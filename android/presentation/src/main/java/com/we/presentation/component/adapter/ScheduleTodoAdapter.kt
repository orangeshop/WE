package com.we.presentation.component.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.we.presentation.base.BaseDiffUtil
import com.we.presentation.databinding.ItemScheduleTodoBinding

class ScheduleTodoAdapter : ListAdapter<String, ScheduleTodoAdapter.ScheduleTodoViewHolder>(
    BaseDiffUtil<String>()
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
        holder.bind()
    }

    class ScheduleTodoViewHolder(
        val binding: ItemScheduleTodoBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.apply {

            }
        }
    }
}