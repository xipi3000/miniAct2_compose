package com.example.miniact_composable

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.miniact_composable.ui.theme.MiniAct_ComposableTheme
import java.lang.Integer.parseInt

//https://code.luasoftware.com/tutorials/android/jetpack-compose-lauch-activity-and-receive-result
//https://developer.android.com/codelabs/jetpack-compose-state#3


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {


            Layout()
        }
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
            MainColumn()
        }
    }
}

@Composable
fun launcherDef(resultMsg: MutableState<String>, msg: MutableState<String>, rep : MutableState<String>): ManagedActivityResultLauncher<Intent, ActivityResult> {
    return rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result: ActivityResult ->
            val intent: Intent? = result.data
            resultMsg.value = intent?.getStringExtra("finalText").toString()
            msg.value = ""
            rep.value = ""
        }
    )
}

@Composable
fun MainColumn() {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(20.dp)
    )
    {
        var reps = remember { mutableStateOf("") }
        var msg = remember { mutableStateOf("") }
        var resultMsg = remember { mutableStateOf("") }
        val launcher = launcherDef(resultMsg,msg,reps)


        Text(
            "Result: " + resultMsg.value,
            modifier = Modifier.padding(bottom = 30.dp)
        )



        TextField(
            value = msg.value,
            onValueChange = { msg.value = it },
            modifier = Modifier.padding(bottom = 30.dp),
            label = { Text(text = "Text") },
            placeholder = { Text(text = "Intruduir text a concatenar") }
        )



        val pattern = remember { Regex("^\\d*\$")}
        TextField(
            value = reps.value, onValueChange = { if(it.matches(pattern)){ reps.value = it } },
            modifier = Modifier.padding(bottom = 30.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(text = "Repeticions") },
            placeholder = { Text(text = "Introduir el nombre de repeticions") }
        )

        var intent: Intent = Intent(LocalContext.current, RepActivity::class.java)
        intent.putExtra("msg", msg.value)
        if (reps.value == "") {
            intent.putExtra("rep", 0)
        } else intent.putExtra("rep", parseInt(reps.value))

        Button(onClick = { launcher.launch(intent) }) {
            Text(text = "Simple Button")
        }


    }
}



