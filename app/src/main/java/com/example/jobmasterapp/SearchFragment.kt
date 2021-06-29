package com.example.jobmasterapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import kotlinx.android.synthetic.main.activity_main7.view.*
import kotlinx.android.synthetic.main.fragment_search.view.*
import kotlinx.android.synthetic.main.second_layout.view.*

class SearchFragment : Fragment()
{
    private var recyclerView:RecyclerView? = null
    private var jobAdapter:JobAdapter? = null
    private var mJob: MutableList<Job>? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        recyclerView = view.findViewById(R.id.recycler_view_search)
        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = LinearLayoutManager(context)

        mJob = ArrayList()
        jobAdapter = context?.let { JobAdapter(it,mJob as ArrayList<Job>,true) }
        recyclerView?.adapter = jobAdapter

//        view.wditwhat.addTextChangedListener(object: TextWatcher{
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//
//            }
//
//
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                if (view.wditwhat.text.toString() == "")
//                {
//
//                }
//                else
//                {
//                    recyclerView?.visibility = View.VISIBLE
//
//                    retrieveJobs()
//                    searchJob(s.toString().toLowerCase())
//                }
//            }
//            override fun afterTextChanged(s: Editable?) {
//
//            }
//        })
//
        return view
    }

    private fun searchJob(input: String) {
        val query = FirebaseDatabase.getInstance().getReference("Jobs")
            .orderByChild("jobtitle")
            .startAt(input).endAt(input + "\uf8ff")


        query.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot)
            {
                mJob?.clear()

                for (snapshot in p0.children)
                {
                    val job = snapshot.getValue(Job::class.java)
                    if (job != null)
                    {
                        mJob?.add(job)
                    }
                }

                jobAdapter?.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

//    private fun retrieveJobs() {
//        val jobsRef = FirebaseDatabase.getInstance().getReference("Jobs")
//        jobsRef.addValueEventListener(object : ValueEventListener{
//            override fun onDataChange(p0: DataSnapshot) {
//                if (view?.wditwhat?.text.toString() == "")
//                {
//                    mJob?.clear()
//
//                    for (snapshot in p0.children)
//                    {
//                        val job = snapshot.getValue(Job::class.java)
//                        if (job != null)
//                        {
//                            mJob?.add(job)
//                        }
//                    }
//
//                    jobAdapter?.notifyDataSetChanged()
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//        })
//    }


}