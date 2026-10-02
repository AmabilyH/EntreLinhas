package br.edu.ifpe.entrelinhas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import br.edu.ifpe.entrelinhas.ui.navigation.NavGraph
import br.edu.ifpe.entrelinhas.ui.theme.EntreLinhasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EntreLinhasTheme {
                NavGraph()
            }
        }
    }
}
