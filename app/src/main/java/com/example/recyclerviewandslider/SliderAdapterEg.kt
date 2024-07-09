package com.example.recyclerviewandslider

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewandslider.databinding.ItemFolderBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player.REPEAT_MODE_ONE

class SliderAdapterEg : ListAdapter<SliderItems, SliderAdapterEg.SliderAdapterVH>(ModelClassDiff){

    inner class SliderAdapterVH(private val binding: ItemFolderBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(sliderItems: SliderItems){
           // binding.imageView.setImageDrawable(ContextCompat.getDrawable(itemView.context,sliderItems.image))
            val exoPlayer = ExoPlayer.Builder(itemView.context).build()
            val mediaItem = MediaItem.fromUri(sliderItems.video_url)
            binding.playerView.player = exoPlayer
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.prepare()
            exoPlayer.playWhenReady = true
            exoPlayer.repeatMode = REPEAT_MODE_ONE


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