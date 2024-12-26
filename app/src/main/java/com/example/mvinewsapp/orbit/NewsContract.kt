package com.example.mvinewsapp.orbit

import com.example.mvinewsapp.data.models.Article

/**
 * Represents the state of application.
 *  * @property isLoading true if data is currently being loaded
 *  * @property articles List of all the articles that are fetched using the API
 */
data class NewsState(
    val isLoading : Boolean = false,
    val articles : List<Article>? = emptyList(),
)


/**
 * Sealed class which holds all the side effects that can occur as its children
 */
sealed class NewsSideEffects{

    /**
     * Represents a toast message to be displayed to the user.
     * @property message The message to be shown in the toast.
     */
    data class Toast(val message : String) : NewsSideEffects()


    /**
     * Represents a navigation action to the details screen for a specific article.
     * @property title The title of the article to navigate to.
     */
    data class NavigateToDetails(val title : String): NewsSideEffects()
}