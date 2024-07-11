package com.example.recyclerviewandslider

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.recyclerviewandslider.databinding.FragmentFirstBinding

class FirstFragment : Fragment(R.layout.fragment_first) {
    private val binding by viewBinding(FragmentFirstBinding::bind)
    private val sliderAdapterEg = SliderAdapterEg()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //    binding.viewPager.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.viewPager.adapter = sliderAdapterEg
        val viewPagerList = listOf(
            SliderItems(R.drawable.ic_launcher_foreground,"https://videos.pexels.com/video-files/3764259/3764259-hd_1280_720_60fps.mp4"),
            SliderItems(R.drawable.ic_launcher_foreground,"https://videos.pexels.com/video-files/6312162/6312162-hd_1920_1080_24fps.mp4"),
            SliderItems(R.drawable.ic_launcher_foreground,"https://videos.pexels.com/video-files/26957373/12036862_2560_1440_25fps.mp4")
        )


        sliderAdapterEg.submitList(viewPagerList)
    }
}