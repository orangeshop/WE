package com.we.presentation.component.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.we.presentation.base.BaseDiffUtil
import com.we.presentation.databinding.ItemBankBinding

class AccountBankChooseAdapter() : ListAdapter<String, AccountBankChooseAdapter.AccountBankChooseViewHolder>(BaseDiffUtil<String>()) {

    inner class AccountBankChooseViewHolder(val binding: ItemBankBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: String){
            binding.apply {

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountBankChooseViewHolder {
        return AccountBankChooseViewHolder(
            ItemBankBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: AccountBankChooseViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}