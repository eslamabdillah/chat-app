package com.example.chatapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.databinding.ItemRoomBinding
import com.example.chatapp.firstore.model.Room

class RoomsRecyclerViewAdapter(var rooms: MutableList<Room>?) :
    RecyclerView.Adapter<RoomsRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(val itemRoom: ItemRoomBinding) : RecyclerView.ViewHolder(itemRoom.root) {
        val title = itemRoom.title


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemRoom = ItemRoomBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemRoom)

    }

    override fun getItemCount(): Int {
        return rooms?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = rooms!![position].title
        onitemlistener.let {
            holder.title.setOnClickListener({ view ->
                it?.onitemclick(position, rooms!!.get(position))

            })


        }

    }

    fun bindList(rooms: MutableList<Room>?) {
        this.rooms = rooms
        notifyDataSetChanged()
    }


    var onitemlistener: OnItemListener? = null

    fun interface OnItemListener {
        fun onitemclick(position: Int, get: Room)
    }


}
