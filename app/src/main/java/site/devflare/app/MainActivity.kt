package site.devflare.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import site.devflare.app.ui.DevFlareRoot
import site.devflare.app.ui.theme.DevFlareTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val app = application as DevFlareApplication
        setContent {
            DevFlareTheme {
                DevFlareRoot(
                    sessionStore = app.sessionStore,
                    authRepository = app.authRepository,
                )
            }
        }
    }
}
