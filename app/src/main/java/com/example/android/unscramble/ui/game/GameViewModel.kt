package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    private var _score = 0
    val score: Int
    get() = _score

    private var _currentWordCount = 0
    val currentWordCount: Int
    get() = _currentWordCount

    private lateinit var _currentScrambledWord: String
    val currentScrambledWord: String
    get() = _currentScrambledWord

    private var wordslist: MutableList<String> = mutableListOf()
    private lateinit var currentWord: String

    init {
        Log.d("GameViewModel", "ViewModel Created!")
        getNextWord()
    }

    private fun getNextWord(){

        currentWord = allWordsList.random()
        val tempWord = currentWord.toCharArray()
        tempWord.shuffle()

        // Makes sure the unscrambled word differs from the original
        while(tempWord.toString().equals(currentWord, false)){
            tempWord.shuffle()
        }

        if (wordslist.contains(currentWord)){
            getNextWord()

        } else {
            _currentScrambledWord = String(tempWord)
            ++_currentWordCount
            wordslist.add(currentWord)
        }
    }

     fun nextWord() : Boolean {
       return if (_currentWordCount < MAX_NO_OF_WORDS){
            getNextWord()
            true
        } else false

    }

    fun reinitializeData(){
        _score = 0
        _currentWordCount = 0
        wordslist.clear()
        getNextWord()
    }

    private fun inscreaseScore(){
        _score += SCORE_INCREASE
    }

     fun isUserWordCorrect(playerWord: String) : Boolean {
        if (playerWord.equals(currentWord, true)){
            inscreaseScore()
            return true
        }
        return false
    }
}