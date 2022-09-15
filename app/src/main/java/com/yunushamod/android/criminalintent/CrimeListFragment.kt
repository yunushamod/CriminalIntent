package com.yunushamod.android.criminalintent

import android.content.Context
import android.os.Bundle
import android.text.format.DateFormat
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yunushamod.android.criminalintent.models.Crime
import com.yunushamod.android.criminalintent.viewmodels.CrimeListViewModel
import java.util.*

class CrimeListFragment private constructor() : Fragment() {
    private var callbacks : Callbacks? = null
    private val crimeListViewModel : CrimeListViewModel by lazy{
        ViewModelProvider(this)[CrimeListViewModel::class.java]
    }
    private var adapter: CrimeListViewAdapter? = CrimeListViewAdapter(emptyList())
    private lateinit var crimeRecyclerView : RecyclerView

    /**
     * Required interface for hosting activities
     */
    interface Callbacks{
        fun onCrimeSelected(crimeId: UUID)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.frragment_crime_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.new_crime -> {
                val crime = Crime()
                crimeListViewModel.addCrime(crime)
                callbacks?.onCrimeSelected(crime.id)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crime_list, container, false)
        crimeRecyclerView = view.findViewById(R.id.recycler_view)
        crimeRecyclerView.layoutManager = LinearLayoutManager(context)
        crimeRecyclerView.adapter = adapter
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Observe the crimeListLiveData for data
        // Also ties the observer to the lifecycle of this fragment using <code>viewLifecycleOwner</code>
        crimeListViewModel.crimeListLiveData.observe(viewLifecycleOwner){
            it?.let {
                updateUI(it)
            }
        }
    }

    private inner class CrimeListViewHolder(val view: View) : RecyclerView.ViewHolder(view), View.OnClickListener{
        private lateinit var crime: Crime
        private val crimeTitle: TextView = itemView.findViewById(R.id.crime_title)
        private val crimeDate: TextView = itemView.findViewById(R.id.crime_date)
        private val crimeSolved: ImageView = itemView.findViewById(R.id.crime_solved)
        fun bind(crime: Crime){
            this.crime = crime
            crimeTitle.text = crime.title
            crimeDate.text = DateFormat.format("EEE, dd MM yyyy", crime.date)
            crimeSolved.visibility = if(crime.isSolved) View.VISIBLE else View.GONE
        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            callbacks?.onCrimeSelected(crime.id)
        }
    }

    private fun updateUI(crimes: List<Crime>){
        this.adapter = CrimeListViewAdapter(crimes)
        crimeRecyclerView.adapter = adapter
    }

    private inner class CrimeListViewAdapter(val crimes: List<Crime>) : RecyclerView.Adapter<CrimeListViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeListViewHolder {
            val crimeView = layoutInflater.inflate(R.layout.list_item_crime, parent, false);
            return CrimeListViewHolder(crimeView)
        }

        override fun onBindViewHolder(holder: CrimeListViewHolder, position: Int) {
            val crime = crimes[position]
            holder.bind(crime)
        }

        override fun getItemCount(): Int = crimes.size

    }

    companion object{
        private const val TAG = "CrimeListFragment"
        fun newInstance() = CrimeListFragment()
    }
}