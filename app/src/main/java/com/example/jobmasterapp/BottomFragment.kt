package com.example.jobmasterapp

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jobmasterapp.Adapter.JobAdapter
import com.example.jobmasterapp.Model.Job
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_bottom.*
import kotlinx.android.synthetic.main.fragment_bottom.view.*

class BottomFragment : Fragment() {
    private lateinit var firebaseUser: FirebaseUser
    private var mJob: MutableList<Job>? = null
    private var jobAdapter: JobAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_bottom, container, false)


        var recyclerviewmodcom: RecyclerView? = null

        recyclerviewmodcom = view.findViewById(R.id.recycler_view_modcom)
        recyclerviewmodcom?.setHasFixedSize(true)
        recyclerviewmodcom?.itemAnimator = DefaultItemAnimator()
        val linearLayout = LinearLayoutManager(context)
        linearLayout.reverseLayout = true
        linearLayout.orientation = LinearLayoutManager.HORIZONTAL
        linearLayout.stackFromEnd = true
        recyclerviewmodcom?.layoutManager = linearLayout
        mJob = ArrayList()
        jobAdapter = context?.let { JobAdapter(it,mJob as ArrayList<Job>,true) }
        recyclerviewmodcom?.adapter = jobAdapter
        recyclerviewmodcom?.visibility = View.VISIBLE



                view.wordtaf.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }



            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (view.wordtaf.text.toString() == "")
                {

                }
                else
                {
                    taf.setOnClickListener {
                        recyclerviewmodcom?.visibility = View.VISIBLE
                        retrieveJobstwo()
                        inputsearch()
                        searchJob(s.toString().toLowerCase())
                    }



                }
            }
            override fun afterTextChanged(s: Editable?) {

            }
        })


        return view
    }

    private fun inputsearch() {
        val searchtext = wordtaf.text.toString()

        when {
            TextUtils.isEmpty(searchtext) -> Toast.makeText(
                context,
                "input job title first",
                Toast.LENGTH_LONG
            ).show()

            else ->{
                val currentUserID = FirebaseAuth.getInstance().currentUser!!.uid
                val time = System.currentTimeMillis().toString()
                val verifiedRef: DatabaseReference =
                    FirebaseDatabase.getInstance().reference.child("Searchtext").child(currentUserID)

                val userMap = HashMap<String, Any>()
                userMap["searchtext"] = searchtext.toLowerCase()
                userMap["uid"] = currentUserID

                verifiedRef.child(time).setValue(userMap)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {


                        } else {
                            val message = task.exception!!.toString()
                            Toast.makeText(context, "Error: $message", Toast.LENGTH_LONG).show()
                            FirebaseAuth.getInstance().signOut()

                        }
                    }
            }
        }
    }
//
        private fun searchJob(input: String) {
            val query = FirebaseDatabase.getInstance().getReference("Jobs").child("Building and Construction")
                .orderByChild("jobtitle")
                .startAt(input).endAt(input + "\uf8ff")


            query.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot)
                {
                    mJob?.clear()
                    if (p0.exists())
                    {
                        for (snapshot in p0.children)
                        {
                            val job = snapshot.getValue(Job::class.java)
                            if (job != null)
                            {
                                mJob?.add(job)
                                view!!.mmm.visibility = View.GONE
                            }

                        }
                        jobAdapter?.notifyDataSetChanged()
                    }
                    else
                    {
                        view!!.mmm.visibility = View.VISIBLE
                    }



                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }

        private fun retrieveJobstwo() {
            val jobsRef = FirebaseDatabase.getInstance().getReference("Jobs").child("Engineering")
            jobsRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
                    if (view?.wordtaf?.text.toString() == "")
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
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }




    }