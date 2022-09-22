package com.martin.wordcount

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.martin.wordcount.ui.theme.WordCountTheme

class MainActivity : ComponentActivity() {
    private val viewModel: WordCountViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val uiState = viewModel.wordCountInfoState
            WordCountTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    WordsInput(uiState.words, uiState.wordCount, {w -> viewModel.wordsInput(w)}, { viewModel.random() })
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun WordsInput(words: String, wordCount: Int, wordsInput: (String) -> Unit, randomise: () -> Unit) {
    Column(
        modifier = Modifier.padding(10.dp)
    ) {
        OutlinedTextField(
            value = words,
            onValueChange = { wordsInput(it) },
            label = { Text(stringResource(R.string.input_words_prompt)) },
            modifier = Modifier
                .fillMaxWidth(1F)

        )

        Button(
            onClick = {
                randomise()
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(stringResource(id = R.string.random))
        }

        Text(text = pluralStringResource(id = R.plurals.word_count, wordCount, wordCount))
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WordCountTheme {
        WordsInput("Android", 1, {}, {})
    }
}