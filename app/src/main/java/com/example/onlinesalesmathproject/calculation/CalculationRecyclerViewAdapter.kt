package com.example.onlinesalesmathproject.calculation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinesalesmathproject.databinding.CalculationsRecyclerViewItemBinding

class CalculationRecyclerViewAdapter :
    RecyclerView.Adapter<CalculationRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: CalculationsRecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: String) {
            binding.apply {
                calcuationsTextView.text = item

            }
        }
    }

    val diffCallback = object : DiffUtil.ItemCallback<String>() {

        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: String,
            newItem: String
        ): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CalculationsRecyclerViewItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = differ.currentList[position]

        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}