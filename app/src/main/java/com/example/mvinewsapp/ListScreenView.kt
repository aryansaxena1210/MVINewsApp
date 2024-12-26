package com.example.mvinewsapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mvinewsapp.data.models.Article
import com.example.mvinewsapp.viewmodel.NewsViewModel

import org.orbitmvi.orbit.compose.collectAsState


/**
 * Composable function that displays a list of articles.
 *
 * This function observes the state from the [NewsViewModel] and shows a loading indicator while data is being fetched.
 * Once the data is available, it displays a list of articles using [ArticleCard].
 *
 * @param viewModel The [NewsViewModel] instance used to retrieve articles and handle user interactions.
 */

@Composable
fun ListScreenView(viewModel: NewsViewModel) {
    val state by viewModel.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            //when loading is true, show loader
            state.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            else -> {
                //loading is false, so we know articles have been fetched. show the articles

                LazyColumn {
                    state.articles?.let { articleList ->
                        items(articleList) { article ->
                            ArticleCard(article, onClick = { viewModel.onListScreenCardClicked(article.title) } )
                        }?: item {Text(text = "article list is null")}
                    }
                }

            }
        }
    }
}


/**
 * Composable function that represents an individual article card.
 *
 * This function displays the article's title and byline, along with an image icon.
 *
 * @param article The [Article] object containing the data to display.
 * @param onClick A callback function to be invoked when the card is clicked.
 */
@Composable
fun ArticleCard(article: Article, onClick: () -> Unit ) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = onClick, //hold the value of a wrapper to the viewModel.onListScreenCardClicked function
    ) {

        Column(modifier = Modifier.padding(16.dp)) {

            //has 2 rows. one row contains the IMAGE and TITLE.
            //next row contains the BYLINE
            Row {

                Image(
                    painter = painterResource(id = R.drawable.newsicon),
                    contentDescription = "News Icon",
                    modifier = Modifier
                        .size(40.dp)
                        .padding(8.dp)
                )

                Text(
                    text = article.title,
                    style = MaterialTheme.typography.headlineSmall
                )
            }



            Row {
                Text(
                    text = article.byline,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.absolutePadding(left = 40.dp),
                )
            }


        }
    }
}
