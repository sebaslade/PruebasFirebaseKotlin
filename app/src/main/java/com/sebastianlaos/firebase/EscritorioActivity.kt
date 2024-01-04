package com.sebastianlaos.firebase

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sebastianlaos.firebase.ui.theme.FirebaseTheme

class EscritorioActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        val currentUser = auth.currentUser
        setContent {
            FirebaseTheme {
                Column {
                    Text(text = currentUser!!.email.toString())
                    Button(onClick = {
                        auth.signOut()
                        startActivity(Intent(this@EscritorioActivity,MainActivity::class.java))
                    }) {
                        Text(text = "Cerrar sesión")
                    }
                }
            }
        }
    }
}
