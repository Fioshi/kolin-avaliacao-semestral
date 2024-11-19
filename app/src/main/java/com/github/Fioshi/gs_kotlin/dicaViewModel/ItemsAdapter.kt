package com.github.Fioshi.gs_kotlin.dicaViewModel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.Fioshi.gs_kotlin.R
import com.github.Fioshi.gs_kotlin.dicaModel.ItemModel

class ItemsAdapter(private val onItemRemoved: (ItemModel) -> Unit) :
    RecyclerView.Adapter<ItemsAdapter.ItemViewHolder>() {

    private var items = listOf<ItemModel>()
    private var filteredItems = listOf<ItemModel>()

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView = view.findViewById<TextView>(R.id.textViewItem)
        val textDesc = view.findViewById<TextView>(R.id.textDesc)
        val button = view.findViewById<ImageButton>(R.id.imageButton)

        fun bind(item: ItemModel) {
            textView.text = item.name
            textDesc.text = item.desc
            button.setOnClickListener {
                onItemRemoved(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int = filteredItems.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = filteredItems[position]
        holder.bind(item)
    }

    fun updateItems(newItems: List<ItemModel>) {
        items = newItems
        filteredItems = newItems
        notifyDataSetChanged()
    }

    fun filter(query: String) {
        filteredItems = if (query.isEmpty()) {
            items
        } else {
            val filterPattern = query.toLowerCase().trim()
            items.filter {
                it.name.toLowerCase().contains(filterPattern) || it.desc.toLowerCase().contains(filterPattern)
            }
        }
        notifyDataSetChanged()
    }
}
