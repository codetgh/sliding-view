package com.t.demoprojects

import android.content.DialogInterface
import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.view.get
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.zip.Inflater

class MainActivity : AppCompatActivity(), View.OnClickListener, OnSwipeCallback,
    AdapterGenericItemClick<OptionModel> {

    private lateinit var dummyDataList:MutableList<QuestionModel>
    private lateinit var viewInflator: LayoutInflater
    private lateinit var parentView:View
    private var currentPosition:Int = -1

    private lateinit var recyclerView:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        parentLl.setOnTouchListener(OnSwipeTouchListener(this))
        dummyDataList = getDummyData()


        prevBtn.setOnClickListener(this)
        nextBtn.setOnClickListener(this)



        customViewWithQuestion(0) //main
    }

    private fun customViewWithQuestion(position:Int) {
        this.currentPosition = position
        viewInflator = LayoutInflater.from(this) as LayoutInflater
        parentView = viewInflator.inflate(R.layout.item_card, null, false)

        //parentView.findViewById<TextView>(R.id.questionTv).text = dummyDataList[position].question
        parentView.findViewById<TextView>(R.id.questionTv).text = dummyDataList[position].question
        findViewById<Button>(R.id.nextBtn).visibility = View.VISIBLE
        findViewById<Button>(R.id.prevBtn).visibility = View.VISIBLE
        if(position == 0) {
            findViewById<Button>(R.id.prevBtn).visibility = View.GONE
        }
        else if(position == dummyDataList.size - 1) {
            findViewById<Button>(R.id.nextBtn).visibility = View.GONE
        }
        val childLayoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL, false)
        val childEventAdapter =
            dummyDataList[position].option.let {
                ChildOptionAdapter(dummyDataList[position]) }
        childEventAdapter?.setEventChildAdapterViewClick(this)
        /*parentView.findViewById<RecyclerView>(R.id.optionRv).apply {
            layoutManager = childLayoutManager
            adapter = childEventAdapter
        }*/
        recyclerView = parentView.findViewById(R.id.optionRv)
        detectSwipeOnItem(recyclerView)
        recyclerView.apply {
            layoutManager = childLayoutManager
            adapter = childEventAdapter
        }



        if(llPager.childCount > 0)
            llPager.removeAllViews()
        llPager.addView(parentView, 0)
    }

    private fun getDummyData():MutableList<QuestionModel>{
        val questionList : MutableList<QuestionModel> = mutableListOf()

        val option1 : MutableList<OptionModel> = mutableListOf()

        option1.add(OptionModel(0, 0,"Yes"))
        option1.add(OptionModel(1, 0,"Yes1"))
        option1.add(OptionModel(2, 0,"Yes2"))
        option1.add(OptionModel(1, 0,"Yes2"))
        option1.add(OptionModel(2, 0, "Yes3"))
        option1.add(OptionModel(0, 1,"No"))

        /*questionList.add(
                QuestionModel("Do you want to die?",
                    listOf("Yes", "No")))*/

        questionList.add(
            QuestionModel("Do you want to keep moving?",
                option1))

        questionList.add(
            QuestionModel("What is the color of sky?",
                arrayOf( OptionModel(0, 0,
                    "Blue")
                    , OptionModel(0,0,
                        "Red"),
                    OptionModel(0,0, "Green")).toList()))

        questionList.add(
            QuestionModel("What age do you want to die?",
                arrayOf( OptionModel(0, 0,
                    "70")
                    , OptionModel(0,0,
                        "60"),
                    OptionModel(0,0, "65")).toList()))

        questionList.add(
            QuestionModel("What do you love to do?",
                arrayOf( OptionModel(0, 0,
                    "watching moview")
                    , OptionModel(0,0,
                        "Reading"),
                OptionModel(0,0, "Coding")).toList()))

        questionList.add(
            QuestionModel("What is pefection?",
                arrayOf( OptionModel(0, 0,"We you do your best")
                , OptionModel(0,0,
                        "When think go according to plan")).toList()))
        return questionList
    }

    private fun nextPreviousPageMovement(isNextOrPrevious:Boolean){
        //rvPager.adapter as RVPagerAdapter
        /*(rvPager?.layoutManager as LinearLayoutManager).smoothScrollToPosition(rvPager,
            RecyclerView.State(),if(isNextOrPrevious)
                rvPagerAdapter.currentPosition+1 else -(rvPagerAdapter.currentPosition - 1))*/
        if(isNextOrPrevious) customViewWithQuestion(currentPosition+1) //page movement
        else customViewWithQuestion(currentPosition - 1) //page movement
    }
    override fun onClick(view: View?) {
        when(view?.id){
            R.id.nextBtn -> nextPreviousPageMovement(true) //next button
            R.id.prevBtn -> nextPreviousPageMovement(false) //previous button
        }
    }

    override fun onSwipeRight() {
        if (currentPosition < dummyDataList.size - 1)
        customViewWithQuestion(currentPosition +1) //on swipe right
    }

    override fun onSwipeLeft() {
        if (currentPosition > 0)
        customViewWithQuestion(currentPosition - 1) // on swipe left
    }

    override fun onAdapterItemClickCallback(position: Int, adapterItem: OptionModel) {
        val obj = dummyDataList[currentPosition]
        obj.answer = adapterItem.option
        dummyDataList[currentPosition] = obj
    }

    private fun getSwipeDirectionBasedOnPagePosition(): Int {
        return when (currentPosition) {
            0 -> ItemTouchHelper.LEFT
            dummyDataList.size - 1 -> ItemTouchHelper.RIGHT
            else -> ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        }
    }

    private fun detectSwipeOnItem(recyclerView: RecyclerView) {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0, getSwipeDirectionBasedOnPagePosition()
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder,
                                  direction: Int) {}

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                // Swiping to the right but actually it is left
                if (dX > 0) {
                    onSwipeLeft()
                }
                // Swiping to the left but actually it is right
                else if (dX < 0) {
                    onSwipeRight()
                }
            }
        }).attachToRecyclerView(recyclerView)
    }
}