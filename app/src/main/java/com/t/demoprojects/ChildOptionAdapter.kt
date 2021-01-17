package com.t.demoprojects

import android.text.TextUtils
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_options.view.*

class ChildOptionAdapter(private val questionModel:QuestionModel) :
    RecyclerView.Adapter<ChildOptionAdapter.ViewHolder>() {
    private val children: List<String> = questionModel.option
    private var onEventChildAdapterViewClick: AdapterGenericItemClick<String>? = null

    fun setEventChildAdapterViewClick(onEventChildAdapterViewClick:  AdapterGenericItemClick<String>) {
        this.onEventChildAdapterViewClick = onEventChildAdapterViewClick
    }

    fun getAdapterList(): List<String> {
        return children
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_options, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return children.size
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val child = children[position]

        holder.textView.text = child

        if(!TextUtils.isEmpty(questionModel.answer) && questionModel.answer?.toLowerCase() == child.toLowerCase()){
            holder.textView.setTextColor(ContextCompat.getColor(holder.textView.context, R.color.colorPrimary))
        }else{holder.textView.setTextColor(ContextCompat.getColor(holder.textView.context, android.R.color.black))

        }
        holder.textView.setOnClickListener{
            onEventChildAdapterViewClick?.onAdapterItemClickCallback(position, child)
            notifyItemChanged(position)
        }
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.optionTv

    }
}

