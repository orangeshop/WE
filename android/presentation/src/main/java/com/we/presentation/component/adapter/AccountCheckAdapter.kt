package com.we.presentation.component.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.we.model.TransactionHistoryData
import com.we.presentation.base.BaseDiffUtil
import com.we.presentation.databinding.ItemAccountCheckBinding

class AccountCheckAdapter  : ListAdapter<TransactionHistoryData, AccountCheckAdapter.AccountCheckItemViewHolder>(BaseDiffUtil<TransactionHistoryData>()) {

    inner class AccountCheckItemViewHolder(val binding: ItemAccountCheckBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: TransactionHistoryData){
            binding.apply {
                tvAccountCheckPrice.text = item.transactionBalance
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountCheckItemViewHolder {
        return AccountCheckItemViewHolder(
            ItemAccountCheckBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: AccountCheckItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}