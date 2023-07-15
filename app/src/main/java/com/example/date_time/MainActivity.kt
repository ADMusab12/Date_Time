package com.example.date_time

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TimePicker
import com.example.date_time.databinding.ActivityMainBinding
import java.util.Calendar

class MainActivity : AppCompatActivity(),DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener{

    private lateinit var binding: ActivityMainBinding

    //sharedpreference

    private lateinit var sharedPreferences: SharedPreferences
    private val sharedPreferencesKey="datetime_pref"
    private val dateKey="date"
    private val timeKey="time"

    var day =0
    var month=0
    var year=0
    var hour=0
    var minute=0

    var savedDay =0
    var savedMonth=0
    var savedYear=0
    var savedHour=0
    var savedMinute=0

    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        sharedPreferences=getSharedPreferences(sharedPreferencesKey, Context.MODE_PRIVATE)

        pickDate()
        displaySavedDateTime()
    }

    private fun pickDate() {
        binding.btPicker.setOnClickListener {
            getDateTimeCalender()

            DatePickerDialog(this,this,year,month,day).show()
        }
    }

    private fun getDateTimeCalender(){
        val cal =Calendar.getInstance()
        day=cal.get(Calendar.DAY_OF_MONTH)
        month=cal.get(Calendar.MONTH)
        year=cal.get(Calendar.YEAR)
        hour=cal.get(Calendar.HOUR)
        minute=cal.get(Calendar.MINUTE)
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay=dayOfMonth
        savedMonth=month
        savedYear=year

        getDateTimeCalender()
        TimePickerDialog(this,this,hour,minute,true).show()
    }

    @SuppressLint("SetTextI18n")
    override fun onTimeSet(p0: TimePicker?, hourOfDay: Int, minute: Int) {
        savedHour=hourOfDay
        savedMinute=minute

        val dateTimeString="$savedDay-$savedMonth-$savedYear\n Hour:$savedHour Minute:$savedMinute"

        val editor=sharedPreferences.edit()
        editor.putString(dateKey,dateTimeString)
        editor.apply()

        binding.tvTime.text=dateTimeString

    }
    private fun displaySavedDateTime(){
        val savedDateTime=sharedPreferences.getString(dateKey,"")
        if (!savedDateTime.isNullOrEmpty()){
            binding.tvTime.text=savedDateTime
        }
    }
}