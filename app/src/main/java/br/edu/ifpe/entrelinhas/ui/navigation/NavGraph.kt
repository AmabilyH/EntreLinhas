package br.edu.ifpe.entrelinhas.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.edu.ifpe.entrelinhas.ui.features.abertura.TelaAbertura
import br.edu.ifpe.entrelinhas.ui.features.home.TelaPedidos

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavTarget.Splash.route
    ) {
        composable(NavTarget.Splash.route) {
            TelaAbertura(navController = navController)
        }

        composable(NavTarget.Home.route) {
            TelaPedidos()
        }
    }
}