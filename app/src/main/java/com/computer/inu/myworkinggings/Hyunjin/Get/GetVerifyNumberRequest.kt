package com.computer.inu.myworkinggings.Hyunjin.Get

import com.computer.inu.myworkinggings.Moohyeon.Data.Token

data class GetVerifyNumberRequest(
        val status : String,
        val message : String,
        val data  : Token
)