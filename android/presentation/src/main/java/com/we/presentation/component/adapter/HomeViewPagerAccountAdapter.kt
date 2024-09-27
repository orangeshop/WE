package com.we.presentation.component.adapter

import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuProvider
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.we.model.BankData
import com.we.presentation.base.BaseDiffUtil
import com.we.presentation.databinding.ItemAccountBinding

class HomeViewPagerAccountAdapter(

    private val accountClickListener: (idx : Int) -> Unit,
    private val accountRemittance: (accountNo : String) -> Unit,
    private val typeCheck : Boolean,
    private val moreVertClickListener : () -> Unit
) : ListAdapter<BankData, HomeViewPagerAccountAdapter.HomeViewPagerAccountViewHolder>(BaseDiffUtil<BankData>()) {
    inner class HomeViewPagerAccountViewHolder(val binding: ItemAccountBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: BankData){
            binding.apply {

                tvHomeAccount.text = item.bankName
                tvHomeNo.text = item.accountNo
                tvHomeMoney.text = item.accountBalance

//                ivMoreVert.addMenuProvider(object : MenuProvider{
//                    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
//                        TODO("Not yet implemented")
//                    }
//
//                    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
//                        TODO("Not yet implemented")
//                    }
//
//                })

                when(typeCheck){
                    true -> {
                        tvAccountAdapterRemittance.visibility = View.GONE
                        tvHomeMoney.visibility = View.GONE
                        ivReload.visibility = View.GONE
                        ivMoreVert.visibility = View.GONE
                    }
                    false -> {

                        if(currentList.lastIndex == adapterPosition){

                            tvAccountAdapterRemittance.visibility = View.GONE
                            tvHomeMoney.visibility = View.GONE
                            ivReload.visibility = View.GONE
                            tvAccountAdapterRemittance.visibility = View.GONE
                            ivMoreVert.visibility = View.GONE
                            ivRotateArrow.visibility = View.VISIBLE

                            clItemAccount.setOnClickListener {
                                accountClickListener(adapterPosition)
                            }
                        }
                        else{

                            clItemAccount.setOnClickListener {
                                accountClickListener(adapterPosition)
                            }

                            tvAccountAdapterRemittance.setOnClickListener {
                                accountRemittance(item.accountNo)
                            }

                            ivMoreVert.setOnClickListener {



//                                moreVertClickListener()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun menuSetting(){

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeViewPagerAccountViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAccountBinding.inflate(inflater, parent, false)
        return HomeViewPagerAccountViewHolder(binding)
    }


    override fun onBindViewHolder(holder: HomeViewPagerAccountViewHolder, position: Int) {
        holder.bind(currentList[position])
    }



}