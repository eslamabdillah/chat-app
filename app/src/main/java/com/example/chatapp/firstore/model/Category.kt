package com.example.chatapp.firstore.model

import com.example.chatapp.R

data class Category(val id: Int, val title: String, val imageResId: Int) {
    companion object {
        fun getCategories(): List<Category> {
            return listOf<Category>(
                Category(1, "sports", R.drawable.ic_add),
                Category(2, "movies", R.drawable.ic_add),
                Category(3, "music", R.drawable.ic_add)
            )
        }
    }
}
