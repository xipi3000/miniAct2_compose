package com.example.miniact_composable

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.miniact_composable.ui.theme.MiniAct_ComposableTheme

class RepActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {


            Layout()
        }
    }


    @Preview(showBackground = true)
    @Composable
    fun Layout() {
        MiniAct_ComposableTheme {
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background
            ) {
                var msg: String = intent.getStringExtra("msg").toString()
                var numRep: Int = intent.getIntExtra("rep", 0)
                var finalText: String = ""
                if (msg.isEmpty() || numRep==0){
                    msg = "Default BYE"
                    numRep = 1
                }
                while (numRep > 0) {
                    finalText += msg
                    numRep--
                }
                Column() {
                    Text(text = finalText)
                    Button(onClick = {
                        var retIntent: Intent = Intent()
                        retIntent.putExtra("finalText", finalText)
                        setResult(RESULT_OK, retIntent)
                        finish()

                    }) {
                        Text(text = "Go back")
                    }
                }


            }

        }
    }
}
