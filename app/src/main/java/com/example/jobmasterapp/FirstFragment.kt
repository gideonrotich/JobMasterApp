package com.example.jobmasterapp

import RecyclerAdapter
import android.app.ProgressDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jobmasterapp.Adapter.HomeAdapter
import com.example.jobmasterapp.Adapter.JobAdapter
import com.example.jobmasterapp.Model.Job
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main5.*
import kotlinx.android.synthetic.main.activity_tafutanetwork.*
import kotlinx.android.synthetic.main.activity_tafutanetwork.view.*
import kotlinx.android.synthetic.main.first_layout.view.*
import kotlinx.android.synthetic.main.fragment_search.view.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class FirstFragment : Fragment(){
    private var recyclerView: RecyclerView? = null
    private var homeAdapter: HomeAdapter? = null
    private var jobAdapter:JobAdapter? = null
    private var mJob: MutableList<Job>? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.first_layout,null)

        val connectionManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
             val activeNetwork: NetworkInfo? = connectionManager.activeNetworkInfo
             val isConnected = activeNetwork?.isConnectedOrConnecting == true

                if (isConnected){
                    recyclerView = view.findViewById(R.id.recycler_view_home)
                    recyclerView?.setHasFixedSize(true)
                    val linearLayout = LinearLayoutManager(context)
                    linearLayout.reverseLayout = true
                    linearLayout.stackFromEnd = true
                    recyclerView?.layoutManager = linearLayout
                    mJob = ArrayList()
                    jobAdapter = context?.let { JobAdapter(it,mJob as ArrayList<Job>,true) }
                    recyclerView?.adapter = jobAdapter
                    recyclerView?.visibility = View.VISIBLE
                    retrieveJobs()




                    view.btn_check.visibility = View.GONE
                    view.network.visibility = View.GONE
                }else{
                    view.network.setImageResource(R.drawable.ic_baseline_wifi_24)
                    view.TxtStatus.text = "                    No Connection \n" +
                            " You're not connected to the internet. \n" +
                            " Check your connection and try again"
                }

        view.btn_check.setOnClickListener {
            val activeNetwork: NetworkInfo? = connectionManager.activeNetworkInfo
            val isConnected = activeNetwork?.isConnectedOrConnecting == true


            if (isConnected){
                recyclerView = view.findViewById(R.id.recycler_view_home)
                recyclerView?.setHasFixedSize(true)
                val linearLayout = LinearLayoutManager(context)
                linearLayout.reverseLayout = true
                linearLayout.stackFromEnd = true
                recyclerView?.layoutManager = linearLayout
                mJob = ArrayList()
                jobAdapter = context?.let { JobAdapter(it,mJob as ArrayList<Job>,true) }
                recyclerView?.adapter = jobAdapter
                recyclerView?.visibility = View.VISIBLE
                retrieveJobs()

                view.btn_check.visibility = View.GONE
                view.network.visibility = View.GONE

            }else{

                view.TxtStatus.text = "                    No Connection \n You're not connected to the internet. \n Check your connection and try again"
                view.network.setImageResource(R.drawable.ic_baseline_wifi_24)

            }

        }

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