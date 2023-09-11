package com.example.onlinesalesmathproject.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinesalesmathproject.databinding.CalculationsRecyclerViewItemBinding
import com.example.onlinesalesmathproject.history.database.CalculationData

class HistoryRecyclerViewAdapter : RecyclerView.Adapter<HistoryRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: CalculationsRecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CalculationData) {
            binding.apply {
                calcuationsTextView.text = item.calculationString
            }
        }
    }

    val diffCallback = object : DiffUtil.ItemCallback<CalculationData>() {

        override fun areItemsTheSame(oldItem: CalculationData, newItem: CalculationData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: CalculationData,
            newItem: CalculationData
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