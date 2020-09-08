package mibnu.team.petugas.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mibnu.team.petugas.models.Survey
import mibnu.team.petugas.utils.SingleLiveEvent
import mibnu.team.petugas.webservice.ApiClient
import mibnu.team.petugas.webservice.WreppedRespond
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SurveyViewModel : ViewModel(){
    private var api = ApiClient.instance()
    private  var state : SingleLiveEvent<SurveyState> = SingleLiveEvent()
    private var hasil = MutableLiveData<Survey>()
    private fun setLoading() { state.value = SurveyState.IsLoading(true) }
    private fun hideLoading() { state.value = SurveyState.IsLoading(false) }
    private fun showToast(mesage : String) { state.value = SurveyState.ShowToast(mesage) }
    fun fectSurvey(){
        api.survey().enqueue(object : Callback<WreppedRespond<Survey>>{
            override fun onFailure(call: Call<WreppedRespond<Survey>>, t: Throwable) {
                println(t.printStackTrace())
                println(t.message)
                hideLoading()
                showToast(t.message.toString())
            }

            override fun onResponse(
                call: Call<WreppedRespond<Survey>>,
                response: Response<WreppedRespond<Survey>>
            ) {
                if(response.isSuccessful){
                    val b = response.body()
                    println(b.toString())
                    hasil.postValue(b?.data)
                }else{
                    showToast("Kesalahan saat mengambil data")
                }
                hideLoading()
            }

        })
    }
    fun listenToInfo()=hasil
}

sealed class SurveyState {
    data class IsLoading(var state : Boolean) : SurveyState()
    data class ShowToast(var message : String) : SurveyState()
    object Reset : SurveyState()
    object Success: SurveyState()
}