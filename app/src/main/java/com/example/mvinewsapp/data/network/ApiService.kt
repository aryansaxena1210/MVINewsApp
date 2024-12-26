package com.example.mvinewsapp.data.network

import com.example.mvinewsapp.data.models.TopStoriesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface for accessing the New York Times Top Stories API.
 */
interface NYTimesApi {

    /**
     * Retrieves the top stories from the homepage of the New York Times.
     *
     * @param apiKey The API key for authentication.
     * @return A [Response] object containing a field results that has a list of articles.
     */
    @GET("topstories/v2/home.json")
    suspend fun getTopStories(
        @Query("api-key") apiKey: String
    ): Response<TopStoriesResponse>
}
