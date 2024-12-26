package com.example.mvinewsapp.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.mvinewsapp.data.models.Article
//import com.example.newsappassignment.data.network.NetworkConnectivityObserver
import com.example.mvinewsapp.data.network.Retrofit.api
import com.example.mvinewsapp.data.network.RetryHelper
import com.example.mvinewsapp.orbit.NewsSideEffects
import com.example.mvinewsapp.orbit.NewsState
import com.example.mvinewsapp.utils.Constant
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import java.util.UUID
import kotlin.math.log


/**
* ViewModel for managing news articles and their states in the application.
* This ViewModel has functions to fetch articles, manage side effects like toasts or navigation, etc.
*
* @param application The application context used for network checks and other operations.
*/

open class NewsViewModel(): ViewModel(), ContainerHost<NewsState, NewsSideEffects> {

    override val container = container<NewsState, NewsSideEffects>(NewsState())


    init {
        setTopStories()
    }

    private val retryHelper = RetryHelper()

    /**
     * Fetches top stories from the NYTimes and updates the state.
     * Handles the loading state and the error state
     */
    private fun setTopStories() = intent {

        //first change the loading state to be true as we are starting the process of fetching data
        reduce {
            state.copy(isLoading = true)
        }

        //try to make the GET request.
        try {
            val fetchedData = retryHelper.retry {
                api.getTopStories(Constant.APIKEY)
            }
            if (fetchedData.isSuccessful) {
                //request is successful, populate the state.articles
                //NOTE: every article has a articleID that is initialized to null, we have
                //to now generate an id per article by mapping over the articles

                val newArticles = fetchedData.body()?.results?.mapIndexed{ index, article ->
                    article.copy(articleId = UUID.randomUUID().toString())
                }

                reduce {
                    state.copy(
                        articles = newArticles,
                        isLoading = false
                    )
                }
                Log.i("populated state.articles", state.articles.toString())
            } else {
                //request was not successful, show the error
                Log.e("Error while fetching Top Stories", fetchedData.errorBody().toString())
                reduce { state.copy(isLoading = false) }
                postSideEffect(NewsSideEffects.Toast("Error while fetching stories"))
            }

        } catch (e: Exception) {
            //Exception while making the request, show user a toast
            Log.e("Exception while fetching Top Stories", e.message.toString())
            reduce { state.copy(isLoading = false) }
            postSideEffect(NewsSideEffects.Toast("Exception while fetching stories"))

        }
    }


    /**
     * Handles navigation to article details when a card is clicked.
     *
     * @param title The title of the article to navigate to.
     */
    fun onListScreenCardClicked(title : String) = intent {
        postSideEffect(NewsSideEffects.NavigateToDetails( title ))
        Log.i("Navigation Side Effect posted", "")
    }


    /**
     * Handles side effects. We listen for these effect in the MainActivity
     *
     * @param it The side effect to handle.
     * @param context The context used for displaying toast messages.
     * @param navController The navigation controller used for navigating between screens.
     */
    fun handleSideEffect(it: NewsSideEffects, context: Context, navController: NavHostController ) {
        when(it){
            is NewsSideEffects.NavigateToDetails -> {
                navController.navigate(
                    route = "articles/${it.title}",
                    )
            }


            is NewsSideEffects.Toast -> {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
        }

    }



    /**
     * Finds an article by its title in the current state.
     *
     * @param title The title of the article to find.
     * @return The found Article object or null if not found.
     */
    open fun findArticleByTitle(title: String): Article? {
        return container.stateFlow.value.articles?.find { it.title==title }
    }

    fun findArticleByID(title: String): Article? {
        return container.stateFlow.value.articles?.find { it.articleId==title }
    }


}