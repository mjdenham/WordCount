package com.martin.wordcount

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test

class WordCountViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val instantCoroutineRule = MainCoroutineRule()

    @Test
    fun wordsInput() {
        val viewModel = WordCountViewModel(Dispatchers.Main)
        viewModel.wordsInput("abc def")
        assertEquals("abc def", viewModel.uiState.words)
        assertEquals(2, viewModel.uiState.wordCount)
    }

    @Test
    fun random() {
    }
}