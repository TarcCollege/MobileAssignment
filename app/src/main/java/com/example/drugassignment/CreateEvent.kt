package com.example.drugassignment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.drugassignment.databinding.ActivityCreateEventBinding
import kotlinx.android.synthetic.main.activity_create_event.*
import kotlinx.android.synthetic.main.fragment_drug_info.*
import java.text.SimpleDateFormat
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS

class CreateEvent : AppCompatActivity() {

    private lateinit var binding : ActivityCreateEventBinding
    private lateinit var cal : Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_event)

        binding  =  DataBindingUtil.setContentView(this, R.layout.activity_create_event)

        binding.selectDate.setOnClickListener {
            selectDate()
        }

        binding.selectTime.setOnClickListener {
            selectTIme()
        }

        binding.totalTime.setOnClickListener{
            showTotal()
        }

    }

    @SuppressLint("SetTextI18n")
    private fun selectDate() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

            // Display Selected date in textbox
            textDate.text = "" + dayOfMonth + " " + (monthOfYear + 1) + ", " + year
        }, year, month, day)

        dpd.show()
    }

    private fun selectTIme() {
       cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            textTime.text = SimpleDateFormat("HH:mm").format(cal.time)
        }

        TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
    }

    private fun showTotal() {
        binding.totalTime.text = cal.toString()
    }
}
