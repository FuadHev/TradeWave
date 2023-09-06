package com.fuadhev.tradewave.ui.favorite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fuadhev.tradewave.databinding.ItemFavoriteBinding
import com.fuadhev.tradewave.domain.model.FavoriteUiModel

class FavoriteAdapter: RecyclerView.Adapter<FavoriteAdapter.FavViewHolder>() {

    var onDelete: (FavoriteUiModel) -> Unit = {}
    var onClick: (Int) -> Unit = {}

    inner class FavViewHolder(val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FavoriteUiModel) {
            binding.product = item
            binding.executePendingBindings()

            binding.ibDelete.setOnClickListener {
                onDelete(item)
            }
            itemView.setOnClickListener {
                onClick(item.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        val view = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavViewHolder(view)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    object FavDiffUtilCallback : DiffUtil.ItemCallback<FavoriteUiModel>() {
        override fun areItemsTheSame(oldItem: FavoriteUiModel, newItem: FavoriteUiModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FavoriteUiModel, newItem: FavoriteUiModel): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, FavDiffUtilCallback)


}