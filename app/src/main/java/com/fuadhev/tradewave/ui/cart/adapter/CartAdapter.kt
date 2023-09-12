package com.fuadhev.tradewave.ui.cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fuadhev.tradewave.databinding.ItemCartBinding
import com.fuadhev.tradewave.domain.model.CartUiModel

class CartAdapter : RecyclerView.Adapter<CartAdapter.CartHolder>() {

    private var count = 1
    var onDelete: (CartUiModel) -> Unit = {}
    var onClickPlus: (Int) -> Unit = {}
    var onClickMinus: (Int) -> Unit = {}

    inner class CartHolder(val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CartUiModel) {
            with(binding) {
                product = item
                quantity = count
                executePendingBindings()


                btnPlus.setOnClickListener {
                    count+=1
                    quantity=count
                    onClickPlus(item.price)
                }

                btnMinus.setOnClickListener {
                    if (count > 1) {
                        count -= 1
                        quantity = count
                        onClickMinus(item.price)
                    }
                }

                ibDelete.setOnClickListener {
                    onDelete(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartHolder {
        val view = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartHolder(view)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: CartHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    val differ = AsyncListDiffer(this, CartDiffUtil)

    object CartDiffUtil : DiffUtil.ItemCallback<CartUiModel>() {
        override fun areItemsTheSame(oldItem: CartUiModel, newItem: CartUiModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CartUiModel, newItem: CartUiModel): Boolean {
            return oldItem == newItem
        }
    }
}