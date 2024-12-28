package com.example.mvinewsapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.mvinewsapp.data.models.Article
import com.example.mvinewsapp.orbit.NewsState
import com.example.mvinewsapp.viewmodel.NewsViewModel
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.orbitmvi.orbit.viewmodel.container

class ListScreenViewTestMockito {

    @JvmField
    @Rule
    val rule = createComposeRule()

    @Test
    fun test0(){
        val mockViewModel  = mock(NewsViewModel::class.java)

        val mockArticles =  listOf(
            Article(title = "Article 1", byline = "byline 1", abstract = "abstract 1", url = "URL 1"),
            Article(title = "Article 2", byline = "byline 2", abstract = "abstract 2", url = "URL 2")
        )

        val mockNewsState = NewsState(false, mockArticles)



        `when`(mockViewModel.container).thenReturn(mockViewModel.container(mockNewsState))

        rule.setContent {
            ListScreenView(mockViewModel)
        }

        rule.onNodeWithText("Article 1").assertIsDisplayed()
        rule.onNodeWithText("byline 1").assertIsDisplayed()
        rule.onNodeWithText("Article 2").assertIsDisplayed()
        rule.onNodeWithText("byline 1").assertIsDisplayed()
    }

}
