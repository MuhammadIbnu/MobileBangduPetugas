package mibnu.team.petugas.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mibnu.team.petugas.models.User
import mibnu.team.petugas.utils.SingleLiveEvent
import mibnu.team.petugas.utils.Utilities
import mibnu.team.petugas.webservice.ApiClient
import mibnu.team.petugas.webservice.WreppedRespond
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel(){
    private var api = ApiClient.instance()
    private  var state : SingleLiveEvent<UserState> = SingleLiveEvent()
    private var curruntUser = MutableLiveData<User>()

    fun login(username : String, password:String){
        state.value = UserState.IsLoading(true)
        api.login(username, password).enqueue(object : Callback<WreppedRespond<User>>{
            override fun onFailure(call: Call<WreppedRespond<User>>, t: Throwable) {
                println(t.message)
                state.value = UserState.ShowToast(t.message.toString())
                state.value = UserState.IsLoading(false)
            }

            override fun onResponse(call: Call<WreppedRespond<User>>, response: Response<WreppedRespond<User>>) {
                if (response.isSuccessful){
                    val body =response.body()
                    body?.let{
                        if (it.status){
                            state.value = UserState.Success(it.data.token!!)
                        }else{
                            state.value= UserState.ShowToast("Login gagal")
                        }
                    }
                }else{
                    state.value = UserState.ShowAlert("Tidak dapat masuk. Periksa kembali username dan kata sandi anda")
                }
                state.value = UserState.IsLoading(false)
            }

        })
    }

    fun validate(usernamepetugas: String, password: String):Boolean{
        state.value = UserState.Reset
        if (!Utilities.isValidUsername(usernamepetugas)){
            state.value = UserState.Validate(username="Username tidak valid")
            return false
        }
        if (!Utilities.isValidPassword(password)){
            state.value = UserState.Validate(password="Password tidak valid")
            return false
        }
        return true
    }

    fun listenUIState() = state
}

sealed class UserState{
    object Reset : UserState()
    data class ShowAlert(var message : String) : UserState()
    data class IsLoading(var state : Boolean) : UserState()
    data class ShowToast(var message: String) : UserState()
    data class Validate(var username: String? = null, var  password: String? = null) : UserState()
    data class Success(var param:String) : UserState()
}