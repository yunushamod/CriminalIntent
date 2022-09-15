package com.yunushamod.android.criminalintent

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerFragment private constructor() : DialogFragment() {
    interface Callbacks{
        fun onDateSelected(date: Date)
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val listener = DatePickerDialog.OnDateSetListener{
            _, year, month, day ->
            val resultDate: Date = GregorianCalendar(year, month, day).time
            targetFragment?.let{
                (it as Callbacks)?.onDateSelected(resultDate)
            }
        }
        val date = arguments?.getSerializable(CRIME_DATE) as Date?
        val calendar = Calendar.getInstance()
        date?.let {
            calendar.time = it
        }
        val initialYear = calendar.get(Calendar.YEAR)
        val initialMonth = calendar.get(Calendar.MONTH)
        val initialDay = calendar.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(
            requireContext(),
            listener,
            initialYear,
            initialMonth,
            initialDay
        )
    }

    companion object{
        private const val CRIME_DATE = "Crime_Date"
        fun newInstance(date: Date): DatePickerFragment{
            return DatePickerFragment().apply {
                val args = Bundle().apply {
                    putSerializable(CRIME_DATE, date)
                }
                arguments = args
            }
        }
    }
}