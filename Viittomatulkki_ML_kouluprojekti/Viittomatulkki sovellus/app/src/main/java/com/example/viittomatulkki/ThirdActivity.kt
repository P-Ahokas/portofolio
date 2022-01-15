package com.example.viittomatulkki

import android.os.Bundle
import android.content.Intent
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Timer
import kotlin.concurrent.timerTask


private lateinit var auth: FirebaseAuth
class ThirdActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "ThirdActivity"
    }

    private lateinit var intentData : String
    private val db = Firebase.firestore

    val timer = Timer(true) //daemon thread = timer task runs in the background
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        //Tätä käytetään lupa tiedon päivittämiseen
        intentData = intent.getStringExtra("email").toString()

        oClickButtonListener()
        oClickButtonListener2()

        timer.schedule(timerTask { timertask() },10000)
    //timerin ajoitus. pysäyttää timerin onclicklister funktioissa, muuten toteuttaa funktion timertask(siirtyy Main ikkunaan)
    }
    fun oClickButtonListener() {
        val button_yes = findViewById<Button>(R.id.button_yes)
        button_yes!!.setOnClickListener {
            checkCurrentsUser()
            //luvan antaminen: kyllä nappi
            timer.cancel()
            val intent = Intent(".FourthActivity")
            startActivity(intent)
            this.finish()
        }
    }
    fun oClickButtonListener2() {
        val button_denied = findViewById<Button>(R.id.button_no)
        button_denied!!.setOnClickListener {
            //luvan antamine: ei nappi
            timer.cancel()
            val intent = Intent(".PermissionDeniedActivity")
            startActivity(intent)
        }
    }

//timer tänne. Jos mitään ei tapahdu 10 sekuntiin niin siirrytään Mainiin
    fun timertask() {
    timer.cancel()
    val intent = Intent(".MainActivity")
    this.finish()
    }


    private fun checkCurrentsUser(){

        db.collection("Users").whereEqualTo("email",intentData).get().addOnSuccessListener { documents ->
            for (document in documents) {
                // Otetaan vain permission fieldin sisältö, 0 = ei lupaa, 1 = on lupa
                val docId = document. id
                Log.d(TAG, "permission oli: $docId")

                updateUsersPermission(docId)
            }
        }

    }


    private fun updateUsersPermission(docId: String){
        //Tallennetaan, että luvat on annettu, niin ei joka kerta tule tätä näkymää
        val userRef = db.collection("Users").document(docId)

        userRef.update("permission", "1").addOnSuccessListener { Toast.makeText(
            baseContext, "Lupa tiedot päivitettiin",
            Toast.LENGTH_SHORT).show() }


    }

}