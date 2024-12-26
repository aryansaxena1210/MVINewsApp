package com.example.mvinewsapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mvinewsapp.viewmodel.NewsViewModel


/**
 * Composable function that displays the details of a selected article.
 *
 * This function retrieves an article by its title from the [NewsViewModel] and displays its
 * title, byline, abstract, and URL. If the article is not found, a message is displayed.
 *
 * @param viewModel The [NewsViewModel] instance used to retrieve the article data.
 * @param title The title of the selected article
 */
@Composable
fun DetailScreenView( viewModel: NewsViewModel, title : String) {

    val article = viewModel.findArticleByTitle(title)

    //display the title, byline, abstract and URL of this article in subsequent rows

    article?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = it.title,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

                Text(
                    text = it.byline,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = it.abstract,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                    Text(text = "URL: ${it.url}",
                        color = Color.Blue)
        }
    } ?: run {
        Text(
            text = "Article not found",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(16.dp)
        )
    }
}