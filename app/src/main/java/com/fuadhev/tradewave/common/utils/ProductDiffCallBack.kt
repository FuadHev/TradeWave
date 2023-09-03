package com.fuadhev.tradewave.common.utils

import androidx.recyclerview.widget.DiffUtil
import com.fuadhev.tradewave.domain.model.ProductUiModel


object ProductDiffCallBack : DiffUtil.ItemCallback<ProductUiModel>() {
    override fun areItemsTheSame(oldItem: ProductUiModel, newItem: ProductUiModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ProductUiModel, newItem: ProductUiModel): Boolean {
        return oldItem == newItem
    }
}
