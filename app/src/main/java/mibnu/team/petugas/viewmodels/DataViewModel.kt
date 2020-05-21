package mibnu.team.petugas.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mibnu.team.petugas.models.Data
import mibnu.team.petugas.utils.SingleLiveEvent
import mibnu.team.petugas.webservice.ApiClient
import mibnu.team.petugas.webservice.WreppedListResponse
import mibnu.team.petugas.webservice.WreppedRespond
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataViewModel : ViewModel(){
    private val api =  ApiClient.instance()
    private  var state : SingleLiveEvent<DataState> = SingleLiveEvent()
    private var datas = MutableLiveData<List<Data>>()

    private fun setLoading() { state.value = DataState.IsLoading(true) }
    private fun hideLoading() { state.value = DataState.IsLoading(false) }
    private fun showToast(mesage : String) { state.value = DataState.ShowToast(mesage) }

    fun fetchDatas(token: String){
        setLoading()
        api.dataBaru(token).enqueue(object : Callback<WreppedListResponse<Data>>{
            override fun onFailure(call: Call<WreppedListResponse<Data>>, t: Throwable) {
                println(t.printStackTrace())
                println(t.message)
                hideLoading()
                showToast(t.message.toString())
            }

            override fun onResponse(call: Call<WreppedListResponse<Data>>, response: Response<WreppedListResponse<Data>>) {
                if(response.isSuccessful){
                    val b = response.body()
                    println(b.toString())
                    datas.postValue(b?.data)
                }else{
                    showToast("Kesalahan saat mengambil data")
                }
                hideLoading()
            }
        })
    }

    fun updateConfirmedI (token: String, id:Int, confirmed_I: Boolean){
        state.value = DataState.IsLoading(true)
        api.dataUpdate(token,id.toString(), confirmed_I).enqueue(object : Callback<WreppedRespond<Data>>{
            override fun onFailure(call: Call<WreppedRespond<Data>>, t: Throwable) {
                println("onFailure :"+t.message)
                println(t.printStackTrace())
                state.value = DataState.ShowToast("onFailure : "+t.message)
            }

            override fun onResponse(
                call: Call<WreppedRespond<Data>>, response: Response<WreppedRespond<Data>>) {
                if (response.isSuccessful){
                    val body = response.body()
                    if (body?.status!!){
                        val data = body?.data
                        if (data.confirmedI!!){
                            state.value = DataState.ShowToast("Berhasil confrimasi daa masuk")
                            state.value = DataState.Success
                        }else{
                            state.value = DataState.ShowToast("Berhasil menolak data")
                            state.value = DataState.Success
                        }
                    }else{
                        println("Update gagal")
                        state.value = DataState.ShowToast("update gagal")
                    }
                }else{
                    state.value = DataState.ShowToast("gagal")
                    println("gagal")
                }
                state.value = DataState.IsLoading(false)

            }

        })
    }

    fun listenToUIState() = state
    fun listenToDatas() = datas

}

sealed class DataState {
    data class IsLoading(var state : Boolean) : DataState()
    data class ShowToast(var message : String) : DataState()
    object Reset : DataState()
    object Success: DataState()
}
