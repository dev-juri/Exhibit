package com.oluwafemi.exhibit.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.oluwafemi.exhibit.R
import com.oluwafemi.exhibit.databinding.ImageItemBinding

class ImageSlideAdapter(private val context: Context) :
    ListAdapter<String, ImageSlideAdapter.ViewHolder>(DiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageLink = getItem(position)
        holder.bind(imageLink, context)
    }

    class ViewHolder constructor(private val binding: ImageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(imageLink: String, context: Context) {
            Glide.with(context)
                .load(imageLink)
                .placeholder(R.drawable.ic_img)
                .centerCrop()
                .into(binding.image)
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ImageItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    companion object DiffUtilCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem.length == newItem.length
        }

    }
}
