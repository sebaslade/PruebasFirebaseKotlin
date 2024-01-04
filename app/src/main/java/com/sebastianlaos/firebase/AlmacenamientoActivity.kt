package com.sebastianlaos.firebase

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.Firebase
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import com.sebastianlaos.firebase.ui.theme.FirebaseTheme
import java.util.UUID


class AlmacenamientoActivity : ComponentActivity() {
    lateinit var storage: FirebaseStorage
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        storage = Firebase.storage
        var storageRef = storage.reference
        setContent {
            var imageUri by remember { mutableStateOf<Uri?>(null) }
            val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                imageUri = uri
                val archivo: StorageReference = storageRef.child(
                    "misfotos/" + UUID.randomUUID().toString()
                )
                //archivo.putFile(uri)
            }
            Column {
                Button(onClick = { launcher.launch("image/*") }) {
                    Text(text = "Load Image")
                }
                Image(
                    painter = rememberAsyncImagePainter(imageUri),
                    contentDescription = "My Image"
                )
            }
        }
    }
}
