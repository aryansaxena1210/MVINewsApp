package com.example.mvinewsapp

import android.util.Log
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mvinewsapp.data.models.Article
import com.example.mvinewsapp.orbit.NewsSideEffects
import com.example.mvinewsapp.orbit.NewsState
import com.example.mvinewsapp.viewmodel.NewsViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container


/**
 * Mock implementation of NewsViewModel for testing purposes.
 * This class extends NewsViewModel and implements ContainerHost to manage state and side effects.
 *
 *
 *For these tests to work, comment out the "init block" in NewsViewModel file:
 * DIRECTORY: com.example.mvinewsapp.viewmodel
 * LINE : 33 - "setTopStories()"
 * TO-DO: comment out line 33, so the init block does nothing
 * WHY: we need to override the init block's properties but no hook exists to do so
 * BETTER-FIX: use mockito library to mock object's behaviour.
 */
class MockNewsViewModel : NewsViewModel(), ContainerHost<NewsState, NewsSideEffects> {


    override val container: Container<NewsState, NewsSideEffects> = container(NewsState())

    @Override
    private fun setTopStories(){
        Log.i("set Top Stories ran", "")
    }

    init {
        intent {
            reduce {
                val mockArticles =  listOf(
                    Article(title = "Article 1", byline = "byline 1", abstract = "abstract 1", url = "URL 1"),
                    Article(title = "Article 2", byline = "byline 2", abstract = "abstract 2", url = "URL 2")
                )

                state.copy(articles = mockArticles)
            }
        }
    }
}


/**
 * Test class for ListScreenView to check if it does display articles when state.article is populated
 *
 *
 */
@RunWith(AndroidJUnit4::class)
class ListScreenViewTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun listScreenView_displaysArticlesCorrectly() {
        // Arrange: Create a mock ViewModel and load articles
        val viewModel = MockNewsViewModel().apply {
            Log.i("viewmodel initialized", "")
        }

        // Set up the UI with the mock ViewModel
        composeTestRule.setContent {
            ListScreenView(viewModel = viewModel)
        }

        //assert display
        composeTestRule.onNodeWithText("Article 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("byline 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Article 2").assertIsDisplayed()
        composeTestRule.onNodeWithText("byline 1").assertIsDisplayed()

    }
}
