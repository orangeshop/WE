package com.we.presentation.component.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.we.model.BankData
import com.we.presentation.base.BaseDiffUtil
import com.we.presentation.databinding.ItemAccountBinding

class HomeViewPagerAccountAdapter(
    val item: List<BankData>,
    private val accountClickListener: (idx : Int) -> Unit,
    private val accountRemittance: () -> Unit,
    private val typeCheck : Boolean
) : ListAdapter<BankData, HomeViewPagerAccountAdapter.HomeViewPagerAccountViewHolder>(BaseDiffUtil<BankData>()) {
    inner class HomeViewPagerAccountViewHolder(val binding: ItemAccountBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: BankData){
            binding.apply {

                tvHomeAccount.text = item.bankName
                tvHomeNo.text = item.accountNo
                tvHomeMoney.text = item.accountBalance

                when(typeCheck){
                    true -> {
                        tvAccountAdapterRemittance.visibility = View.GONE
                        tvHomeMoney.visibility = View.GONE
                        ivReload.visibility = View.GONE
                    }
                    false -> {
                        clItemAccount.setOnClickListener {
                            accountClickListener(adapterPosition)
                        }

                        tvAccountAdapterRemittance.setOnClickListener {
                            accountRemittance()
                        }
                    }
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