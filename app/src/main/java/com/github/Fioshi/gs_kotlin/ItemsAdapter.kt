package com.github.Fioshi.gs_kotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemsAdapter(private val onItemRemoved: (ItemModel) -> Unit) :
    RecyclerView.Adapter<ItemsAdapter.ItemViewHolder>() {

    private var items = listOf<ItemModel>()
    private var filteredItems = listOf<ItemModel>()  // Lista filtrada

    // ViewHolder para os itens
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

    // Cria o ViewHolder para cada item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ItemViewHolder(view)
    }

    // Retorna o número de itens na lista filtrada
    override fun getItemCount(): Int = filteredItems.size

    // Vincula os dados do item ao ViewHolder
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = filteredItems[position]
        holder.bind(item)
    }

    // Atualiza a lista de itens e a lista filtrada
    fun updateItems(newItems: List<ItemModel>) {
        items = newItems
        filteredItems = newItems // A lista filtrada é inicialmente igual à lista completa
        notifyDataSetChanged()
    }

    // Método para filtrar os itens com base na consulta
    fun filter(query: String) {
        filteredItems = if (query.isEmpty()) {
            items // Se não houver consulta, exibe todos os itens
        } else {
            val filterPattern = query.toLowerCase().trim()
            items.filter {
                it.name.toLowerCase().contains(filterPattern) || it.desc.toLowerCase().contains(filterPattern)
            }
        }
        notifyDataSetChanged()  // Notifica o RecyclerView para atualizar a exibição
    }
}
