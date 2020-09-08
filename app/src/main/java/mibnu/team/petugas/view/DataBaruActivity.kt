package mibnu.team.petugas.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_data_baru.*
import mibnu.team.petugas.R
import mibnu.team.petugas.adapter.DataAdapter
import mibnu.team.petugas.models.Data
import mibnu.team.petugas.utils.Utilities
import mibnu.team.petugas.viewmodels.DataState
import mibnu.team.petugas.viewmodels.DataViewModel

class DataBaruActivity : AppCompatActivity() {
    private lateinit var dataViewModel: DataViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_baru)
        setupUI()
        dataViewModel = ViewModelProvider(this).get(DataViewModel::class.java)
        dataViewModel.listenToUIState().observer(this, Observer { handleUIState(it) })
        dataViewModel.listenToDatas().observe(this, Observer { handleData(it) })
    }

    private fun setupUI(){
        rv_data.apply {
            layoutManager = LinearLayoutManager(this@DataBaruActivity)
            adapter = DataAdapter(mutableListOf(), this@DataBaruActivity)
        }
    }

    private fun handleData(it : List<Data>){
        rv_data.adapter?.let { adapter ->
            adapter as DataAdapter
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
        Utilities.getToken(this)?.let { t -> dataViewModel.fetchDatas("Bearer $t") }
    }
}
