package mibnu.team.petugas.models

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("name") var name : String? = null,
    @SerializedName("username") var username: String? = null,
    @SerializedName("api_token") var token: String? = null
)