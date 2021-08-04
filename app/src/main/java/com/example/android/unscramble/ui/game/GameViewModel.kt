package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    private val _score = MutableLiveData(0)
    val score: LiveData<Int>
    get() = _score

    private val _currentWordCount = MutableLiveData(0)
    val currentWordCount: LiveData<Int>
    get() = _currentWordCount

    private val _currentScrambledWord = MutableLiveData<String>()
    val currentScrambledWord: LiveData<String>
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
            _currentScrambledWord.value = String(tempWord)
            _currentWordCount.value = (_currentWordCount.value)?.inc()
            wordslist.add(currentWord)
        }
    }

     fun nextWord() : Boolean {
       return if (_currentWordCount.value!! < MAX_NO_OF_WORDS){
            getNextWord()
            true
        } else false

    }

    fun reinitializeData(){
        _score.value = 0
        _currentWordCount.value = 0
        wordslist.clear()
        getNextWord()
    }

    private fun increaseScore(){
        _score.value = (_score.value)?.plus(SCORE_INCREASE)
    }

     fun isUserWordCorrect(playerWord: String) : Boolean {
        if (playerWord.equals(currentWord, true)){
            increaseScore()
            return true
        }
        return false
    }
}