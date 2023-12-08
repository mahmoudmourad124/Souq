package com.example.souq.adapters

import android.graphics.drawable.ColorDrawable
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.souq.databinding.ColorRvItemBinding

class ColorsAdapter : RecyclerView.Adapter<ColorsAdapter.ColorsViewholder>() {
    private var selectedPosition = -1

    inner class ColorsViewholder(private val binding: ColorRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(color: Int, postion: Int) {

            val imageDrawable = ColorDrawable(color)
            binding.imageColor.setImageDrawable(imageDrawable)

            if (postion == selectedPosition) {
                binding.apply {
                    imageShadow.visibility = View.VISIBLE
                    imagePicked.visibility = View.VISIBLE
                }

            } else {
                binding.apply {
                    imageShadow.visibility = View.INVISIBLE
                    imagePicked.visibility = View.INVISIBLE
                }
            }

        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Int>() {
        override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }


    }

    val differ = AsyncListDiffer(this, diffCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorsViewholder {
        return ColorsViewholder(
            ColorRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ColorsViewholder, position: Int) {
        val color = differ.currentList[position]
        holder.bind(color, position)


        holder.itemView.setOnClickListener {
            if (selectedPosition >= 0)
                notifyItemChanged(selectedPosition)
            selectedPosition = holder.adapterPosition
            notifyItemChanged(selectedPosition)
            //??
            onItemCLick?.invoke(color)
        }
    }

    var onItemCLick: ((Int) -> Unit)? = null
}