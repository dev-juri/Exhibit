package com.oluwafemi.exhibit.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.oluwafemi.exhibit.R
import com.oluwafemi.exhibit.databinding.ImageSlideDialogBinding
import com.oluwafemi.exhibit.ui.adapter.ImageSlideAdapter

class ImageSlide: DialogFragment() {

    private lateinit var binding: ImageSlideDialogBinding

    override fun getTheme(): Int {
        return R.style.ModalStyle
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.isCancelable = true
        binding = ImageSlideDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments
        val imageLinks = bundle?.getStringArrayList("Exhibit")

        val imageSlideAdapter = ImageSlideAdapter(requireContext())
        imageSlideAdapter.submitList(imageLinks)
        binding.slideRecycler.apply{
            adapter = imageSlideAdapter
        }
    }
}