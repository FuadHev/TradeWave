package com.fuadhev.tradewave.ui.profile.payment.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fuadhev.tradewave.databinding.ItemCardBinding
import com.fuadhev.tradewave.databinding.ItemCategoryBinding
import com.fuadhev.tradewave.domain.model.CardUiModel
import com.fuadhev.tradewave.domain.model.CategoryUiModel
import com.fuadhev.tradewave.ui.home.adapter.CategoryAdapter

class CardAdapter: RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

//    var onClick: () -> Unit = {}
    inner class CardViewHolder(val binding: ItemCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CardUiModel) {

            with(binding){
                card = item
                executePendingBindings()
            }

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardViewHolder(view)
    }
    override fun getItemCount(): Int {
        return differ.currentList.size
    }
    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(differ.currentList[position])

    }

    object CardDiffUtilCallback : DiffUtil.ItemCallback<CardUiModel>() {
        override fun areItemsTheSame(oldItem: CardUiModel, newItem: CardUiModel): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(
            oldItem: CardUiModel,
            newItem: CardUiModel,
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, CardDiffUtilCallback)


}
