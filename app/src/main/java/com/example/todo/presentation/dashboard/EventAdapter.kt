package com.example.todo.presentation.dashboard

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.core.utilities.ISendAction
import com.example.todo.core.utilities.showMenu
import com.example.todo.databinding.EventViewBinding
import com.example.todo.domain.models.EventModel

class EventAdapter(private val iSendAction: ISendAction) :
    RecyclerView.Adapter<EventAdapter.MyViewHolder>() {

    fun removeEvent(pos : Int){
        differ.currentList.removeAt(pos)
        notifyItemRemoved(pos)
        notifyItemRangeChanged(pos,itemCount)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = EventViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(parent.context, binding, iSendAction)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val event = differ.currentList[position]
        holder.bindHolder(event,position)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class MyViewHolder(
        private val context: Context,
        private val eventViewBinding: EventViewBinding,
        private val iSendAction: ISendAction
    ) : RecyclerView.ViewHolder(eventViewBinding.root) {

        var pos: Int = -1

        interface ITitleFinder {
            fun getAction(a: String)
        }

        fun bindHolder(event: EventModel, position: Int) {
            eventViewBinding.apply {
                eventTV.text = event.eventName
                eventDecTV.text = event.eventDesc
                menuImg.setOnClickListener {
                    menuImg.showMenu(context, titleFinder)
                    pos = position
                }
            }
        }

        private val titleFinder = object : ITitleFinder {
            override fun getAction(a: String) {
                iSendAction.data(a, pos)
            }
        }

    }

    private val differCallback = object : DiffUtil.ItemCallback<EventModel>() {
        override fun areItemsTheSame(oldItem: EventModel, newItem: EventModel): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: EventModel, newItem: EventModel): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

}