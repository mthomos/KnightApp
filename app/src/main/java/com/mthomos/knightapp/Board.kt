package com.mthomos.knightapp

class Board ()
{
    private var size = 0
        get() = field
        private set(value) {
            field = value
        }
    private var tilesArray = mutableListOf<BoardTile>()

    constructor(size : Int) : this()
    {
       this.size = size
    }

    public fun addBoardTile(tile : BoardTile)
    {
        tilesArray.add(tile)
    }

    public fun getKnightNextPositions(tile: BoardTile): MutableList<BoardTile> {
        val x = tile.tileRow
        val y = tile.tileColumn
        var nextPositions =  mutableListOf<BoardTile>()
        if (x <size-2)
        {
            if (y< size-1)
                nextPositions.add(BoardTile(x+2, y+1))
            if (y>0)
                nextPositions.add(BoardTile(x+2, y-1))

        }
        if (x<size-1)
        {
            if (y< size-2)
                nextPositions.add(BoardTile(x+1, y+2))
            if (y>1)
                nextPositions.add(BoardTile(x+1, y-2))
        }
        if (x>0)
        {
            if (y< size-2)
                nextPositions.add(BoardTile(x-1, y+2))
            if (y>1)
                nextPositions.add(BoardTile(x-1, y-2))
        }
        if (x>1)
        {
            if (y< size-1)
                nextPositions.add(BoardTile(x-2, y+1))
            if (y>0)
                nextPositions.add(BoardTile(x-2, y-1))
        }

        return nextPositions
    }
    fun getBoardTile(x: Float, y:Float) : BoardTile
    {
        for (boardTile in tilesArray)
        {
            if (boardTile.isTouched(x.toInt(), y.toInt()))
            {
                return boardTile
            }
        }
        return BoardTile()
    }

}