package com.fuadhev.tradewave.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fuadhev.tradewave.databinding.PrimaryProductItemBinding
import com.fuadhev.tradewave.domain.model.ProductUiModel


class RecommendAdapter : RecyclerView.Adapter<RecommendAdapter.ProductViewHolder>() {

    var onClick: (Int) -> Unit = {}

    inner class ProductViewHolder(val binding: PrimaryProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProductUiModel) {
            binding.product = item
            binding.executePendingBindings()

            itemView.setOnClickListener {
                onClick(item.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = PrimaryProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(view)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    object ProductDiffUtilCallback : DiffUtil.ItemCallback<ProductUiModel>() {
        override fun areItemsTheSame(oldItem: ProductUiModel, newItem: ProductUiModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductUiModel, newItem: ProductUiModel): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, ProductDiffUtilCallback)


}