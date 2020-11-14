package com.example.tsisocialapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.tsisocialapp.utils.getCurrentUser
import com.example.tsisocialapp.views.HomeActivity
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Se não houver usuário logado, redireciona para a login
        if(getCurrentUser() == null){
            val providers = arrayListOf(AuthUI.IdpConfig.EmailBuilder().build())

            val i = AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build()

            startActivityForResult(i, 0)
        }else{
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 0){
            if (resultCode == RESULT_OK){
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }else{
                val providers = arrayListOf(AuthUI.IdpConfig.EmailBuilder().build())

                val i = AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build()

                startActivityForResult(i, 0)
            }
        }
    }
}