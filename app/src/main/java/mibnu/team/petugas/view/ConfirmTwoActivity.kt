package mibnu.team.petugas.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_confirm_two.*
//import kotlinx.android.synthetic.main.activity_confirm_two.*
import mibnu.team.petugas.R
import mibnu.team.petugas.adapter.DataValidAdapter
import mibnu.team.petugas.models.Data
import mibnu.team.petugas.utils.Utilities
import mibnu.team.petugas.viewmodels.DataState
import mibnu.team.petugas.viewmodels.DataViewModel

class ConfirmTwoActivity : AppCompatActivity() {
    private  lateinit var dataViewModel: DataViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_two)

        setupUI()
        dataViewModel = ViewModelProvider(this).get(DataViewModel::class.java)
        dataViewModel.listenToUIState().observer(this, Observer { handleUIState(it) })
        dataViewModel.listenToDataConfirmII().observe(this, Observer { handleData(it) })
    }


    private fun setupUI(){
        rv_data.apply {
            layoutManager = LinearLayoutManager(this@ConfirmTwoActivity)
            adapter = DataValidAdapter(mutableListOf(), this@ConfirmTwoActivity)
        }
    }

    private fun handleData(it : List<Data>){
        rv_data.adapter?.let { adapter ->
            adapter as DataValidAdapter
            adapter.updateList(it)
        }
    }


    private fun handleUIState(it: DataState){
        when(it){
            is DataState.ShowToast -> toast(it.message)
            is DataState.IsLoading -> {
                if(it.state){
                    loading.visibility = View.VISIBLE
                }else{
                    loading.visibility = View.GONE
                }
            }
        }
    }


    private fun toast(m : String) = Toast.makeText(this, m, Toast.LENGTH_LONG).show()

    override fun onResume() {
        super.onResume()
        Utilities.getToken(this)?.let { t -> dataViewModel.fetchDatacomfirmII("Bearer $t") }
    }

}
