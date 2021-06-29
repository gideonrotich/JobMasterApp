package com.example.jobmasterapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jobmasterapp.Model.Employer
import com.example.jobmasterapp.Model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_basic.*
import kotlinx.android.synthetic.main.fragment_employer_profile.*

class employerProfile : Fragment() {
    private lateinit var firebaseUser: FirebaseUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_employer_profile, container, false)

        firebaseUser = FirebaseAuth.getInstance().currentUser!!

        val jobsRef = FirebaseDatabase.getInstance().getReference().child("Company").child(firebaseUser.uid)

        jobsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {

                if (p0.exists())
                {
                    val user = p0.getValue<Employer>(Employer::class.java)

                    mpakalininame.text = user!!.companyname
                    mamayao.text = user!!.companyname
                    webme.text = user!!.website
                    webmek.text = user!!.headquarters
                    webmefu.text = user!!.founded
                    webmemoo.text = user!!.companysize
                    webmemew.text = user!!.industry
                    webtu.text = user!!.aboutcompany

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        return view
    }


}