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
    fun words_input_are_displayed() {
        val viewModel = WordCountViewModel(Dispatchers.Main)
        val wordsInput = "abc def"
        viewModel.wordsInput(wordsInput)
        assertEquals(wordsInput, viewModel.wordCountInfoState.words)
    }

    @Test
    fun words_input_are_counted() {
        val viewModel = WordCountViewModel(Dispatchers.Main)
        val wordsInputCountList = mapOf("abc def" to 2, "" to 0, "  " to 0, "\n" to 0, "a!@£$%^&*(b" to 1)
        wordsInputCountList.forEach { (wordsInput, expectedCount) ->
            viewModel.wordsInput(wordsInput)
            assertEquals(expectedCount, viewModel.wordCountInfoState.wordCount)
        }
    }

    @Test
    fun random_text_generated() {
        val viewModel = WordCountViewModel(Dispatchers.Main)
        val wordsInput = "abc def"
        viewModel.wordsInput(wordsInput)
        viewModel.random()
        val randomWords = viewModel.wordCountInfoState.words
        assertNotEquals(wordsInput, randomWords)
        assert(randomWords.isNotEmpty())
    }
}