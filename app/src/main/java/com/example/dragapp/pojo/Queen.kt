package com.example.dragapp.pojo

import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Queen(
    @SerializedName("id")
    @PrimaryKey                     var id              : Int?             = null,
    @SerializedName("name")         var name            : String?          = null,
    @SerializedName("realName")     var realName        : String?          = null,
    @SerializedName("age")          var age             : Int?             = 0,
    @SerializedName("imgUrl")       var imgUrl          : String?          = null,
    @SerializedName("location")     var location        : String?          = null,
    @SerializedName("description")  var description     : String?          = null,
    @SerializedName("gallery")      var gallery         : List<String>?    = null,
    @SerializedName("socialMedia")  var socialMedia     : List<String>?    = null,
)