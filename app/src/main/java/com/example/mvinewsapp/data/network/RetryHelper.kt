package com.example.mvinewsapp.data.network

import kotlinx.coroutines.delay

/**
 * A helper class for retrying operations. This class helps you to call a suspend function multiple
 * times with different delays.
 *
 * @property maxRetries The maximum number of retry attempts (default 3).
 * @property initialDelayMillis The delay before the first retry (default 100ms)
 * @property maxDelayMillis The maximum delay between retries (default 5000ms)
 */
class RetryHelper(
    private val maxRetries: Int = 3,
    private val initialDelayMillis: Long = 1000,
    private val maxDelayMillis: Long = 5000
) {

    /**
     * Executes a suspend operation with retry logic.
     *
     * Retries [maxRetries] times, increasing the delay with every retry to 2x
     *
     * @param operation The suspend function to execute.
     * @return The result of the successful operation.
     * @throws Exception If the operation fails after the maximum number of retries.
     */
    suspend fun <T> retry(operation: suspend () -> T): T {
        var currentDelay = initialDelayMillis
        repeat(maxRetries) { attempt ->
            try {
                return operation()
            } catch (e: Exception) {
                if (attempt == maxRetries - 1) throw e
                delay(currentDelay)
                currentDelay = (currentDelay * 2).coerceAtMost(maxDelayMillis)
            }
        }
        //will be handled by showing a toast to the user
        throw IllegalStateException("Retry failed after $maxRetries attempts")
    }
}
