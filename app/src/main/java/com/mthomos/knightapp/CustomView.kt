package com.mthomos.knightapp

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService


class CustomView : View
{
    private var mCanvas : Canvas ?= null
    private var boardSize : Int = 0
    private var boardRowsColumns = 8//(6..16).random()
    private var board : Board = Board(boardRowsColumns)
    private var boardLogic = BoardLogic(board)
    private var drawStartTile = false
    private var drawPaths = false
    private var inputs = 0
    private  var tileSize = 0

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    {
        boardSize = Resources.getSystem().displayMetrics.widthPixels
    }

    override fun onDraw(canvas : Canvas)
    {
        mCanvas = canvas
        super.onDraw(canvas)
        //Refresh Board
        createBoard()
        if (drawStartTile)
            drawStartPosition()
        if (drawPaths)
        {
            drawStartPosition()
            drawEndPosition()
            drawNewPaths()
        }
    }

    fun reset()
    {
        inputs = 0
        drawStartTile = false
        drawPaths = false
        boardLogic.reset()
        invalidate()
    }

    fun createBoard()
    {
        tileSize = boardSize / boardRowsColumns
        var paint = Paint()
        paint.color = Color.WHITE
        for (i in 0..boardRowsColumns-1)
        {
            if (boardRowsColumns%2 ==0)
            {
                if (paint.color == Color.WHITE)
                    paint.color = Color.BLACK
                else
                    paint.color = Color.WHITE
            }
            for (j in 0..boardRowsColumns-1)
            {
                if (paint.color == Color.WHITE)
                    paint.color = Color.BLACK
                else
                    paint.color = Color.WHITE

                var newRect = Rect(j*tileSize, i*tileSize, (j+1)*tileSize, (i+1)*tileSize)
                mCanvas!!.drawRect(newRect, paint)
                board.addBoardTile(BoardTile(i, j, paint.color, newRect))
            }
        }
        //Refresh board
        boardLogic.board = board
    }

    fun drawNewRandomBoard()
    {
        boardRowsColumns = (6..16).random()
        board = Board(boardRowsColumns)
        boardLogic.board = board
    }

    fun drawStartPosition()
    {
        val knightBitmap = BitmapFactory.decodeStream(this.context.getAssets().open("green_knight.jpg"))
        mCanvas!!.drawBitmap(knightBitmap!!,null, boardLogic.startTileInput.tileRect, null)
    }

    fun drawEndPosition()
    {
        val knightBitmap = BitmapFactory.decodeStream(this.context.getAssets().open("red_knight.jpg"))
        mCanvas!!.drawBitmap(knightBitmap!!,null, boardLogic.finishTileInput.tileRect, null)
    }

    fun drawNewPaths()
    {
        Log.d("KOTLIN", "draw new paths "+ boardLogic.paths.size)
        for (path in  boardLogic.paths)
        {
            var newPaint = Paint()
            newPaint.setARGB(255, (0..255).random(), (0..255).random(), (0..255).random())
            newPaint.strokeWidth = 10.0f
            var startX = 0.0f
            var startY = 0.0f
            var finishX = 0.0f
            var finishY = 0.0f
            for (i in 0..path.getPathSize()-2)
            {
                var offset = (0..50).random()
                if( startX ==0.0f)
                {
                    startX = path.getBoardTile(i).tileRow.toFloat() * tileSize + offset
                    startY = path.getBoardTile(i).tileColumn.toFloat() * tileSize + offset
                }
                else
                {
                    startX = finishX
                    startY = finishY
                }
                finishX =  path.getBoardTile(i+1).tileRow.toFloat()*tileSize + offset
                finishY=  path.getBoardTile(i+1).tileColumn.toFloat()*tileSize + offset

                mCanvas!!.drawLine(startY, startX, finishY, finishX, newPaint)
            }
        }
    }

    fun getRandomPointInsideRect(rect : Rect) : Point
    {
        var x= (rect.top ..rect.bottom).random()
        var y= (rect.left ..rect.right).random()
        return Point(x,y)
    }


    fun boardTouchInput( x : Float, y :Float) :Int
    {
        val boardRect = Rect(0, 0, boardSize, boardSize)
        if (!boardRect.contains(x.toInt(), y.toInt()))
        {
            Log.d("KOTLIN", "INPUT_OUT")
            return inputs
        }
        var tile = board.getBoardTile(x, y)
        inputs++
        if(inputs == 1)
        {
            Log.d("Marios","Start_input")
            boardLogic.startTileInput = tile
            vibratePhone(200)
            drawStartTile = true
            drawPaths = false
        }
        else if (inputs==2)
        {
            Log.d("Marios","End_input")
            boardLogic.finishTileInput = tile
            vibratePhone(300)
            drawStartTile = false
            drawPaths = true
            //prepareNewPaths
            boardLogic.findPaths()
        }
        //Draw start and finish when it should
        return inputs
    }

    fun vibratePhone(duration : Long)
    {
        var vibrator: Vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= 26)
        {
            vibrator.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE))
        }
        else
        {
            vibrator.vibrate(duration)
        }
    }
    fun getPathsSize() : Int
    {
        return boardLogic.paths.size
    }
}