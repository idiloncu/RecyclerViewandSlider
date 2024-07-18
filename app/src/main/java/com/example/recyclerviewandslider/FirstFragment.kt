package com.example.recyclerviewandslider

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.recyclerviewandslider.databinding.FragmentFirstBinding

class FirstFragment : Fragment(R.layout.fragment_first) {
    private val binding by viewBinding(FragmentFirstBinding::bind)
    private val sliderAdapterEg = SliderAdapterEg()
    val viewPagerList = arrayListOf(
        SliderItems(R.drawable.ic_launcher_foreground,"https://videos.pexels.com/video-files/3764259/3764259-hd_1280_720_60fps.mp4"),
        SliderItems(R.drawable.ic_launcher_foreground,"https://videos.pexels.com/video-files/7165500/7165500-hd_1920_1080_30fps.mp4"),
        SliderItems(R.drawable.ic_launcher_foreground,"https://videos.pexels.com/video-files/26781688/12006409_2560_1440_24fps.mp4"),
        SliderItems(R.drawable.ic_launcher_foreground,"https://videos.pexels.com/video-files/26707403/11989977_2560_1440_25fps.mp4")
    )
    //Exoplayera hepsını tek seferde ver tum linkleri -> setMediaItems
    //nextprevious ı ıncele

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.adapter = sliderAdapterEg
        sliderAdapterEg.submitList(viewPagerList)
        startPreLoadingService()
        //her video bıttgınde state ende dustugunde handler post delyaed yapısı yardımızyla control sagla
        //bir Scroll ekle
    }

    private fun startPreLoadingService() {
        val array = ArrayList<String>()
        array.addAll(viewPagerList.map { it.video_url })
        val preloadingServiceIntent = Intent(context, VideoPreLoadService::class.java)
        preloadingServiceIntent.putStringArrayListExtra(Constants.VIDEO_LIST,array)
        context?.startService(preloadingServiceIntent)
    }
    //exoplayer ekle ve burdan calıstır
    //sonra setMedıaItem yap

}