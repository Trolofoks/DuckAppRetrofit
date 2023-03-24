package com.honey.duckappretrofit.data.model

import com.google.gson.annotations.SerializedName

data class Duck(
    @SerializedName("url") val url: String,
    @SerializedName("message") val message: String
){
    constructor() : this("", "")
}
