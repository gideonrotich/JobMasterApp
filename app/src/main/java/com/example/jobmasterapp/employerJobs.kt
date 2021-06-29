package com.example.jobmasterapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jobmasterapp.Adapter.JobAdapter
import com.example.jobmasterapp.Model.Job
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class employerJobs : Fragment() {

    private var recyclerViewajiry: RecyclerView? = null
    private var jobAdapter: JobAdapter? = null
    private var mJob: MutableList<Job>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_employer_jobs, container, false)

        recyclerViewajiry = view.findViewById(R.id.recycler_view_ajiry)
        recyclerViewajiry?.setHasFixedSize(true)
        val linearLayout = LinearLayoutManager(context)
        linearLayout.reverseLayout = true
        linearLayout.stackFromEnd = true
        recyclerViewajiry?.layoutManager = linearLayout
        mJob = ArrayList()
        jobAdapter = context?.let { JobAdapter(it,mJob as ArrayList<Job>,true) }
        recyclerViewajiry?.adapter = jobAdapter
        recyclerViewajiry?.visibility = View.VISIBLE
        retrieveJobs()

        return view
    }

    private fun retrieveJobs() {
        val jobsRef = FirebaseDatabase.getInstance().getReference("Jobs").child("Building and Construction")
        jobsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {

                mJob?.clear()

                for (snapshot in p0.children)
                {
                    val job = snapshot.getValue(Job::class.java)
                    if (job != null)
                    {
                        mJob?.add(job)
                    }
                    jobAdapter?.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }


}