package com.example.firebaseproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intentToAnotherScreen = Intent (this, ActivityMovies::class.java)
        startActivity(intentToAnotherScreen)

//        database = Firebase.database.reference
//
//        val providers = arrayListOf(AuthUI.IdpConfig.EmailBuilder().build())
//
//
//        val signInIntent = AuthUI.getInstance()
//            .createSignInIntentBuilder()
//            .setAvailableProviders(providers)
//            .build()
//
//        signInLauncher.launch(signInIntent)


    }


    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            Log.d("MyLog","success ${response?.email}")
            val authUser = FirebaseAuth.getInstance().currentUser
            authUser?.let {
                val fireBaseUser =  User(it.email.toString(), it.uid)
                Log.d("MyLog","fireBaseUser: $fireBaseUser")
                database.child("users").child(authUser.uid).setValue(fireBaseUser)
                val intentToAnotherScreen = Intent (this, ActivityMovies::class.java)
                startActivity(intentToAnotherScreen)
            }


        } else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
        }
    }


}