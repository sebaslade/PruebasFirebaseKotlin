package com.sebastianlaos.firebase

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sebastianlaos.firebase.ui.theme.FirebaseTheme

class MainActivity : ComponentActivity() {
    private lateinit var analytics: FirebaseAnalytics
    private lateinit var auth: FirebaseAuth
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        analytics = Firebase.analytics
        auth = Firebase.auth
        setContent {
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
                    registro(correo, clave)
                }) {
                    Text(text = "Registrese")
                }
                Spacer(modifier = Modifier.size(16.dp))
                Button(onClick = {
                    startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                }) {
                    Text(text = "Iniciar sesión")
                }
            }
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            startActivity(Intent(this,EscritorioActivity::class.java))
        }
    }

    private fun registro(correo: String, clave: String) {
        auth.createUserWithEmailAndPassword(correo, clave)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Usuario registrado", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(
                        baseContext,
                        task.exception?.message,
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }
}
