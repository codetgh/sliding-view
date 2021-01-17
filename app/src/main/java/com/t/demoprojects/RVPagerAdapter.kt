package com.t.demoprojects

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RVPagerAdapter (val colorList: MutableList<QuestionModel>) : RecyclerView.Adapter<SampleViewHolder>() {
    private val viewPool = RecyclerView.RecycledViewPool()

    var currentPosition:Int = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampleViewHolder {
        return SampleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false))
    }

    override fun getItemCount() = colorList.size

    override fun onBindViewHolder(holder: SampleViewHolder, position: Int) {
        currentPosition = position
        val parent = colorList[position]
        holder.questionTv.text = parent.question

        val childLayoutManager = LinearLayoutManager(holder.optionRv.context,
            LinearLayoutManager.VERTICAL, false)
        //childLayoutManager.initialPrefetchItemCount = 4
        val childEventAdapter = parent?.let { ChildOptionAdapter(it) }
        //childEventAdapter?.setEventChildAdapterViewClick(onEventChildAdapterViewClick)
        holder.optionRv.apply {
            layoutManager = childLayoutManager
            adapter = childEventAdapter
            setRecycledViewPool(viewPool)
        }
    }

}

class SampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val questionTv = itemView.findViewById<TextView>(R.id.questionTv)
    val optionRv = itemView.findViewById<RecyclerView>(R.id.optionRv)
}