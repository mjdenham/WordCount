package com.martin.wordcount

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WordCountViewModel(private val dispatcher: CoroutineDispatcher = Dispatchers.Default): ViewModel() {
    var wordCountInfoState by mutableStateOf(WordCountInfo("", 0))
        private set

    fun wordsInput(newWords: String) {
        Log.d(TAG, "Words input: $newWords")
        viewModelScope.launch(dispatcher) {
            wordCountInfoState = WordCountInfo(newWords, countWords(newWords))
        }
    }

    fun random() {
        Log.d(TAG, "Random")
        viewModelScope.launch(dispatcher) {
            val s = getRandomString()
            wordCountInfoState = WordCountInfo(s, countWords(s))
        }
    }

    private fun countWords(s: String): Int {
        return s.trim().split(WORD_SEPARATORS).filter { it.isNotEmpty() }.size
    }

    private fun getRandomString() : String {
        val length = (1..MAX_RANDOM_LENGTH).random()
        return (1..length)
            .map { ALLOWED_RANDOM_CHARS.random() }
            .joinToString("")
    }

    companion object {
        private val WORD_SEPARATORS = "\\s+".toRegex()

        private val MAX_RANDOM_LENGTH = 100
        private val ALLOWED_RANDOM_CHARS = ('A'..'Z') + ('a'..'z') + ('0'..'9') + " "

        private const val TAG = "WordCountViewModel"
    }
}