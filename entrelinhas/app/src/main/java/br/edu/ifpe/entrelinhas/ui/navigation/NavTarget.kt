package br.edu.ifpe.entrelinhas.ui.navigation

// Rotas do app. Por enquanto só existe a tela inicial.
sealed class NavTarget(val route: String) {
    data object Home : NavTarget("home")
}
