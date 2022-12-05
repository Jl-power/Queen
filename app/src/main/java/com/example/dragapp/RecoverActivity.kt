package com.example.dragapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.telephony.SmsManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import com.example.dragapp.databinding.ActivityRecoverBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.security.SecureRandom
import java.util.*

class RecoverActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRecoverBinding
    private val db = Firebase.firestore
    private var token = "_"
    private lateinit var smsManager: SmsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecoverBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS),1);
        }

        binding.btnCancelRecover.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btnAcceptRecover.setOnClickListener {
            controlEmail(binding.inputMailRecover.text.toString())
        }

        binding.btnConfirm.setOnClickListener {
            controlToken()
        }
    }

    private fun controlToken() {
        val inputToken = binding.tvCod1.text.toString() + binding.tvCod2.text.toString() +
                binding.tvCod3.text.toString() + binding.tvCod4.text.toString()

        if (token.equals(inputToken)){
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun controlEmail(email: String) {

        val ref : CollectionReference = db.collection("User")
        val query = ref.whereEqualTo("email", email)
        query.get().addOnSuccessListener {
            for (document in it){
                sendToken(document.data.get("number").toString())

                binding.tvSubtitleRecover.isVisible = true
                binding.tvCod1.isVisible = true
                binding.tvCod2.isVisible = true
                binding.tvCod3.isVisible = true
                binding.tvCod4.isVisible = true
                binding.btnConfirm.isVisible = true
            }
        }
        .addOnFailureListener{
            Snackbar.make(binding.layoutRecover,"El Email ingresado no esta registrado",
                Snackbar.LENGTH_SHORT).apply {
                view.background = resources.getDrawable(R.drawable.round_corner, null)
            }.show()
        }


    }

    private fun sendToken(number: String){
        token = generateRandomString()
        if (Build.VERSION.SDK_INT>=23) {
            smsManager = this.getSystemService(SmsManager::class.java)
        }
        else{
            smsManager = SmsManager.getDefault()
        }
        smsManager.sendTextMessage(number, null,
            "Queens Token de Seguridad: $token", null, null)

        Snackbar.make(binding.layoutRecover,"Token enviado! revisa tus sms",
            Snackbar.LENGTH_SHORT).apply {
            view.background = resources.getDrawable(R.drawable.round_corner, null)
        }.show()
    }

    private fun generateRandomString(): String {
        val NUMBER = "0123456789"
        val DATA_FOR_RANDOM_STRING = NUMBER
        val random = SecureRandom()
        require(4 >= 1)
        val sb = StringBuilder(4)
        for (i in 0 until 4) {
            // 0-62 (exclusivo), retorno aleatorio 0-61
            val rndCharAt: Int = random.nextInt(DATA_FOR_RANDOM_STRING.length)
            val rndChar = DATA_FOR_RANDOM_STRING[rndCharAt]
            sb.append(rndChar)
        }
        return sb.toString()
    }
}