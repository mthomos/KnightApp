package com.mthomos.knightapp

import android.util.Log

class BoardLogic()
{
    var startTileInput = BoardTile()
        get() = field
        set(value) {
            field = value
        }
    var finishTileInput = BoardTile()
        get() = field
        set(value) {
            field = value
        }
    var paths = mutableListOf<Path>()
    var board = Board()

    constructor(board :Board) : this()
    {
        this.board = board
    }

    fun reset()
    {
        startTileInput = BoardTile()
        finishTileInput = BoardTile()
        paths.clear()
    }

    fun findPaths()
    {
        var nextPositions = board.getKnightNextPositions(startTileInput)
        for (position in nextPositions)
        {
            var newPath =Path(startTileInput)
            newPath.addPositon(position)
            //Log.d("PATHS", newPath.getDebugString())
            paths.add(newPath)
        }

        var newPaths1 = mutableListOf<Path>()
        for (path in paths)
        {
            var newPaths = getNewPathsFromPath(path)
            for (newPath in newPaths)
            {
                newPaths1.add(newPath)
                //Log.d("PATHS_1",newPath.getDebugString())
            }

        }


        var newPaths2 = mutableListOf<Path>()
        for (path in newPaths1)
        {
            var newPaths = getNewPathsFromPath(path)
            for (newPath in newPaths)
            {
                newPaths2.add(newPath)
                //Log.d("PATHS_2",newPath.getDebugString())
            }
        }

        paths.clear()
        for (path in newPaths2)
        {
            var last = path.getFinalPosition()
            if (last.getBoardPosition()==finishTileInput.getBoardPosition() && path.getPathSize() == 4)
            {
                paths.add(path)
                Log.d("FINAL",path.getDebugString() + "and final was " + finishTileInput.getDebugBoardPosition()+ " " + path.getPathSize().toString())
            }
        }
    }

    fun getNewPathsFromPath(path : Path) : MutableList<Path>
    {
        var result= mutableListOf<Path>()
        var nextPositions = board.getKnightNextPositions(path.getFinalPosition())
        for (position in nextPositions)
        {
            var newPath = Path()
            for (pathPoint in path.positions)
                newPath.addPositon(pathPoint)
            newPath.addPositon(position)
            result.add(newPath)
        }
        return result
    }
}