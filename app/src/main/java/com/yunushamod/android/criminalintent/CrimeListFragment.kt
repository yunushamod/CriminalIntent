package com.yunushamod.android.criminalintent

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class CrimeListFragment private constructor() : Fragment() {
    private val crimeListViewModel: CrimeListViewModel by lazy{
        ViewModelProvider(this).get(CrimeListViewModel::class.java)
    }
    private lateinit var crimeRecyclerView: RecyclerView
    private var crimeAdapter: CrimeAdapter? = CrimeAdapter(emptyList())
    private val simpleDateTime = SimpleDateFormat("EEE, dd-MM-yyyy")
    private var callbacks: Callbacks? = null

    interface Callbacks{
        fun onCrimeSelected(uuid: UUID)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crime_list, container, false)
        crimeRecyclerView = view.findViewById(R.id.crime_recycler_view)
        crimeRecyclerView.layoutManager = LinearLayoutManager(context)
        crimeRecyclerView.adapter = crimeAdapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        crimeListViewModel.crimeListLiveData.observe(viewLifecycleOwner){
            it?.let{
                Log.i(TAG,"Crimes: #${it.size}")
                updateUI(it)
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    private inner class CrimeViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener{
        private val titleTextView: TextView = itemView.findViewById(R.id.crime_title) as TextView
        private val dateTextView: TextView = itemView.findViewById(R.id.crime_date) as TextView
        private val solvedImageView: ImageView = itemView.findViewById(R.id.crime_solved) as ImageView
        var crime: Crime? = null
        init{
            itemView.setOnClickListener(this)
        }
        fun bind(crime: Crime){
            titleTextView.text = crime.title
            dateTextView.text = simpleDateTime.format(crime.date).toString()
            solvedImageView.visibility = if(crime.isSolved) View.VISIBLE else View.GONE
            this.crime = crime
        }

        override fun onClick(v: View?) {
            crime?.id?.let { callbacks?.onCrimeSelected(it) }
        }
    }

    private inner class CrimeAdapter(var crimes: List<Crime>) : RecyclerView.Adapter<CrimeViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeViewHolder {
            val view = layoutInflater.inflate(R.layout.list_item_crime, parent, false)
            return CrimeViewHolder(view)
        }

        override fun onBindViewHolder(holder: CrimeViewHolder, position: Int) {
            val crime = crimes[position]
            holder.bind(crime)
        }

        override fun getItemCount(): Int = crimes.size
    }

    private fun updateUI(crimes: List<Crime>){
        crimeAdapter = CrimeAdapter(crimes)
        crimeRecyclerView.adapter = crimeAdapter
    }

    companion object{
        private const val TAG: String = "CrimeListFragment"
        fun newInstance(): CrimeListFragment{
            return CrimeListFragment()
        }
    }
}