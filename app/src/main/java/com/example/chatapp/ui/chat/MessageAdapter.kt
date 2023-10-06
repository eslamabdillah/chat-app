package com.example.chatapp.ui.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.SessionProvider
import com.example.chatapp.databinding.ItemRecevieMessageBinding
import com.example.chatapp.databinding.ItemSendMessageBinding
import com.example.chatapp.firstore.model.Message


enum class MessageType(val value: Int) {
    Received(200),
    sent(100)
}

class MessageAdapter(var messages: MutableList<Message>? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    class SentMessageViewHolder(val itemBinding: ItemSendMessageBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(message: Message) {
            itemBinding.messageDataBinding = message
        }
    }

    class ReceivedMessageViewHolder(val itemBinding: ItemRecevieMessageBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(message: Message) {
            itemBinding.messageDataBinding = message
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == MessageType.sent.value) {
            val itemBinding =
                ItemSendMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return SentMessageViewHolder(itemBinding)
        } else {
            val itemBinding = ItemRecevieMessageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return ReceivedMessageViewHolder(itemBinding)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val message = messages?.get(position)
        if (message?.senderId == SessionProvider.user?.id) {
            return MessageType.sent.value
        } else {
            return MessageType.Received.value
        }
    }


    override fun getItemCount(): Int = messages?.size ?: 0

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val messagee = messages?.get(position)
        if (messagee != null) {
            //use when to best
            if (holder is SentMessageViewHolder) {
                //smart cast
                holder.bind(messagee)
            } else if (holder is ReceivedMessageViewHolder) {
                //smart cast
                holder.bind(messagee)
            }
        }
    }

    fun addnewMessage(newMessage: MutableList<Message>) {
        if (messages == null) {
            messages = mutableListOf()
        }
        val oldSize = messages?.size ?: 0
        messages?.addAll(newMessage)
        notifyItemRangeInserted(oldSize, newMessage.size)
    }


}




