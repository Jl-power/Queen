package com.example.dragapp.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class User (
    @SerializedName("id")
    @PrimaryKey                     var id              : Int?             = null,
    @SerializedName("email")        var email           : String?          = null,
    @SerializedName("pass")         var pass            : String?          = null,
    @SerializedName("name")         var name            : String?          = null,
    @SerializedName("number")       var number          : Int?             = null,
    )