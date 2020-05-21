package mibnu.team.petugas.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Data (
    @SerializedName("kd_berkas") var kodeBerkas : Int? = null,
    @SerializedName("ktp_meninggal") var ktpMeninggalUrl : String?=null ,
    @SerializedName("kk_meninggal") var kkMeninggalUrl : String?=null,
    @SerializedName("jamkesmas") var jamkesmasMeninggalUrl : String?=null,
    @SerializedName("ktp_waris") var ktpWarisUrl : String?=null,
    @SerializedName("kk_waris") var kkWarisUrl : String?=null,
    @SerializedName("akta_kematian") var aktaKematianUrl : String?=null,
    @SerializedName("pakta_waris") var paktaWarisUrl : String?=null,
    @SerializedName("pernyataan_waris") var pernyataanWarisUrl : String?=null,
    @SerializedName("keterangan") var keterangan : String?=null,
    @SerializedName("confirmed_I") var confirmedI : Boolean?=null,
    @SerializedName("confirmed_II") var confirmedII : Boolean?=null,
    @SerializedName("confirmed_III") var confirmedIII : Boolean?=null,
    @SerializedName("waris") var waris : Waris? = null,
    @SerializedName("petugas") var petugas: Petugas?=null,
    @SerializedName("dinkes") var dinkes: Dinkes?=null
) : Parcelable