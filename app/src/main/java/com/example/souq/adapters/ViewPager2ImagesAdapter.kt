package com.example.souq.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.souq.databinding.ViewpagerImageItemBinding

class ViewPager2ImagesAdapter :
    RecyclerView.Adapter<ViewPager2ImagesAdapter.Viewpager2Viewholder>() {
    inner class Viewpager2Viewholder(private val binding: ViewpagerImageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(imagePath:String){
            Glide.with(itemView).load(imagePath).into(binding.imageProductDetails)

        }    }



    private val diiffCallbacks = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, diiffCallbacks)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewpager2Viewholder {
return Viewpager2Viewholder(
    ViewpagerImageItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
)
    }

    override fun getItemCount(): Int {
return differ.currentList.size   }

    override fun onBindViewHolder(holder: Viewpager2Viewholder, position: Int) {
        val image=differ.currentList[position]
        holder.bind(image)
    }

}