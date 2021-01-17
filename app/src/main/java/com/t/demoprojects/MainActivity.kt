package com.t.demoprojects

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.zip.Inflater

class MainActivity : AppCompatActivity(), View.OnClickListener, OnSwipeCallback,
    AdapterGenericItemClick<String> {

    private lateinit var dummyDataList:MutableList<QuestionModel>
    private lateinit var viewInflator: LayoutInflater
    private lateinit var parentView:View
    private var currentPosition:Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        llPager.setOnTouchListener(OnSwipeTouchListener(this))
        dummyDataList = getDummyData()
        /*rvPager.layoutManager = LinearLayoutManagerWithSmoothScroller(this,LinearLayoutManager.HORIZONTAL,false)
        rvPager.setHasFixedSize(true)
        rvPagerAdapter = RVPagerAdapter((getDummyData()))
        rvPager.adapter = rvPagerAdapter
        PagerSnapHelper().attachToRecyclerView(rvPager)*/

        customViewWithQuestion(0)
        prevBtn.setOnClickListener(this)
        nextBtn.setOnClickListener(this)
    }

    private fun customViewWithQuestion(position:Int) {
        this.currentPosition = position
        viewInflator = LayoutInflater.from(this) as LayoutInflater
        parentView = viewInflator.inflate(R.layout.item_card, null, false)

        parentView.findViewById<TextView>(R.id.questionTv).text = dummyDataList[position].question

        val childLayoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL, false)
        val childEventAdapter =
            dummyDataList[position].option.let { ChildOptionAdapter(dummyDataList[position]) }
        childEventAdapter?.setEventChildAdapterViewClick(this)
        parentView.findViewById<RecyclerView>(R.id.optionRv).apply {
            layoutManager = childLayoutManager
            adapter = childEventAdapter
        }


        if(llPager.childCount > 0)
            llPager.removeAllViews()
        llPager.addView(parentView, 0)
    }

    private fun getDummyData():MutableList<QuestionModel>{
        val questionList : MutableList<QuestionModel> = mutableListOf()

        questionList.add(
                QuestionModel("Do you want to die?",
                    listOf("Yes", "No")))

        questionList.add(
            QuestionModel("What is the color of sky?",
                listOf("Blue", "Red", "Green")))

        questionList.add(
            QuestionModel("What age do you want to die?",
                listOf("70", "60", "65")))

        questionList.add(
            QuestionModel("What do you love to do?",
                listOf("watching moview", "Reading", "Coding")))

        questionList.add(
            QuestionModel("What is pefection?",
                listOf("We you do your best", "When think go according to plan")))

        return questionList
    }

    private fun nextPreviousPageMovement(isNextOrPrevious:Boolean){
        //rvPager.adapter as RVPagerAdapter
        /*(rvPager?.layoutManager as LinearLayoutManager).smoothScrollToPosition(rvPager,
            RecyclerView.State(),if(isNextOrPrevious)
                rvPagerAdapter.currentPosition+1 else -(rvPagerAdapter.currentPosition - 1))*/
        if(isNextOrPrevious) customViewWithQuestion(currentPosition+1)
        else customViewWithQuestion(currentPosition - 1)
    }
    override fun onClick(view: View?) {
        when(view?.id){
            R.id.nextBtn -> nextPreviousPageMovement(true)
            R.id.prevBtn -> nextPreviousPageMovement(false)
        }
    }

    override fun onSwipeRight() {
        customViewWithQuestion(currentPosition +1)
    }

    override fun onSwipeLeft() {
        customViewWithQuestion(currentPosition - 1)
    }

    override fun onAdapterItemClickCallback(position: Int, adapterItem: String) {
        val obj = dummyDataList[currentPosition]
        obj.answer = adapterItem
        dummyDataList[currentPosition] = obj
    }
}