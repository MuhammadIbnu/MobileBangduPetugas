package mibnu.team.petugas.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_detail_data_confirmed_i_i.*
import mibnu.team.petugas.R
import mibnu.team.petugas.models.Data
import mibnu.team.petugas.utils.Utilities
import mibnu.team.petugas.viewmodels.DataState
import mibnu.team.petugas.viewmodels.DataViewModel

class DetailDataConfirmedIIActivity : AppCompatActivity() {
    private lateinit var dataViewModel: DataViewModel
    private lateinit var keterangan_III: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_data_confirmed_i_i)

        dataViewModel= ViewModelProvider(this).get(DataViewModel::class.java)
        dataViewModel.listenToUIState().observer(this, Observer { handleui(it)  })
        fill()
//        confirmed_III()
//        checkBoxBehavior()
//        checkButtonYesAcceptable()
    }

    private fun handleui(it:DataState){
        when(it){
            is DataState.ShowToast -> Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            is DataState.Success -> finish()
        }
    }

    private fun fill() {
        getPassedData()?.let {
            nama.text = it.waris!!.nama
            kk.text = it.waris!!.kk
            nik.text = it.waris!!.nik

            Glide.with(this)
                .load(it.ktpMeninggalUrl)
                .apply(RequestOptions())
                .into(img_Ktp_Meninggal)
            Glide.with(this)
                .load( it.kkMeninggalUrl)
                .apply(RequestOptions())
                .into(img_kk_meninggal)
            Glide.with(this)
                .load( it.jamkesmasMeninggalUrl)
                .apply(RequestOptions())
                .into(img_jamkesmas)
            Glide.with(this)
                .load( it.ktpWarisUrl)
                .apply(RequestOptions())
                .into(img_ktp_waris)
            Glide.with(this)
                .load( it.kkWarisUrl)
                .apply(RequestOptions())
                .into(img_kk_waris)
            Glide.with(this)
                .load( it.aktaKematianUrl)
                .apply(RequestOptions())
                .into(img_Akta_kematian)
            Glide.with(this)
                .load( it.paktaWarisUrl)
                .apply(RequestOptions())
                .into(img_integritas_waris)
            Glide.with(this)
                .load( it.pernyataanWarisUrl)
                .apply(RequestOptions())
                .into(img_pernyataan_waris)
            Glide.with(this)
                .load(it.bukuTabungan)
                .apply(RequestOptions())
                .into(img_buku_tabungan)
        }
    }
//    private  fun confirmed_III (){
//
//        btnYes.setOnClickListener {
//            var id = getPassedData()?.kodeBerkas!!
//            if (opsi1.isChecked()){
//                keterangan_III =resources.getString(R.string.pilihsatu)
//            }
//            var confirmedIII = true
//            dataViewModel.updateConfirmedIII("Bearer ${Utilities.getToken(this@DetailDataConfirmedIIActivity)!!}",id,confirmedIII,keterangan_III)
//        }
//
//        btnNo.setOnClickListener {
//            var id = getPassedData()?.kodeBerkas!!
//            if (opsi2.isChecked()){
//                keterangan_III =resources.getString(R.string.opsiDua)
//            }
//            var confirmedIII = false
//            dataViewModel.updateConfirmedIII("Bearer ${Utilities.getToken(this@DetailDataConfirmedIIActivity)!!}",id,confirmedIII,keterangan_III)
//        }
//    }

    private fun getPassedData() = intent.getParcelableExtra<Data>("DATA")

}
