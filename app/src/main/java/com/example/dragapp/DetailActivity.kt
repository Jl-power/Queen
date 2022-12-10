package com.example.dragapp

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.dragapp.databinding.ActivityDetailBinding
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Picasso.get().load(intent.extras?.getString("imgUrl")).into(binding.imgProfile)

        binding.imgBack.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        binding.tvName.text = intent.extras?.getString("name")
        var aux = binding.tvRealName.text.toString() + " " + intent.extras?.getString("realName")
        binding.tvRealName.text = aux
        aux = binding.tvAge.text.toString() + " " + intent.extras?.getInt("age").toString()
        binding.tvAge.text = aux
        binding.tvDescription.text = intent.extras?.getString("description")

        Picasso.get().load(intent.extras?.getStringArrayList("gallery")?.get(0))
            .into(binding.imgGallery1)
        Picasso.get().load(intent.extras?.getStringArrayList("gallery")?.get(1))
            .into(binding.imgGallery2)
        Picasso.get().load(intent.extras?.getStringArrayList("gallery")?.get(2))
            .into(binding.imgGallery3)
        Picasso.get().load(intent.extras?.getStringArrayList("gallery")?.get(3))
            .into(binding.imgGallery4)
        Picasso.get().load(intent.extras?.getStringArrayList("gallery")?.get(4))
            .into(binding.imgGallery5)
        Picasso.get().load(intent.extras?.getStringArrayList("gallery")?.get(5))
            .into(binding.imgGallery6)

        controlSocialMedia(intent.extras?.getStringArrayList("socialMedia"))

        binding.imgFacebook.setOnClickListener {
            val uri = Intent(Intent.ACTION_VIEW,
                Uri.parse(intent.extras?.getStringArrayList("socialMedia")?.get(0)))
            startActivity(uri)
        }

        binding.imgInstagram.setOnClickListener {
            val uri = Intent(Intent.ACTION_VIEW,
                Uri.parse(intent.extras?.getStringArrayList("socialMedia")?.get(1)))
            startActivity(uri)
        }

        binding.imgTwitter.setOnClickListener {
            val uri = Intent(Intent.ACTION_VIEW,
                Uri.parse(intent.extras?.getStringArrayList("socialMedia")?.get(2)))
            startActivity(uri)
        }

        binding.imgGallery1.setOnClickListener {
            expandImage(binding.imgGallery1.drawable)
        }

        binding.imgGallery2.setOnClickListener {
            expandImage(binding.imgGallery2.drawable)
        }

        binding.imgGallery3.setOnClickListener {
            expandImage(binding.imgGallery3.drawable)
        }

        binding.imgGallery4.setOnClickListener {
            expandImage(binding.imgGallery4.drawable)
        }

        binding.imgGallery5.setOnClickListener {
            expandImage(binding.imgGallery5.drawable)
        }

        binding.imgGallery6.setOnClickListener {
            expandImage(binding.imgGallery6.drawable)
        }

    }

    private fun controlSocialMedia(socialMedia: ArrayList<String>?) {
        if (socialMedia?.get(0) == "")
            binding.imgFacebook.isVisible = false
        if (socialMedia?.get(1) == "")
            binding.imgInstagram.isVisible = false
        if (socialMedia?.get(2) == "")
            binding.imgTwitter.isVisible = false

    }

    private fun expandImage(drawable: Drawable?) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog)
        dialog.findViewById<ImageView>(R.id.dialogImage).setImageDrawable(drawable)
        dialog.findViewById<ImageView>(R.id.btn_cancel).setOnClickListener {
            dialog.hide()
        }
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        dialog.window!!.setGravity(Gravity.CENTER)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.show()

    }
}