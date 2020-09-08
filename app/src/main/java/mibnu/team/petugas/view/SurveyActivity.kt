package mibnu.team.petugas.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.anychart.APIlib
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.enums.Align
import com.anychart.enums.LegendLayout
import kotlinx.android.synthetic.main.chart_pie.*
import mibnu.team.petugas.R
import mibnu.team.petugas.models.Survey
import mibnu.team.petugas.viewmodels.SurveyViewModel

class SurveyActivity : AppCompatActivity() {
    private lateinit var surveyViewModel: SurveyViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey)
        surveyViewModel = ViewModelProvider(this).get(SurveyViewModel::class.java)
        surveyViewModel.fectSurvey()
        surveyViewModel.listenToInfo().observe(this, Observer { attachSurveyInfo(it) })
    }

    private fun attachSurveyInfo(it:Survey){
        pie_chart.setProgressBar(progress_chart)
        APIlib.getInstance().setActiveAnyChartView(pie_chart)
        val pie = AnyChart.pie()
        val data:MutableList<DataEntry> = mutableListOf()
        data.add(ValueDataEntry("Baik",it.good))
        data.add(ValueDataEntry("Cukup",it.enough))
        data.add(ValueDataEntry("Buruk",it.bad))
        pie.data(data)
        pie.title("Hasil Survey Kepuasan Masyarakat")
        pie.labels().position("outsider")
        pie.legend().title().enabled(true)
        pie.legend().position("center-buttom").itemsLayout(LegendLayout.HORIZONTAL).align(Align.CENTER)
        pie_chart.setChart(pie)
    }
}
