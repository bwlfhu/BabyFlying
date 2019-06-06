package com.allcam.babyflying.baby

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.allcam.babyflying.R
import com.allcam.babyflying.utils.Preference
import kotlinx.android.synthetic.main.activity_baby_add.*
import java.util.*

class BabyAddActivity : AppCompatActivity() {

    private var babyName: String by Preference(this, "babyName", "")
    private var bornDate: String by Preference(this, "bornDate", "")
    private var bornTime: String by Preference(this, "bornTime", "")
    private var babyGender: Int by Preference(this, "babyGender", 0)

    private val dateSelect: DatePickerDialog.OnDateSetListener =
        DatePickerDialog.OnDateSetListener { _, y, m, d -> selectBornDay(y, m, d) }

    private val timeSelect: TimePickerDialog.OnTimeSetListener =
        TimePickerDialog.OnTimeSetListener { _, h, m -> selectBornTime(h, m) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_baby_add)

        nameEt.setText(babyName)
        bornDayTv.text = bornDate
        bornTimeTv.text = bornTime
        maleCb.isChecked = babyGender == 1
        femaleCb.isChecked = babyGender == 0

        confirmBtn.setOnClickListener {
            addBabyInfo()
        }

        bornDayTv.setOnClickListener {
            val cal = Calendar.getInstance()
            val y = cal.get(Calendar.YEAR)
            val m = cal.get(Calendar.MONTH)
            val d = cal.get(Calendar.DATE)
            DatePickerDialog(this@BabyAddActivity, AlertDialog.THEME_HOLO_LIGHT, dateSelect, y, m, d).show()
        }

        bornTimeTv.setOnClickListener {
            val cal = Calendar.getInstance()
            val h = cal.get(Calendar.HOUR_OF_DAY)
            val m = cal.get(Calendar.MINUTE)
            TimePickerDialog(this@BabyAddActivity, AlertDialog.THEME_HOLO_LIGHT, timeSelect, h, m, true).show()
        }

        maleCb.setOnCheckedChangeListener { _, isChecked -> femaleCb.isChecked = !isChecked }
        femaleCb.setOnCheckedChangeListener { _, isChecked -> maleCb.isChecked = !isChecked }
    }

    private fun selectBornDay(year: Int, month: Int, day: Int) {
        val bornDay = String.format("%d - %02d - %02d", year, month + 1, day)
        bornDayTv.text = bornDay
    }

    private fun selectBornTime(hour: Int, minute: Int) {
        val bornTime = String.format("%02d : %02d", hour, minute)
        bornTimeTv.text = bornTime
    }

    private fun addBabyInfo() {
        val name = nameEt.text.toString()
        if (name.isEmpty()) {
            Toast.makeText(this, nameEt.hint, Toast.LENGTH_SHORT).show()
            return
        }

        val bDate = bornDayTv.text.toString()
        if (!bDate.contains('-')) {
            Toast.makeText(this, bornDayTv.text, Toast.LENGTH_SHORT).show()
            return
        }

        val bTime = bornTimeTv.text.toString()
        if (!bTime.contains(':')) {
            Toast.makeText(this, bornTimeTv.text, Toast.LENGTH_SHORT).show()
            return
        }

        babyName = name
        bornDate = bDate
        bornTime = bTime
        babyGender = if (femaleCb.isChecked) 0 else 1
        finish()
    }
}
