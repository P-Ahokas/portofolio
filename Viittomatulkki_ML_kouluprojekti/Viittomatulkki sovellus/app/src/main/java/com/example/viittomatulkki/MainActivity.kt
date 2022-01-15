package com.example.viittomatulkki

import android.os.Bundle
import android.content.Intent
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

private lateinit var auth: FirebaseAuth
class MainActivity : AppCompatActivity() {
    companion object{
        private const val TAG = "MainActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth

        oClickButtonListenerLogin()
        oClickButtonListenerWithoutLogin()
    }

    private fun oClickButtonListenerLogin() {
        val button_login = findViewById<Button>(R.id.button_login)
        button_login!!.setOnClickListener {
            val intent = Intent(".SecondActivity")
            startActivity(intent)
        }
    }

    private fun oClickButtonListenerWithoutLogin() {
        val button_withoutLogin = findViewById<Button>(R.id.button_without_login)
        button_withoutLogin!!.setOnClickListener {

            adLogIn()
            val intent = Intent(".ThirdActivity")
            startActivity(intent)
        }
    }

    //Kaikki kirjautumisen kanssa tekemissen liittyvät katsottu seuraavan dokumentoinnin pohjalta: https://firebase.google.com/docs/auth/android/password-auth
    fun adLogIn(){
        Log.d(TAG, "Lets log in with 'admin' account, so we get access to the database")
        //Tähän pitää laittaa oikeat tunnukset, kun eihän sinne tietokantaan pääse muuten, kuin tunnuksilla
        //En teko hetkellä keksinyt parempaa
        val adEmail = "u9641291@gmail.com"
        val adPassword = "sala23456"

        auth.signInWithEmailAndPassword(adEmail, adPassword)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //Kirjaudutaan sisään onnistui
                    Log.d(TAG, "SignInWithAdEmail:success")
                } else {
                    //Kirjaudu sisään epäonnistui
                    Log.w(TAG, "signInWithAdEmail:failure")
                }
            }
        }
}