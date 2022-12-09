package com.example.dragapp.pojo

data class Queen(
    var email           : String?               = null,
    var name            : String?               = null,
    var realName        : String?               = null,
    var age             : Int?                  = 0,
    var imgUrl          : String?               = null,
    var location        : String?               = null,
    var description     : String?               = null,
    var gallery         : ArrayList<String>?    = null,
    var socialMedia     : ArrayList<String>?    = null,
)