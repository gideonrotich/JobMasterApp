package com.example.jobmasterapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jobmasterapp.Adapter.JobAdapter
import com.example.jobmasterapp.Model.Job
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity7 : AppCompatActivity() {

    private lateinit var firebaseUser: FirebaseUser
    private var mJob: MutableList<Job>? = null
    private var jobAdapter: JobAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main7)


        firebaseUser = FirebaseAuth.getInstance().currentUser!!

        var recyclerView: RecyclerView? = null

        recyclerView = findViewById(R.id.recycler_view_home_t)
        recyclerView?.setHasFixedSize(true)
        recyclerView.itemAnimator = DefaultItemAnimator()
        val linearLayout = LinearLayoutManager(this)
        linearLayout.reverseLayout = true
        linearLayout.orientation = LinearLayoutManager.HORIZONTAL
        linearLayout.stackFromEnd = true
        recyclerView?.layoutManager = linearLayout
        mJob = ArrayList()
        jobAdapter = this.let { JobAdapter(it,mJob as ArrayList<Job>,true) }
        recyclerView?.adapter = jobAdapter
        recyclerView?.visibility = View.VISIBLE


        retrieveJobs()


    }

    private fun retrieveJobs() {

        val jobsRef = FirebaseDatabase.getInstance().getReference("Jobs")
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
    }

}