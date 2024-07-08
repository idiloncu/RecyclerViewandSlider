package com.example.recyclerviewandslider

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewandslider.databinding.ItemFolderBinding

class SliderAdapterEg : ListAdapter<SliderItems, SliderAdapterEg.SliderAdapterVH>(ModelClassDiff){

    inner class SliderAdapterVH(private val binding: ItemFolderBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(sliderItems: SliderItems){
            binding.imageView.setImageDrawable(ContextCompat.getDrawable(itemView.context,sliderItems.image))


            }
        }

    object ModelClassDiff:DiffUtil.ItemCallback<SliderItems>(){
        override fun areItemsTheSame(oldItem: SliderItems, newItem: SliderItems): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: SliderItems, newItem: SliderItems): Boolean {
            return oldItem.image == newItem.image

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderAdapterVH {
        return SliderAdapterVH(ItemFolderBinding.inflate(LayoutInflater.from(parent.context),parent,false))


    }

    override fun onBindViewHolder(holder: SliderAdapterVH, position: Int) {
        holder.bind(currentList[position])


    }

}