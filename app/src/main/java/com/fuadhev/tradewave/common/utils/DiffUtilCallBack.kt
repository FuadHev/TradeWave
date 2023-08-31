package com.fuadhev.tradewave.common.utils

import androidx.recyclerview.widget.DiffUtil
import com.fuadhev.tradewave.data.network.dto.ProductDTO

object DiffUtilCallback : DiffUtil.ItemCallback<ProductDTO>() {
    override fun areItemsTheSame(
        oldItem: ProductDTO,
        newItem: ProductDTO,
    ): Boolean {
        return oldItem.id==newItem.id
    }

    override fun areContentsTheSame(
        oldItem: ProductDTO,
        newItem: ProductDTO,
    ): Boolean {
        return oldItem==newItem
    }
}