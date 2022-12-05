package com.example.dragapp.pojo

data class Queen(
    var id              : Int?             = null,
    var name            : String?          = null,
    var realName        : String?          = null,
    var age             : Int?             = 0,
    var imgUrl          : String?          = null,
    var location        : String?          = null,
    var description     : String?          = null,
    var gallery         : List<String>?    = null,
    var socialMedia     : List<String>?    = null,
)