package com.mthomos.knightapp

import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout


class MainActivity() : AppCompatActivity()
{
    private var context: Context ? = null
    private var customView : CustomView ?=null
    private var newBoardButton : Button?= null
    private var resetButton : Button ?= null
    private var knightTextView : TextView ?= null
    private var heightLayout = -1

    private var setStartText = "Set Knight starting position"
    private var setEndText = "Set Knight ending position"
    private var resetText = "Reset the board"

    override fun onCreate(savedInstanceState: Bundle?)
    {
        context = this
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        knightTextView = findViewById(R.id.knightTextView)
        knightTextView!!.text = setStartText
        customView = findViewById(R.id.customView)
        newBoardButton = findViewById(R.id.boardButton)
        newBoardButton!!.setOnClickListener(object : View.OnClickListener
        {
            override fun onClick(v: View)
            {
                customView!!.reset()
                customView!!.drawNewRandomBoard()
                customView!!.invalidate()
                knightTextView!!.text = setStartText
            }
        })

        resetButton = findViewById(R.id.resetButton)
        resetButton!!.setOnClickListener(object : View.OnClickListener
        {
            override fun onClick(v: View)
            {
                customView!!.invalidate()
                customView!!.reset()
                knightTextView!!.text = setStartText
            }
        })

        var layout = findViewById<ConstraintLayout>(R.id.main_layout)
        val vto = layout.viewTreeObserver
        vto.addOnGlobalLayoutListener(object : OnGlobalLayoutListener
        {
            override fun onGlobalLayout()
            {
                layout.viewTreeObserver.removeOnGlobalLayoutListener(this)
                heightLayout = layout.measuredHeight
            }
        })
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean
    {
        if(event!!.action == MotionEvent.ACTION_UP)
        {
            val x = event.rawX
            val y = event.rawY
            var result = customView!!.boardTouchInput(x,y-(Resources.getSystem().displayMetrics.heightPixels- heightLayout))
            if (result == 1)
            {
                knightTextView!!.text = setEndText
                customView!!.invalidate()
            }
            else if (result == 2)
            {
                knightTextView!!.text = resetText
                knightTextView!!.text = "Found " + customView!!.getPathsSize() +  " paths"
                customView!!.invalidate()
            }
            return true
        }
        else
            return false
    }

    public fun refresheText()
    {

    }
}
