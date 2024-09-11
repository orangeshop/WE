package com.we.presentation.component.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.we.presentation.base.BaseDiffUtil
import com.we.presentation.component.adapter.InvitationAdapter.InvitationViewHolder
import com.we.presentation.databinding.ItemInvitationBinding

class InvitationAdapter : ListAdapter<String, InvitationViewHolder>(
    BaseDiffUtil<String>()
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InvitationViewHolder {
        val binding =
            ItemInvitationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InvitationViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: InvitationViewHolder,
        position: Int
    ) {
        holder.bind()
        holder.binding.emptyVisible = position == itemCount-1
    }

    class InvitationViewHolder(
        val binding: ItemInvitationBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {

            binding.apply {

            }
        }

        fun visibleCheck(position : Int){

        }
    }
}