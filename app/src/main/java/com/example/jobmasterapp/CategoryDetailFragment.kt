package com.example.jobmasterapp

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jobmasterapp.Adapter.CategoryAdapter
import com.example.jobmasterapp.Adapter.JobAdapter
import com.example.jobmasterapp.Model.Category
import com.example.jobmasterapp.Model.Job
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.choose_category_item.view.*

class CategoryDetailFragment : Fragment() {
    private var categoryAdapter: CategoryAdapter? = null
    private var mCategory:MutableList<Category>? = null
    private var jobAdapter: JobAdapter? = null
    private lateinit var firebaseUser: FirebaseUser
    private var mJob: MutableList<Job>? = null
    private lateinit var nyinyi:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.choose_category_item, container, false)

        firebaseUser = FirebaseAuth.getInstance().currentUser!!

        val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)

        if (pref != null)
        {
            this.nyinyi = pref.getString("nyinyi","none").toString()
        }

        var recyclerviewleta: RecyclerView? = null

        recyclerviewleta = view.findViewById(R.id.recycler_view_leta)
        recyclerviewleta?.setHasFixedSize(true)
        recyclerviewleta?.itemAnimator = DefaultItemAnimator()
        val linearLayout = LinearLayoutManager(context)
        linearLayout.reverseLayout = true
        linearLayout.orientation = LinearLayoutManager.VERTICAL
        linearLayout.stackFromEnd = true
        recyclerviewleta?.layoutManager = linearLayout
        mJob = ArrayList()
        jobAdapter = context?.let { JobAdapter(it,mJob as ArrayList<Job>,true) }
        recyclerviewleta?.adapter = jobAdapter
        recyclerviewleta?.visibility = View.VISIBLE

        view.giddy.text = nyinyi


        val jobsRef = FirebaseDatabase.getInstance().getReference("Jobs").child(nyinyi)
        jobsRef.addValueEventListener(object : ValueEventListener {
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

        return view
    }


}