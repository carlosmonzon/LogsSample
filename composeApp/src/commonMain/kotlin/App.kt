import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import io.github.aakira.napier.Napier
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

            Button(onClick = {
                Napier.d { "DEBUG message" }
            }) {
                Text("DEBUG")
            }

            Button(onClick = {
                Napier.i { "INFO message" }
            }) {
                Text("INFO")
            }

            Button(onClick = {
                Napier.w { "WARN message" }
            }) {
                Text("WARN")
            }

            Button(
                onClick = {
                    Napier.e(throwable = RuntimeException("ERROR throwable")) {
                        "ERROR message"
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Magenta
                )
            ) {
                Text("ERROR")
            }

            Button(
                onClick = {
                    Napier.wtf(throwable = RuntimeException("WTF throwable")) { "WTF message" }
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Red
                )
            ) {
                Text("WTF")
            }
        }
    }
}