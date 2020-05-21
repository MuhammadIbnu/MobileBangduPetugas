package mibnu.team.petugas.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Waris(
    @SerializedName("nik") var nik : String? = null,
    @SerializedName("kk") var kk : String? = null,
    @SerializedName("nama") var nama:String?=null,
    @SerializedName("jenis_kelami") var jenisKelamin:String?=null,
    @SerializedName("alamat") var alamat:String?=null,
    @SerializedName("rt") var rt:String?=null,
    @SerializedName("rw") var rw:String?=null,
    @SerializedName("kelurahan") var kelurahan:String?=null,
    @SerializedName("kecamatan") var kecamatan:String?=null,
    @SerializedName("kota") var kota:String?=null
) : Parcelable