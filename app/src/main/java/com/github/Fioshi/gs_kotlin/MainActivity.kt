package com.github.Fioshi.gs_kotlin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ItemsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Eco Dicas"

        val buttonInte = findViewById<Button>(R.id.integrantes)
        val searchView = findViewById<SearchView>(R.id.searchView)

        buttonInte.setOnClickListener {
            val intent = Intent(this, IntegrantesActivity::class.java)
            startActivity(intent)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val itemsAdapter = ItemsAdapter { item ->
            viewModel.removeItem(item)
        }
        recyclerView.adapter = itemsAdapter

        val button = findViewById<Button>(R.id.button)
        val editText = findViewById<EditText>(R.id.editText)
        val desc = findViewById<EditText>(R.id.editTextDesc)

        button.setOnClickListener {
            if (editText.text.isEmpty()) {
                editText.error = "Preencha um valor"
                return@setOnClickListener
            }
            viewModel.addItem(editText.text.toString(), desc.text.toString())
            editText.text.clear()
            desc.text.clear()
        }

        val viewModelFactory = ItemsViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ItemsViewModel::class.java)

        viewModel.itemsLiveData.observe(this) { items ->
            itemsAdapter.updateItems(items)
        }

        // Configurar o SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    itemsAdapter.filter(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    itemsAdapter.filter(it)
                }
                return true
            }
        })
    }
}