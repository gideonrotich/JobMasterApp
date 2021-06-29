package com.example.jobmasterapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jobmasterapp.Adapter.SavedjobAdapter
import com.example.jobmasterapp.Model.Savedjob
import com.example.jobmasterapp.Model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class SavedFragment : Fragment() {
    private var savedjobAdapter: SavedjobAdapter? = null
    private var mSaved:MutableList<Savedjob>? = null
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var testing:String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_saved, container, false)

        firebaseUser = FirebaseAuth.getInstance().currentUser!!

        var recyclerviewsaved: RecyclerView? = null

        recyclerviewsaved = view.findViewById(R.id.recycler_view_savedjobs)
        recyclerviewsaved?.setHasFixedSize(true)
        recyclerviewsaved?.itemAnimator = DefaultItemAnimator()
        val linearLayout = LinearLayoutManager(context)
        linearLayout.reverseLayout = true
        linearLayout.orientation = LinearLayoutManager.VERTICAL
        linearLayout.stackFromEnd = true
        recyclerviewsaved?.layoutManager = linearLayout
        mSaved = ArrayList()
        savedjobAdapter = context?.let { SavedjobAdapter(it,mSaved as ArrayList<Savedjob>,true) }
        recyclerviewsaved?.adapter = savedjobAdapter
        recyclerviewsaved?.visibility = View.VISIBLE

        val userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseUser.uid)

        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {

                if (p0.exists())
                {
                    val user = p0.getValue<User>(User::class.java)


                    val testing = user!!.getUid()

        val jobsReff = FirebaseDatabase.getInstance().getReference("New").child(testing)
        jobsReff.addChildEventListener(object : ChildEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                mSaved?.clear()

                    if (snapshot.exists()){
                        val job = snapshot.getValue(Savedjob::class.java)
                        if (job != null)
                        {
                            mSaved?.add(job)
                        }
                        savedjobAdapter?.notifyDataSetChanged()
                    }

                }


            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                mSaved?.clear()

                if (snapshot.exists()){
                    val job = snapshot.getValue(Savedjob::class.java)
                    if (job != null)
                    {
                        mSaved?.add(job)
                    }
                    savedjobAdapter?.notifyDataSetChanged()
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                recyclerviewsaved.visibility = View.GONE
            }

        })

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })



//        val jobsRef = FirebaseDatabase.getInstance().getReference("New")
//        jobsRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(p0: DataSnapshot) {
//
//                mSaved?.clear()
//
//                for (snapshot in p0.children)
//                {
//                    val job = snapshot.getValue(Savedjob::class.java)
//                    if (job != null)
//                    {
//                        mSaved?.add(job)
//                    }
//                    savedjobAdapter?.notifyDataSetChanged()
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//
//            }
//        })






        return view
    }


}