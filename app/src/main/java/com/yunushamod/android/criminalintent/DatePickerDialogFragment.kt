package com.yunushamod.android.criminalintent

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerDialogFragment private constructor() : DialogFragment() {
    interface Callbacks{
        fun onDateSelected(date: Date)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val date = arguments?.getSerializable(ARG_DATE) as Date
        val dateListener = DatePickerDialog.OnDateSetListener{
                _: DatePicker, year: Int, month: Int, day: Int ->
            val resultDate: Date =  GregorianCalendar(year, month, day).time
            targetFragment?.let{
                (it as Callbacks).onDateSelected(resultDate)
            }
        }
        val calendar = Calendar.getInstance()
        calendar.time = date
        val initialYear = calendar.get(Calendar.YEAR)
        val initialMonth = calendar.get(Calendar.MONTH)
        val initialDay = calendar.get(Calendar.DAY_OF_MONTH)
        return DatePickerDialog(
            requireContext(),
            dateListener,
            initialYear,
            initialMonth,
            initialDay
        )
    }

    companion object{
        private const val ARG_DATE = "date"
        fun newInstance(date: Date): DatePickerDialogFragment{
            val args = Bundle().apply {
                putSerializable(ARG_DATE, date)
            }
            return DatePickerDialogFragment().apply {
                arguments = args
            }
        }
    }
}