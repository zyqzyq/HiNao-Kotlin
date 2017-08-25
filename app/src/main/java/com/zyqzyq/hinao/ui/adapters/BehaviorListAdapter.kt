package com.zyqzyq.hinao.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.zyqzyq.hinao.R
import android.view.View
import com.zyqzyq.hinao.data.startBehavior
import kotlinx.android.synthetic.main.behavior_item.view.*

class BehaviorListAdapter(val items: List<String>)
    : RecyclerView.Adapter<BehaviorListAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder{
        val view = LayoutInflater.from(parent?.context)
                .inflate(R.layout.behavior_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        with(items[position]){
            holder?.bindBehavior(items[position])
        }
    }
    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        fun bindBehavior(behavior: String){
            with(behavior){
                itemView.behaviorName.text = behavior
                itemView.runBehaviorBtn.setOnClickListener { startBehavior(behavior) }
            }
        }
    }
}