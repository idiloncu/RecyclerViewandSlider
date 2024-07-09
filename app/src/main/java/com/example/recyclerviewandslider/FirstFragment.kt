package com.example.recyclerviewandslider

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.recyclerviewandslider.databinding.FragmentFirstBinding

class FirstFragment : Fragment(R.layout.fragment_first) {
    private val binding by viewBinding(FragmentFirstBinding::bind)
    private val sliderAdapterEg = SliderAdapterEg()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //    binding.viewPager.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.viewPager.adapter = sliderAdapterEg
        val viewPagerList = listOf(
            SliderItems(R.drawable.ic_launcher_foreground,"https://videos.pexels.com/video-files/1093662/1093662-hd_1920_1080_30fps.mp4"),
            SliderItems(R.drawable.ic_launcher_foreground,"https://videos.pexels.com/video-files/2759477/2759477-uhd_2560_1440_30fps.mp4")
        )


        sliderAdapterEg.submitList(viewPagerList)

    }

}