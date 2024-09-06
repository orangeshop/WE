package com.we.presentation.component.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.we.presentation.databinding.ItemBankBinding

class AccountBankChooseAdapter() : ListAdapter<String, AccountBankChooseAdapter.AccountBankChooseViewHolder>(diffUtil) {

    inner class AccountBankChooseViewHolder(val binding: ItemBankBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: String){
            binding.apply {
                tvItemBank.text = item
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

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<String>(){
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

        }
    }


}