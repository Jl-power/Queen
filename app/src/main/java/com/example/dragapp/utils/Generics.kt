package com.example.dragapp.utils

import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar

class Generics {

    companion object{

        fun showSnackBar(layout: ConstraintLayout, message : String){
            Snackbar.make(layout,message,
                Snackbar.LENGTH_SHORT).apply {
            }.show()
        }
    }
}