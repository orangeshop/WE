package com.we.presentation.component.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.we.presentation.databinding.ItemAccountBinding

class HomeViewPagerAccountAdapter(
    val item: List<String>,
    private val accountClickListener: () -> Unit,
    private val accountRemittance: () -> Unit
) : RecyclerView.Adapter<HomeViewPagerAccountAdapter.HomeViewPagerAccountViewHolder>() {
    inner class HomeViewPagerAccountViewHolder(val binding: ItemAccountBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: String){
            binding.apply {
                clItemAccount.setOnClickListener {
                    accountClickListener()
                }

                tvAccountAdapterRemittance.setOnClickListener {
                    accountRemittance()
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeViewPagerAccountViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAccountBinding.inflate(inflater, parent, false)
        return HomeViewPagerAccountViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return item.size
    }

    override fun onBindViewHolder(holder: HomeViewPagerAccountViewHolder, position: Int) {
        holder.bind(item[position])
    }
}