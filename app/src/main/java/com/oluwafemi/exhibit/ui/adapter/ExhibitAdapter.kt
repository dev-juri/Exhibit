package com.oluwafemi.exhibit.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.oluwafemi.exhibit.R
import com.oluwafemi.exhibit.data.Exhibit
import com.oluwafemi.exhibit.databinding.ExhibitLayoutBinding

class ExhibitAdapter(private val context: Context, private val onClickListener: ClickListener) :
    ListAdapter<Exhibit, ExhibitAdapter.ExhibitViewHolder>(DiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExhibitViewHolder =
        ExhibitViewHolder.from(parent)

    override fun onBindViewHolder(holder: ExhibitViewHolder, position: Int) {
        val exhibit = getItem(position)
        holder.bind(exhibit, context, onClickListener)
    }

    class ExhibitViewHolder constructor(private val binding: ExhibitLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(exhibit: Exhibit, context: Context, onClickListener: ClickListener) {
            binding.title.text = exhibit.title
            Glide.with(context)
                .load(exhibit.images[0])
                .placeholder(R.drawable.ic_img)
                .centerCrop()
                .into(binding.image)
            binding.parent.setOnClickListener {
                onClickListener.onExhibitClick(exhibit)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ExhibitViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ExhibitLayoutBinding.inflate(layoutInflater, parent, false)
                return ExhibitViewHolder(binding)
            }
        }
    }

    companion object DiffUtilCallback : DiffUtil.ItemCallback<Exhibit>() {
        override fun areItemsTheSame(oldItem: Exhibit, newItem: Exhibit): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Exhibit, newItem: Exhibit): Boolean {
            return oldItem.images == newItem.images
        }

    }
}

interface ClickListener {
    fun onExhibitClick(exhibit: Exhibit)
}