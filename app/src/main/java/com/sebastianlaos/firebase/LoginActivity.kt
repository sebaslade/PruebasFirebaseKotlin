package com.sebastianlaos.firebase

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sebastianlaos.firebase.ui.theme.FirebaseTheme

class LoginActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        setContent {
            FirebaseTheme {
                var correo by remember { mutableStateOf("") }
                var clave by remember { mutableStateOf("") }

                Column(modifier = Modifier.padding(all = 32.dp)) {
                    TextField(value = correo,
                        label = { Text(text = "Nombre usuario") },
                        modifier = Modifier.fillMaxWidth(),
                        onValueChange = {
                            correo = it
                        })
                    Spacer(modifier = Modifier.size(16.dp))
                    TextField(value = clave,
                        label = { Text(text = "Contraseña") },
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = PasswordVisualTransformation(),
                        onValueChange = {
                            clave = it
                        })
                    Spacer(modifier = Modifier.size(16.dp))
                    Button(onClick = {
                        iniciarSesion(correo, clave)
                    }) {
                        Text(text = "Iniciar sesión")
                    }
                }
            }
        }
    }

    private fun iniciarSesion(correo: String, clave: String) {
        auth.signInWithEmailAndPassword(correo, clave)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@LoginActivity, "Bienvenido", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,EscritorioActivity::class.java))
                } else {
                    Toast.makeText(
                        baseContext,
                        task.exception?.message.toString(),
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }
}
