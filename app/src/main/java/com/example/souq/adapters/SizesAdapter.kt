package com.example.souq.adapters


import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.souq.databinding.SizeRvItemBinding

class SizesAdapter : RecyclerView.Adapter<SizesAdapter.SizesViewholder>() {
    private var selectedPosition = -1

    inner class SizesViewholder(private val binding: SizeRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(size: String, postion: Int) {

            binding.tvSize.text=size

            if (postion == selectedPosition) {
                binding.apply {
                    //size is selected
                    imageShadow.visibility = View.VISIBLE
                }

            } else {
                binding.apply {
                    //size is not selected

                    imageShadow.visibility = View.INVISIBLE
                }
            }

        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }


    }

    val differ = AsyncListDiffer(this, diffCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SizesViewholder {
        return SizesViewholder(
            SizeRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: SizesViewholder, position: Int) {
        val size = differ.currentList[position]
        holder.bind(size, position)

        // note4

        holder.itemView.setOnClickListener {
            if (selectedPosition >= 0)
                notifyItemChanged(selectedPosition)
            selectedPosition = holder.adapterPosition
            notifyItemChanged(selectedPosition)
            //??
            onItemCLick?.invoke(size)
        }
    }

    //???????? note5
    var onItemCLick: ((String) -> Unit)? = null
}