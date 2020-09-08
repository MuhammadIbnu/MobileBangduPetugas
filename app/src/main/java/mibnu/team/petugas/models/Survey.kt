package mibnu.team.petugas.models

import com.google.gson.annotations.SerializedName

data class Survey(
    @SerializedName("1") var bad:Int=0,
    @SerializedName("2") var enough:Int=0,
    @SerializedName("3") var good:Int=0

)