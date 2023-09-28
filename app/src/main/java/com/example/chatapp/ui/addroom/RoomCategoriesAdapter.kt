package com.example.chatapp.ui.addroom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.chatapp.databinding.ItemRoomCatagoryBinding
import com.example.chatapp.firstore.model.Category

class RoomCategoriesAdapter(val items: List<Category>) : BaseAdapter() {
    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(p0: Int): Any {
        return items[p0]

    }

    override fun getItemId(p0: Int): Long {
        return items[p0].id.toLong()
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        val viewholder: ViewHolder
        if (view == null) {
            //create ViewHolder
            val itemBinding = ItemRoomCatagoryBinding
                .inflate(
                    LayoutInflater.from(parent?.context),
                    parent, false
                )
            viewholder = ViewHolder(itemBinding)
            itemBinding.root.tag = viewholder
        } else {
            viewholder = view.tag as ViewHolder
        }
        //bind
//       viewholder.bind(items[position])
        viewholder.itemBinding.image.setImageResource(items[position].imageResId)
        viewholder.itemBinding.title.text = items[position].title
        return viewholder.itemBinding.root
    }

    class ViewHolder(val itemBinding: ItemRoomCatagoryBinding) {


    }
}