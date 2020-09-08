package mibnu.team.petugas.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_profile.*
import mibnu.team.petugas.R
import mibnu.team.petugas.models.User
import mibnu.team.petugas.utils.Utilities
import mibnu.team.petugas.viewmodels.UserState
import mibnu.team.petugas.viewmodels.UserViewModel

class ProfileActivity : AppCompatActivity() {
    private lateinit var userViewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        userViewModel.listenUIState().observer(this, Observer { handleUI(it) })
        userViewModel.listenToCurrentUser().observe(this, Observer { attachToUI(it) })

        Utilities.getToken(this)?.let { token -> userViewModel.profile("Bearer $token") }
        updatePass()
    }

    private fun handleUI(it : UserState){
        when(it){
            is UserState.ShowToast -> toast(it.message)
            is UserState.Sucess -> finish()
        }
    }

    private fun attachToUI(user : User){
        nik.setText(user.username)
        name.setText(user.name)
    }

    private fun updatePass(){
        btn_simpan.setOnClickListener {
            val password = password.text.toString().trim()
            userViewModel.updateProfile("Bearer ${Utilities.getToken(this@ProfileActivity)!!}", password)
        }
        btn_logout.setOnClickListener {
            Utilities.clearToken(this@ProfileActivity)
            startActivity(Intent(this@ProfileActivity,LoginActivity::class.java))
            this@ProfileActivity.finish()
        }
    }

    private fun toast(message : String) = Toast.makeText(this@ProfileActivity, message, Toast.LENGTH_SHORT).show()
}
