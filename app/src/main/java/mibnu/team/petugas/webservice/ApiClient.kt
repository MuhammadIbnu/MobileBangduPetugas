package mibnu.team.petugas.webservice

import com.google.gson.annotations.SerializedName
import mibnu.team.petugas.models.Data
import mibnu.team.petugas.models.Survey
import mibnu.team.petugas.models.User
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

class ApiClient {
    companion object{
        private var retrofit: Retrofit? = null
        private  const val  ENDPOINT="https://bangdu.herokuapp.com/"
        private  var options = OkHttpClient.Builder().apply{
            connectTimeout(30, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30,TimeUnit.SECONDS)
        }.build()

        private  fun getClient(): Retrofit{
            return if (retrofit == null){
                retrofit = Retrofit.Builder().apply {
                    baseUrl(ENDPOINT)
                    addConverterFactory(GsonConverterFactory.create())
                    client(options)
                }.build()
                retrofit!!

            }else{
                retrofit!!
            }
        }

        fun instance() = getClient().create(ApiService::class.java)
    }
}
interface  ApiService{
    @FormUrlEncoded
    @POST("api/petugas/login")
    fun login(@Field("username") username:String, @Field("password") password:String): Call<WreppedRespond<User>>

    @GET("api/petugas/berkasBaru")
    fun dataBaru(@Header("Authorization") token: String) : Call<WreppedListResponse<Data>>

    @GET("api/petugas/dataconfirmedII")
    fun dataconfirm(@Header("Authorization") token: String) : Call<WreppedListResponse<Data>>

    @GET("api/petugas/datafail")
    fun dataconfirmIII(@Header("Authorization") token: String): Call<WreppedListResponse<Data>>


    @GET("api/petugas/profile")
    fun profile(@Header("Authorization") token: String): Call<WreppedRespond<User>>

    @GET("api/petugas/listsurvey")
    fun survey():Call<WreppedRespond<Survey>>

    @FormUrlEncoded
    @POST("api/petugas/update")
    fun update(@Header("Authorization") token: String, @Field("password") password:String): Call<WreppedRespond<User>>



    @FormUrlEncoded
    @POST("api/petugas/berkas/{kd_berkas}")
    fun dataUpdate(@Header("Authorization") token: String, @Path("kd_berkas") id:String,
                   @Field("confirmed_I") confirmed_I: Boolean,@Field("keterangan") keterangan_I:String,
                   @Field("ket_ktp_meninggal") ket_ktp_meninggal : String,
                   @Field("ket_kk_meninggal") ketKkMeninggalUrl : String,
                   @Field("ket_jamkesmas") ketJamkesmasMeninggalUrl : String,
                    @Field("ket_ktp_waris") ketKtpWarisUrl : String,
                    @Field("ket_kk_waris") ketKkWarisUrl : String,
                    @Field("ket_akta_kematian") ketAktaKematianUrl : String,
                    @Field("ket_pernyataan_waris") ketPernyataanWarisUrl : String,
                    @Field("ket_pakta_waris") ketPaktaWarisUrl : String,
                    @Field("ket_buku_tabungan") ketBukuTabunganUrl : String): Call<WreppedRespond<Data>>

    @FormUrlEncoded
    @POST("api/petugas/acc/{kd_berkas}")
    fun dataValid(@Header("Authorization") token: String, @Path("kd_berkas") id: String,
    @Field("confirmed_III") confirmed_III: Boolean,@Field("keterangan_III")keterangan_III : String): Call<WreppedRespond<Data>>


}

data class WreppedRespond <T>(
    @SerializedName("message") var message : String,
    @SerializedName("status") var status :Boolean,
    @SerializedName("data") var  data : T
)

data class WreppedListResponse<T>(
    @SerializedName("message") var message : String,
    @SerializedName("status") var status :Boolean,
    @SerializedName("data") var  data : List<T>
)