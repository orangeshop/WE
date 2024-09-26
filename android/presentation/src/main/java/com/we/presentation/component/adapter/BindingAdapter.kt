package com.we.presentation.component.adapter

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.we.presentation.util.addComma

@SuppressLint("SetTextI18n")
@BindingAdapter("setPrice")
fun setPrice(textView: TextView, price : Long) {
    textView.text = "${price.addComma()} 원"
}