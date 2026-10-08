package br.edu.ifpe.entrelinhas.ui.navigation

// Rotas do app. Por enquanto só existe a tela inicial.
sealed class NavTarget(val route: String) {
    data object Splash : NavTarget("abertura")
    data object Home : NavTarget("home")
    data object Estoque : NavTarget("estoque")
    data object Produtos : NavTarget("produtos")
}