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
        val viewPagerList = listOf(SliderItems(R.drawable.ic_launcher_foreground),SliderItems(R.drawable.ic_launcher_foreground))
        val adapterList = listOf(
            ModelClass(
                1,
                listOf(
                    "https://placehold.co/1920x1080/orange/white",
                    "https://placehold.co/1920x1080/white/orange"
                ),
                "asd"
            ),
            ModelClass(
                2,
                listOf(
                    "https://placehold.co/1920x1080/red/white",
                    "https://placehold.co/1920x1080/white/red"
                ),
                "asd"
            ),
            ModelClass(
                3,
                listOf(
                    "https://placehold.co/1920x1080/yellow/white",
                    "https://placehold.co/1920x1080/white/yellow"
                ),
                "asd"
            )
        )

        sliderAdapterEg.submitList(viewPagerList)

    }

}