package mibnu.team.petugas.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import mibnu.team.petugas.R
import mibnu.team.petugas.utils.Utilities

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Thread(Runnable {
            if (Utilities.getToken(this@MainActivity) == null) {
                startActivity(Intent(this@MainActivity, MainActivity::class.java))
                finish()
            }
        }).start()
        dashboardMenus()
    }

    private fun dashboardMenus(){
        dataMasuk.setOnClickListener {
            startActivity(Intent(this,DataBaruActivity::class.java))
        }
        dataDinkes.setOnClickListener {
            startActivity(Intent(this,ConfirmTwoActivity::class.java))
        }

        berkasSah.setOnClickListener {
            startActivity(Intent(this,DataValidActivity::class.java))
        }
        imageView2.setOnClickListener {
            startActivity(Intent(this,ProfileActivity::class.java))
        }
        dataSurvey.setOnClickListener {
            startActivity(Intent(this,SurveyActivity::class.java))
        }
    }
}
