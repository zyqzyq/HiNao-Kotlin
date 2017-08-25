package com.zyqzyq.hinao.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zyqzyq.hinao.R
import com.zyqzyq.hinao.data.chatModel
import kotlinx.android.synthetic.main.chat_item_my.view.*


class NaoChatAdapter(val chatList: ArrayList<chatModel>) : RecyclerView.Adapter<NaoChatAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder{
        when(viewType){
            0->{
                val view = LayoutInflater.from(parent?.context)
                        .inflate(R.layout.chat_item_nao,parent,false)
                return ViewHolder(view)
            }
            else->{
                val view = LayoutInflater.from(parent?.context)
                        .inflate(R.layout.chat_item_my,parent,false)
                return ViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        with(chatList[position]){
            holder?.bindChatData(chatList[position].info)
        }
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    override fun getItemViewType(position: Int): Int {
        return chatList[position].type
    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        fun bindChatData(chatdata: String){
            with(chatdata){
                itemView.chatData.text = chatdata
            }
        }
    }
}