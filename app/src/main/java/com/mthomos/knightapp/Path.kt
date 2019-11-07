package com.mthomos.knightapp

class Path ()
{
    var positions = mutableListOf<BoardTile>()
        get() = field
        private set(value) {
            field = value
        }
    var namePath = ""
        get() = field
        set(value) {
            field = value
        }

    constructor(startPosition : BoardTile) : this()
    {
        positions.add(startPosition)
    }

    fun addPositon(position:BoardTile)
    {
        positions.add(position)
    }

    fun getPathSize(): Int
    {
        return positions.size
    }

    fun getStartPosition(): BoardTile
    {
        return positions.get(0)
    }

    fun getFinalPosition() : BoardTile
    {
        return positions.get(positions.size -1)
    }

    fun getBoardTile (i : Int) : BoardTile
    {
        return positions[i]
    }

    fun getDebugString() :String
    {
        var str = ""
        for (position in positions)
            str = str+ ">>" + position.getDebugBoardPosition()
        return str
    }
}