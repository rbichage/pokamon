package com.pokamon.demo.ui

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pokamon.features.pokedex.ui.details.CharacterDetailsScreen
import com.pokamon.features.pokedex.ui.listing.CharactersScreen

@Composable
fun PokamonNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Destination.Characters.route
    ) {
        composable(
            route = Destination.Characters.route
        ) {
            CharactersScreen(onItemClicked = {
                val route = "${Destination.Characters.route}/$it"
                navController.navigate(route)
            })
        }

        composable(
            route = Destination.CharacterDetails.route,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        500, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(500, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        500, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(
                        500, easing = EaseOut
                    ),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ) {
            val id = it.arguments?.getString("id")

            CharacterDetailsScreen(
                id = id.orEmpty(),
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }

}

sealed class Destination(val route: String) {
    data object Characters : Destination("characters")
    data object CharacterDetails : Destination("characters/{id}")
}