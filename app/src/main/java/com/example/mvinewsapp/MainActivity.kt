package com.example.mvinewsapp

import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mvinewsapp.viewmodel.NewsViewModel
import org.orbitmvi.orbit.compose.collectSideEffect


/**
 * Main activity for the News App, responsible for setting up navigation and UI.
 *
 * This activity initializes the [NewsViewModel] and sets up the navigation graph for the application.
 * It defines two composable destinations: a list of articles and the detail view for a specific article.
 */

class MainActivity : ComponentActivity() {

    //initialize viewmodel such that we can pass the Application as a parameter
    private val viewModel: NewsViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContent {

            //navigation component with a starting destination
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "articles") {

                composable("articles") {
                    ListScreenView(viewModel)
                }

                composable("articles/{articleId}"){ navBackStackEntry ->
                    val articleId = navBackStackEntry.arguments?.getString("articleId")
                    DetailScreenView(viewModel, articleId?: "")

                }
            }


            //create a listener to handle all side-effects, if any occur
            val currentContext = LocalContext.current
            viewModel.collectSideEffect { viewModel.handleSideEffect(
                it,
                currentContext,
                navController
            )}

        }



    }


}