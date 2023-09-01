package com.fuadhev.tradewave.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fuadhev.tradewave.databinding.ItemImageBinding
import com.fuadhev.tradewave.databinding.ItemProductBinding

class ImageAdapter : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>(){



    inner class ImageViewHolder(val binding:ItemImageBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(item:String){
            with(binding){
                image=item
                executePendingBindings()
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(view)
    }

    override fun getItemCount(): Int {
       return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    object ImageDiffUtilCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    val differ=AsyncListDiffer(this,ImageDiffUtilCallback)
}