package com.example.inventory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.inventory.data.Item
import com.example.inventory.databinding.ItemListItemBinding
import java.text.NumberFormat

class ItemListAdapter(private val onItemClicked: (Item) -> Unit) :
  ListAdapter<Item, ItemListAdapter.ItemListViewHolder>(DiffCallback) {

  class ItemListViewHolder(private val binding: ItemListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Item) {
      binding.itemName.text = item.itemName
      binding.itemPrice.text = getPriceWithCurrency(item.itemPrice)
      binding.itemQuantity.text = item.quantityInStock.toString()
    }

    private fun getPriceWithCurrency(value: Double): String =
      NumberFormat.getCurrencyInstance().format(value)
  }

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): ItemListViewHolder {
    val viewHolder = ItemListViewHolder(
      ItemListItemBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
      )
    )
    viewHolder.itemView.setOnClickListener {
      val position = viewHolder.adapterPosition
      onItemClicked(getItem(position))
    }
    return viewHolder
  }

  override fun onBindViewHolder(holder: ItemListViewHolder, position: Int) {
    holder.bind(getItem(position))
  }

  companion object {
    private val DiffCallback = object : DiffUtil.ItemCallback<Item>() {
      override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.id == newItem.id
      }

      override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
      }
    }
  }
}
