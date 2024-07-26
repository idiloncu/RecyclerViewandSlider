package com.example.recyclerviewandslider

import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewandslider.databinding.ItemFolderBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.Player.REPEAT_MODE_ONE
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import kotlinx.coroutines.withContext

class SliderAdapterEg : ListAdapter<SliderItems, SliderAdapterEg.SliderAdapterVH>(ModelClassDiff){
    inner class SliderAdapterVH(private val binding: ItemFolderBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(sliderItems: SliderItems){
            val exoPlayer = ExoPlayer.Builder(itemView.context).build()
            val mediaItem = MediaItem.fromUri(sliderItems.video_url)
            binding.playerView.player = exoPlayer
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.prepare()
            exoPlayer?.playWhenReady = true

            val handler = Handler()
            val runnable = object :Runnable{
                override fun run() {
                    if (exoPlayer.currentPosition >= 10000){
                        val nextPosition = bindingAdapterPosition + 1
                        if (nextPosition < currentList.size){
                            val nextItem = getItem(nextPosition)
                            val nextMediaItem = MediaItem.fromUri(nextItem.video_url)
                            exoPlayer.setMediaItem(nextMediaItem)
                            exoPlayer.prepare()
                            exoPlayer.playWhenReady = true
                        }
                    }
                    handler.postDelayed(this,1000)
                }
            }
            handler.post(runnable)

            exoPlayer.addListener(object :Player.Listener{
                override fun onPlaybackStateChanged(playbackState: Int) {
                    super.onPlaybackStateChanged(playbackState)
                    if (playbackState == Player.STATE_ENDED){
                        val nextPosition = bindingAdapterPosition + 1
                        if (nextPosition < currentList.size){
                            val nextItem =getItem(nextPosition)
                            val nextMediaItem = MediaItem.fromUri(nextItem.video_url)
                            exoPlayer.setMediaItem(nextMediaItem)
                            exoPlayer.prepare()
                            exoPlayer.playWhenReady = true
                        }
                    }
                }
            })
            }
        }
    override fun onViewRecycled(holder: SliderAdapterVH) {
        super.onViewRecycled(holder)

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