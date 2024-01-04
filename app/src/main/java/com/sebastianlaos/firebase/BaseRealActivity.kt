package com.sebastianlaos.firebase

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.sebastianlaos.firebase.ui.theme.FirebaseTheme

class BaseRealActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = Firebase.database
        val myRef = database.getReference("message")
        var textos = ""
        setContent {
            var mensaje by remember { mutableStateOf("") }
            var datos by remember { mutableStateOf("") }

            Column {
                TextField(value = mensaje,
                    label = { Text(text = "Escriba un mensaje") },
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = {
                        mensaje = it
                    }
                )
                Button(onClick = {
                    myRef.addValueEventListener(object: ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val value = snapshot.getValue<String>()
                            Log.d("MENSAJE", "Value is: " + value)
                            textos += value.toString() + "\n\n"
                            datos = textos
                        }

                        override fun onCancelled(error: DatabaseError) {
                        }
                    })
                    myRef.setValue(mensaje)
                }) {
                    Text(text = "Enviar")
                }
                Text(text = datos)
            }
        }
    }
}
