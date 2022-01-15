package com.example.viittomatulkki


import android.os.Bundle
import android.content.Intent
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore



private lateinit var auth: FirebaseAuth
class SecondActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "SecondActivity"
        private var count = 0
        private var state = 0
    }

    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        //Alustetaan firebase auth
        auth = Firebase.auth

        oClickButtonListener()
    }

    fun oClickButtonListener() {
        val button_submit = findViewById<Button>(R.id.button2)
        button_submit!!.setOnClickListener {
            //tänne käyttäjätunnuksen ja salasanan varmistaminen eli if true niin käynnistää seuraavan activityn
            //Asetetaan state = 1, eli tarkistetaan onko tili lukittu, kun muutetaan 0, niin tili lukitaan
            state = 1
            button_submit.isEnabled = false
            adLogIn("")

        }

    }


    private fun adLogIn(email: String){
        Log.d(TAG, "Lets log in with 'admin' account, so we get access to the database")
        //Tähän pitää laittaa oikeat tunnukset, kun eihän sinne tietokantaan pääse muuten, kuin tunnuksilla
        //En teko hetkellä keksinyt parempaa
        val adEmail = "u9641291@gmail.com"
        val adPassword = "sala23456"

        auth.signInWithEmailAndPassword(adEmail, adPassword)
            .addOnCompleteListener(this) { task ->
                if(task.isSuccessful) {
                    //Kirjaudutaan sisään onnistui
                    Log.d(TAG,"SignInWithAdEmail:success")
                    if (state == 1){
                        checkIfLocked(findViewById<EditText>(R.id.editTextTextEmailAddress).text.toString())
                    }else if (state == 0){
                        lockAccountPartOne(email)
                    }
                } else {
                    //Kirjaudu sisään epäonnistui
                    Log.w(TAG, "signInWithAdEmail:failure")
                }

            }
    }


    private fun checkIfLocked(email: String){

        val btn = findViewById<Button>(R.id.button2)

        Log.d(TAG, "Ollaan checkIfLockedissa")

        var data: Any

        db.collection("Users").whereEqualTo("email", email).get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    // Otetaan vain locked fieldin sisältö, 0 = ei lukittu, 1 = on lukittu
                    data = document["locked"]!!
                    Log.d(TAG, data.toString())
                    if (data.toString() == "0") {
                        auth.signOut()
                        Log.d(TAG, "Jatketaan checCurrentUseriin")
                        checkCurrentsUser()
                        break
                    } else {
                        Firebase.auth.signOut()
                        Log.d(TAG, "Tilisi on jo lukittu aikaisemmin")
                        Toast.makeText(
                            baseContext, "Tilisi on jo lukittu aikaisemmin",
                            Toast.LENGTH_SHORT
                        ).show()
                        btn.isEnabled = true
                        break
                    }
                }
            }
    }


    private fun checkCurrentsUser(){
        //Tarkistetaan, että onko kukaan kirjautunut sisään
        val currentUser = auth.currentUser
        if(currentUser != null){
            Firebase.auth.signOut()
        }else{
            Log.d(TAG, "Voidaan jatkaa kirjautumiseen")
            signIn()
        }
    }

    //Kaikki kirjautumisen kanssa tekemissen liittyvät katsottu seuraavan dokumentoinnin pohjalta: https://firebase.google.com/docs/auth/android/password-auth
    private fun signIn(){

        val btn = findViewById<Button>(R.id.button2)

        Log.d(TAG, "Ollaan signInissä")
        Log.d(TAG, "state is: $state and count is: $count")

        //Haetaan EditText kentistä tiedot
        val email = findViewById<EditText>(R.id.editTextTextEmailAddress).text.toString()
        val password = findViewById<EditText>(R.id.editTextTextPassword).text.toString()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if(task.isSuccessful) {
                    //Kirjaudutaan sisään onnistui
                    Log.d(TAG,"SignInWithEmail:success")
                    Toast.makeText(baseContext, "Autentikaatio onnistui.",
                        Toast.LENGTH_SHORT).show()
                    //Tarkistetaan vielä onko käyttäjä jo antanut luvat
                    checkPermission()
                } else {
                    //Kirjaudu sisään epäonnistui
                    Log.w(TAG,"signInWithEmail:failure")
                    count ++
                    if (count == 3){
                        Log.w(TAG,"signInWithEmail:failure, account is getting locked")
                        Toast.makeText(baseContext, "Autentikaatio epäonnistui 3 kertaa, tilisi lukitaan.",
                            Toast.LENGTH_SHORT).show()
                        state = 0
                        adLogIn(email)
                    }else if (count < 3){
                        Toast.makeText(baseContext, "Autentikaatio epäonnistui.",
                            Toast.LENGTH_SHORT).show()
                        btn.isEnabled = true
                    }
                }
            }
    }

    //Kaikki Firestoren tiedon lukemisessa oli tämä pohjana: https://firebase.google.com/docs/firestore/query-data/get-data
    //Kaikki Firestoren tietojen muokkaamisessa oli tämä pohjana: https://firebase.google.com/docs/firestore/manage-data/add-data
    private fun lockAccountPartOne(email : String){
        Log.d(TAG, "Ollaan lockAccountPartOnessa")

        var docId = ""

        Log.d(TAG, email)

        db.collection("Users").whereEqualTo("email", email).get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    // Otetaan vain permission fieldin sisältö, 0 = ei lupaa, 1 = on lupa
                    docId = document.id
                    //val data = document["locked"]
                    Log.d(TAG, "docId oli: $docId")
                    lockAccountPartTwo(docId)

                }
            }
    }


    private fun lockAccountPartTwo(docId: String){
        Log.d(TAG, "Ollaan lockAccountPartTwossa")

        val btn = findViewById<Button>(R.id.button2)

        Log.d(TAG, "Ollaan lockedissa")
        val userRef = db.collection("Users").document(docId)

        userRef.update("locked", 1).addOnSuccessListener {
            Log.d(TAG, "Tili on lukittu")
            Toast.makeText(
                baseContext, "Tili on lukittu",
                Toast.LENGTH_SHORT).show()
            count = 0
            Firebase.auth.signOut()
            btn.isEnabled = true
        }

        //auth.signOut()
    }


    private fun checkPermission(){
        Log.d(TAG,"Tarkistetaan luvat")
        val user = findViewById<EditText>(R.id.editTextTextEmailAddress).text.toString()

        var list = ""

        db.collection("Users").whereEqualTo("email",user).get().addOnSuccessListener { documents ->
            for (document in documents) {
                //list = "${document.id} => ${document.data}"
                // Otetaan vain permission fieldin sisältö, 0 = ei lupaa, 1 = on lupa
                list = document.getString("permission").toString()
                Log.d(TAG, "permission oli: $list")


            }

            when(list) {
                "0" -> continueToNext(0)
                "1" -> continueToNext(1)
                else -> Log.d(TAG,"Nyt meni jotain vikaan")
            }
        }

    }


    private fun continueToNext(permState: Int){
        when (permState) {
            0 -> {
                //Jos luvat on jo annettu aikaisemmin datan tallentamiseen, mennään suoraan päänäkymään
                val intent1 = Intent(".ThirdActivity")
                intent1.putExtra("email", findViewById<EditText>(R.id.editTextTextEmailAddress).text.toString())
                startActivity(intent1)
                this.finish()
            }
            1 -> {
                val intent2 = Intent(".FourthActivity")
                startActivity(intent2)
                this.finish()
            }
            else -> {
                Log.d(TAG,"Nyt meni jotain vikaan")
            }
        }
    }

}