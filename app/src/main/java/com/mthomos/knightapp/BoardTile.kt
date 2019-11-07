package com.mthomos.knightapp

import android.graphics.Color
import android.graphics.Rect

class BoardTile () {
    var tileRow = -1
        get() = field
        set(value) {
            field = value
        }
    var tileColumn = -1
        get() = field
        set(value) {
            field = value
        }

    var tileColor: Int = Color.RED
        get() = field
        set(value) {
            field = value
        }
    var tileRect: Rect = Rect()
        get() = field
        set(value) {
            field = value
        }

    constructor(row: Int, col: Int) : this()
    {
        tileRow = row
        tileColumn = col
    }

    constructor(row: Int, col: Int, color: Int, rect: Rect) : this() {
        tileRow = row
        tileColumn = col
        tileColor = color
        tileRect = rect
    }

    public fun getColumnString(): String {
        when (tileColumn) {
            0 -> return "A"
            1 -> return "B"
            2 -> return "C"
            3 -> return "D"
            4 -> return "E"
            5 -> return "F"
            6 -> return "G"
            7 -> return "H"
            8 -> return "I"
            9 -> return "J"
            10 -> return "K"
            11 -> return "L"
            12 -> return "M"
            13 -> return "N"
            14 -> return "O"
            15 -> return "P"
            else -> return "-1"
        }
    }

    public fun getRowString(): String {
        return (tileRow+1).toString()
    }

    public fun getBoardPosition(): String
    {
        return getColumnString()+getRowString()
    }

    public fun getDebugBoardPosition() :String
    {
        return tileRow.toString() +"_" + tileColumn.toString()
    }

    public fun isDark() : Boolean
    {
        if (tileColor == Color.BLACK)
            return true
        else
            return false
    }

    public fun isTouched(x : Int, y : Int) : Boolean
    {
        return tileRect.contains(x, y);
    }
}
