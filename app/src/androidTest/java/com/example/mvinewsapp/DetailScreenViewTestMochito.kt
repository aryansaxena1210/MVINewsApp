package com.example.mvinewsapp

import android.util.Log
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.mvinewsapp.data.models.Article
import com.example.mvinewsapp.orbit.NewsSideEffects
import com.example.mvinewsapp.orbit.NewsState
import com.example.mvinewsapp.viewmodel.NewsViewModel
import kotlinx.coroutines.flow.StateFlow
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.viewmodel.container
import kotlin.reflect.typeOf

class DetailScreenViewTestMochito {

    @JvmField
    @Rule
    val rule = createComposeRule()

    @Test
    fun test0(){
        val mockViewModel :NewsViewModel  = mock(NewsViewModel::class.java)

        val mockArticles =  listOf(
            Article(articleId = "0", title = "Article 1", byline = "byline 1", abstract = "abstract 1", url = "URL 1"),
            Article(articleId = "1", title = "Article 2", byline = "byline 2", abstract = "abstract 2", url = "URL 2")
        )

        val mockNewsStateFlow: StateFlow<NewsState> = mock()
        val mockNewsState = NewsState(false, mockArticles)
        val mockContainer: Container<NewsState, NewsSideEffects> = mock()

        `when`(mockViewModel.container).thenReturn(mockContainer)
        `when`(mockContainer.stateFlow).thenReturn(mockNewsStateFlow)
        `when`(mockNewsStateFlow.value).thenReturn(mockNewsState)
        `when`(mockViewModel.findArticleByID("0")).thenReturn(mockArticles[0])


        rule.setContent {
            DetailScreenView(mockViewModel, "0")
        }

        rule.onNodeWithText("Article 1").assertIsDisplayed()

    }

}
