package com.martin.wordcount

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martin.wordcount.ui.theme.WordCountInfo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WordCountViewModel(private val dispatcher: CoroutineDispatcher = Dispatchers.Default): ViewModel() {
    var uiState by mutableStateOf(WordCountInfo("", 0))
        private set

    fun wordsInput(newWords: String) {
        Log.d(TAG, "newWords: $newWords")
        viewModelScope.launch(dispatcher) {
            uiState = WordCountInfo(newWords, countWords(newWords))
        }
    }

    fun random() {
        Log.d(TAG, "random")
        viewModelScope.launch(dispatcher) {
            val s = getRandomString()
            uiState = WordCountInfo(s, countWords(s))
        }
    }

    private fun countWords(s: String): Int {
        return s.trim().split(WORD_SEPARATORS).filter { it.isNotEmpty() }.size
    }

    private fun getRandomString() : String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9') + " "
        val length = (1..100).random()
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }

    companion object {
        private val WORD_SEPARATORS = "\\s+".toRegex()

        private const val TAG = "WordCountViewModel"
    }
}