package com.example.tictactoe

class MiniMax {

    companion object {
        fun isMovesLeft(arr:Array<IntArray>): Boolean {
            for(i in 0 until 3)
                for (j in 0 until 3)
                    if(arr[i][j]==2)
                        return true
            return false
        }

        fun evaluate(a:Array<IntArray>):Int {

            for(i in 0 until 3) {
                if(a[i][0] == a[i][1] && a[i][1] == a[i][2]) {
                    if (a[i][0]==0)
                        return +10
                    else if (a[i][0]==1)
                        return -10
                }
            }
            for(i in 0 until 3) {
                if(a[0][i] == a[1][i] && a[1][i] == a[2][i]) {
                    if (a[0][i]==0)
                        return +10
                    else if (a[0][i]==1)
                        return -10
                }
            }
            if(a[0][0] == a[1][1] && a[1][1] == a[2][2]) {
                if(a[0][0] == 0)
                    return +10
                else if (a[0][0] == 1)
                    return -10
            }
            if(a[0][2] == a[1][1] && a[1][1] == a[2][0]) {
                if(a[0][2] == 0)
                    return +10
                else if (a[0][2] == 1)
                    return -10
            }
            return 0
        }

        fun minmax(arr:Array<IntArray>, depth:Int, isMax:Boolean):Int {

            var score = evaluate(arr)

            if(score == 10) {
                return score
            }

            if(score == -10) {
                return score
            }

            if(isMovesLeft(arr) == false) {
                return 0
            }

            if(isMax) {
                var best = -10000
                for(i in 0 until 3) {
                    for(j in 0 until 3) {
                        if(arr[i][j]==2) {
                            arr[i][j] = 0
                            best = Math.max(best, minmax(arr, depth+1, !isMax))
                            arr[i][j] = 2
                        }
                    }
                }
                return best
            }
            else {
                var best = 10000
                for(i in 0 until 3) {
                    for(j in 0 until 3) {
                        if(arr[i][j]==2) {
                            arr[i][j] = 1
                            best = Math.max(best, minmax(arr, depth+1, !isMax))
                            arr[i][j] = 2
                        }
                    }
                }
                return best
            }

        }

        fun findBestMove(gameState:IntArray):Int{
            var bestVal = -1000
            var bestsmoveRow = -1
            var bestsmoveCol = -1
            var array = turn1Dto2D(gameState)

            for(i in 0 until 3) {
                for(j in 0 until 3) {
                    if(array[i][j] == 2) {
                        array[i][j] = 0
                        var moveVal = minmax(array, 0 ,false)
                        if(moveVal > bestVal) {
                            bestsmoveRow = i
                            bestsmoveCol = j
                            bestVal = moveVal
                        }
                    }
                }
            }
            return bestsmoveRow * 3 + bestsmoveCol
        }

        fun turn1Dto2D(gameState:IntArray):Array<IntArray>{
            var arr = Array(3) {IntArray(3) }
            var index = 0
            for(i in 0 until 3) {
                for (j in 0 until 3) {
                    arr[i][j] = gameState[index++]
                }
            }
            return arr
        }
    }
}