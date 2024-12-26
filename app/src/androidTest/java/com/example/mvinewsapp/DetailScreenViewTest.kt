package com.example.mvinewsapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.mvinewsapp.data.models.Article
import com.example.mvinewsapp.orbit.NewsSideEffects
import com.example.mvinewsapp.orbit.NewsState
import com.example.mvinewsapp.viewmodel.NewsViewModel
import org.junit.Rule
import org.junit.Test
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

/**
 * Test class for DetailScreenView to check if it does fetch the right article or not.
 * It also checks the state where a non-existent article if being searched for
 *
 * For these tests to work, comment out the "init block" in NewsViewModel file:
 * DIRECTORY: com.example.mvinewsapp.viewmodel
 * LINE : 33 - "setTopStories()"
 * TO-DO: comment out line 33, so the init block does nothing
 * WHY: we need to override the init block's properties but no hook exists to do so
 * BETTER-FIX: use mockito library to mock object's behaviour.
 */

class DetailScreenViewTest {

    class MockDetailNewsViewModel : NewsViewModel(), ContainerHost<NewsState, NewsSideEffects> {
        override val container: Container<NewsState, NewsSideEffects> = container(NewsState())

        private val mockArticles = listOf(
            Article(title = "Article 1", byline = "byline 1", abstract = "abstract 1", url = "URL 1"),
            Article(title = "Article 2", byline = "byline 2", abstract = "abstract 2", url = "URL 2")
        )

        init {
            intent {
                reduce {
                    state.copy(articles = mockArticles)
                }
            }
        }

        override fun findArticleByTitle(title: String): Article? {
            return mockArticles.find { it.title == title }
        }
    }
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun detailScreenView_displaysArticleDetailsCorrectly() {
        // Arrange: Create a mock ViewModel
        val viewModel = MockDetailNewsViewModel()

        // Set up the UI with the mock ViewModel and a specific article title
        composeTestRule.setContent {
            DetailScreenView(viewModel = viewModel, title = "Article 1")
        }

        // Act & Assert: Check if the article details are displayed correctly
        composeTestRule.onNodeWithText("Article 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("byline 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("abstract 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("URL: URL 1").assertIsDisplayed()
    }

    @Test
    fun detailScreenView_displaysNotFoundMessageForNonexistentArticle() {

        val viewModel = MockDetailNewsViewModel()

        composeTestRule.setContent {
            DetailScreenView(viewModel = viewModel, title = "Non-existent Article")
        }

        composeTestRule.onNodeWithText("Article not found").assertIsDisplayed()
    }

}