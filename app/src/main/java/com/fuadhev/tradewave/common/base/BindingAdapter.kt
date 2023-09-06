package com.fuadhev.tradewave.common.base

import android.graphics.Paint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.fuadhev.tradewave.R
import com.fuadhev.tradewave.domain.model.FavoriteUiModel
import com.fuadhev.tradewave.domain.model.ProductUiModel
import com.google.android.material.imageview.ShapeableImageView

object BindingAdapter {

    @BindingAdapter("load_url")
    @JvmStatic
    fun loadUrl(view: ShapeableImageView, url: String?) {
        url?.let {
            Glide.with(view)
                .load(url)
                .placeholder(R.color.white)
                .into(view)
        }
    }

    @BindingAdapter("set_price")
    @JvmStatic
    fun setPrice(view: TextView, it: ProductUiModel) {
        val originalPrice = it.price + (it.price * (it.discount / 100))
        view.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        view.text =  String.format("$ %.2f", originalPrice)
    }
    @BindingAdapter("set_price")
    @JvmStatic
    fun setFavPrice(view: TextView, it: FavoriteUiModel) {
        val originalPrice = (it.price + (it.price * (it.discount / 100))).toDouble()
        view.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        view.text =  String.format("$ %.2f", originalPrice)
    }
}