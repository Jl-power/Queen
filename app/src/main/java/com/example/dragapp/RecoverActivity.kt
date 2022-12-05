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
import com.example.dragapp.utils.Generics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.security.SecureRandom

class RecoverActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRecoverBinding
    private lateinit var auth : FirebaseAuth
    private val db = Firebase.firestore
    private var token = "_"
    private lateinit var smsManager: SmsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecoverBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
            != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS)
                ,1)
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

        if (token == inputToken){
            auth.sendPasswordResetEmail(binding.inputMailRecover.text.toString())
            Generics.showSnackBar(binding.layoutRecover,"Correo de Recuperacion enviado")
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun controlEmail(email: String) {

        val ref : CollectionReference = db.collection("User")
        val query = ref.whereEqualTo("email", email)
        query.get().addOnSuccessListener {
            for (document in it){
                sendToken(document.data["number"].toString())

                binding.tvSubtitleRecover.isVisible = true
                binding.tvCod1.isVisible = true
                binding.tvCod2.isVisible = true
                binding.tvCod3.isVisible = true
                binding.tvCod4.isVisible = true
                binding.btnConfirm.isVisible = true
            }
        }
        .addOnFailureListener{
            Generics.showSnackBar(binding.layoutRecover,
                "El Email ingresado no esta registrado")
        }


    }

    private fun sendToken(number: String){
        token = generateRandomString()
        smsManager = if (Build.VERSION.SDK_INT>=23) {
            this.getSystemService(SmsManager::class.java)
        } else{
            SmsManager.getDefault()
        }
        smsManager.sendTextMessage(number, null,
            "Queens Token de Seguridad: $token", null, null)
        Generics.showSnackBar(binding.layoutRecover,"Token enviado! revisa tus sms")
    }

    private fun generateRandomString(): String {
        val number = "0123456789"
        val random = SecureRandom()
        require(4 >= 1)
        val sb = StringBuilder(4)
        for (i in 0 until 4) {
            // 0-62 (exclusivo), retorno aleatorio 0-61
            val rndCharAt: Int = random.nextInt(number.length)
            val rndChar = number[rndCharAt]
            sb.append(rndChar)
        }
        return sb.toString()
    }
}