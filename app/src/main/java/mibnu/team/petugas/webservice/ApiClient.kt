package mibnu.team.petugas.webservice

import com.google.gson.annotations.SerializedName
import mibnu.team.petugas.models.Data
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


    @FormUrlEncoded
    @POST("api/petugas/berkas/{kd_berkas}")
    fun dataUpdate(@Header("Authorization") token: String, @Path("kd_berkas") id:String,
                   @Field("confirmed_I") confirmed_I: Boolean): Call<WreppedRespond<Data>>
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