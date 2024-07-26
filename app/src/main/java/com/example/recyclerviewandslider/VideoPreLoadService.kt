package com.example.recyclerviewandslider

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import com.google.android.exoplayer2.upstream.DataSpec
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.HttpDataSource
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import com.google.android.exoplayer2.upstream.cache.CacheWriter
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class VideoPreLoadService:IntentService(VideoPreLoadService::class.java.simpleName) {

    private val TAG = "VideoPreLoadingService"
    private lateinit var mContext: Context
    private var cachingJob:Job?=null
    private var videoList:ArrayList<String>? = null
    private lateinit var httpDataSourceFactory: HttpDataSource.Factory
    private lateinit var defaultDataSourceFactory: DefaultDataSourceFactory
    private lateinit var cacheDataSourceFactory: CacheDataSource
    private val simpleCache:SimpleCache=MyApp.simpleCache
    var videoUrl: String? = null
    val videoUri = Uri.parse(videoUrl)

    override fun onHandleIntent(p0: Intent?) {
        mContext = applicationContext

        httpDataSourceFactory = DefaultHttpDataSource.Factory()
            .setAllowCrossProtocolRedirects(true)
        defaultDataSourceFactory = DefaultDataSourceFactory(this,httpDataSourceFactory)

        cacheDataSourceFactory=CacheDataSource.Factory()
            .setCache(simpleCache)
            .setUpstreamDataSourceFactory(httpDataSourceFactory)
            .createDataSource()

        if (p0 != null){
            val extras = p0.extras
            videoList = extras?.getStringArrayList(Constants.VIDEO_LIST)

            if (!videoList.isNullOrEmpty()){
                preCacheVideo(videoList)
            }
        }
    }
    private fun preCacheVideo(arrayList: ArrayList<String>?){
        if (!videoList.isNullOrEmpty()){
            videoUrl = videoList!![0]
            videoList!!.removeAt(0)
        }
        else{
            stopSelf()
        }
        if (!videoUrl.isNullOrBlank()){
            //val videoUri = Uri.parse(videoUrl)
            val dataSpec = DataSpec(videoUri)
            val progressListener =
                CacheWriter.ProgressListener { requestLength, bytesCached, newBytesCached ->
                    val downloadPercentage: Double = (bytesCached * 100.0
                            / requestLength)

                    Log.d(TAG, "downloadPercentage $downloadPercentage videoUri: $videoUri")
                }

            cachingJob = GlobalScope.launch(Dispatchers.IO){
                cacheVideo(dataSpec,progressListener)
                preCacheVideo(videoList)
            }
        }
    }
    private fun cacheVideo(
        dataSpec: DataSpec,progressListener: CacheWriter.ProgressListener
    ) {
        runCatching {
            val progressListener =
                CacheWriter.ProgressListener { requestLength, bytesCached, newBytesCached ->
                    val downloadPercentage: Double = (bytesCached * 100.0
                            / requestLength)

                    Log.d(TAG, "downloadPercentage $downloadPercentage videoUri: $videoUri")
                }
            CacheWriter(
                cacheDataSourceFactory,
                dataSpec,
                null,
                progressListener,
            ).cache()
        }.onFailure {
            it.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cachingJob?.cancel()
    }
}