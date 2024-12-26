package com.example.mvinewsapp.data.models

/**
 * Represents the response containing a list of articles from the NYTimes Top Stories API.
 *
 * @property results A list of [Article] objects representing the top stories.
 */
data class TopStoriesResponse(
    val results: List<Article>
)


/**
 * Represents an individual news article.
 *
 * @property title The title of the article.
 * @property byline The author or source of the article.
 * @property abstract A brief summary of the article content.
 * @property url The URL to read more about the article online.
 */
data class Article(
    val title: String,
    val byline: String,
    val abstract: String,
    val url: String
)