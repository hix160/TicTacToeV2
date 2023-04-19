package com.example.tictactoe

import kotlin.random.Random
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var player1score: TextView
    private lateinit var player2score: TextView
    private lateinit var status: TextView
    private lateinit var buttons: Array<Button>
    private lateinit var reset: Button
    private lateinit var playAgain: Button
    private var player1ScoreCount: Int = 0
    private var player2ScoreCount: Int = 0

    private var playerOneActive: Boolean = true

    private var gameState: IntArray = intArrayOf(2, 2, 2, 2, 2, 2, 2, 2, 2)

    private val winingPos = arrayOf(
        intArrayOf(0, 1, 2), intArrayOf(3, 4, 5),
        intArrayOf(6, 7, 8), intArrayOf(0, 3, 6),
        intArrayOf(1, 4, 7), intArrayOf(2, 5, 8),
        intArrayOf(0, 4, 8), intArrayOf(2, 4, 6)
    )

    private var rounds = 0


    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        player1score = findViewById(R.id.text_player1_score)
        player2score = findViewById(R.id.text_player2_score)
        status = findViewById(R.id.text_status)
        reset = findViewById(R.id.game_reset)
        playAgain = findViewById(R.id.game_play)
        player1ScoreCount = 0
        player2ScoreCount = 0

        buttons = arrayOf(
            findViewById(R.id.btn0),
            findViewById(R.id.btn1),
            findViewById(R.id.btn2),
            findViewById(R.id.btn3),
            findViewById(R.id.btn4),
            findViewById(R.id.btn5),
            findViewById(R.id.btn6),
            findViewById(R.id.btn7),
            findViewById(R.id.btn8)
        )

        val name1 = findViewById<EditText>(R.id.text_player1)
        val name2 = findViewById<EditText>(R.id.text_player2)
        val playAginstAI: Switch = findViewById(R.id.switch_ai)


        for (button in buttons) {
            button.setOnClickListener {
                if (!button.text.equals(""))
                    return@setOnClickListener
                else if (checkWinner())
                    return@setOnClickListener
                var buttonID = button.id
                var gameStatePointer = buttonID - buttons[0].id
                if (playerOneActive) {
                    button.text = "X"

                    gameState[gameStatePointer] = 0

                } else if (playAginstAI.isChecked) {
                    var aiMove = MiniMax.findBestMove(gameState)
                    if(Random.nextBoolean())
                        aiMove = randomeMove()

                    var button = buttons[aiMove]
                    var gameStatePointer = button.id - buttons[0].id
                    button.text = "O"

                    gameState[gameStatePointer] = 1
                } else {
                    button.text = "O"

                    gameState[gameStatePointer] = 1
                }

                rounds++

                if (checkWinner()) {
                    if (playerOneActive) {
                        player1ScoreCount++
                        updatePlayerScore()
                        status.text = name1.editableText.toString() + " has Won"
                    } else {
                        player2ScoreCount++
                        updatePlayerScore()
                        status.text = name2.editableText.toString() + " has Won"
                    }
                } else if (rounds == 9) {
                    status.text = "No Winner"
                } else {
                    playerOneActive = !playerOneActive
                }

                reset.setOnClickListener {
                    playGameAgain()
                    resetGame()
                    updatePlayerScore()
                }
                playAgain.setOnClickListener {
                    playGameAgain()
                }

            }
        }
    }

    private fun randomeMove(): Int {
        var go = true
        val minValue = 0
        val maxValue = 9
        var randomInt: Int = 0
        while(go) {
            randomInt = Random.nextInt(minValue, maxValue)
            for(i in 0 until gameState.size) {
                if(gameState[randomInt] == 2)
                    go = false
            }
        }
        return randomInt
    }

    private fun resetGame() {
        val name1 = findViewById<EditText>(R.id.text_player1)
        val name2 = findViewById<EditText>(R.id.text_player2)
        player1ScoreCount = 0
        player2ScoreCount = 0
        name1.editableText.clear()
        name2.editableText.clear()
        name1.editableText.insert(0, "Player 1")
        name2.editableText.insert(0, "Player 2")
    }

    private fun updatePlayerScore() {
        player1score.text = player1ScoreCount.toString()
        player2score.text = player2ScoreCount.toString()
    }

    private fun checkWinner(): Boolean {
        var winnerResult = false
        for (winingPos in winingPos) {
            if (gameState[winingPos[0]] == gameState[winingPos[1]] &&
                gameState[winingPos[1]] == gameState[winingPos[2]] &&
                gameState[winingPos[0]] != 2
            ) {
                winnerResult = true
            }
        }

        return winnerResult
    }

    private fun playGameAgain() {
        rounds = 0
        playerOneActive = true
        for (i in buttons.indices) {
            gameState[i] = 2
            buttons[i].text = ""

        }
        status.text = "Status"
    }
}