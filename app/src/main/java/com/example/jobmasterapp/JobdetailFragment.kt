package com.example.jobmasterapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.example.jobmasterapp.Model.Job
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_editprofile.*
import kotlinx.android.synthetic.main.job_layout.view.*

class JobdetailFragment : Fragment() {
    private lateinit var profileId:String
    private lateinit var firebaseUser:FirebaseUser
    private lateinit var testing:String
    private lateinit var lawama:String



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {




        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.job_layout, container, false)
        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        val pref = context?.getSharedPreferences("PREFS",Context.MODE_PRIVATE)



        if (pref != null)
        {
            this.profileId = pref.getString("profileId","none").toString()
            this.lawama = pref.getString("lawama","none").toString()
        }


        else if (profileId != firebaseUser.uid)
        {

        }
       // userInfo()

        //testionone

        val jobsRef = FirebaseDatabase.getInstance().getReference().child("Jobs").child(lawama).child(profileId)

        jobsRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
//                if (context != null)
//                {
//                    return
//                }
                if (p0.exists())
                {
                    val jobone = p0.getValue<Job>(Job::class.java)

                    val testing = jobone!!.getCategory()


                    val jobsoneRef = FirebaseDatabase.getInstance().getReference().child("Jobs").child(testing).child(profileId)

                    jobsoneRef.addValueEventListener(object : ValueEventListener{
                        override fun onDataChange(p0: DataSnapshot) {
//                if (context != null)
//                {view?.harmo?.text = job!!.getJobtitle()
                                
//                    return
//                }
                            if (p0.exists())
                            {
                                val job = p0.getValue<Job>(Job::class.java)

                                Picasso.get().load(job!!.getImage()).placeholder(R.drawable.bag).into(view?.logo_one_company)
                                view?.job_name?.text = job!!.getJobtitle()
                                view?.det_company?.text = job!!.getCompanyname()
                                view?.wapi?.text = job!!.getJoblocation()
                                view?.mpakalini?.text = job!!.getJobtype()
                                view?.det_job?.text = job!!.getJobdescription()
                                view?.det_responsibility?.text = job!!.getResponsibilities()
                                view?.det_education?.text = job!!.getEducation()
                                view?.det_skills?.text = job!!.getSkills()
                                view?.title_app_text?.text  = job!!.getJobtitle()
                                view?.title_app_text_one?.text = job!!.getCompanyname()
                                view?.title_app_text_two?.text = job!!.getJoblocation()
                                view?.appl_io_one?.text = job!!.getDeadline()
                                view?.harmo?.text = job!!.getJobtitle()
                                view?.harmonize?.text = job!!.getCompanyname()





                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }
                    })

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

//endoftestingone


        return view
    }

    private fun userInfo()
    {
        val jobsRef = FirebaseDatabase.getInstance().getReference().child("Jobs").child(testing).child(profileId)

        jobsRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
//                if (context != null)
//                {
//                    return
//                }
                if (p0.exists())
                {
                    val job = p0.getValue<Job>(Job::class.java)

                    Picasso.get().load(job!!.getImage()).placeholder(R.drawable.bag).into(view?.logo_one_company)
                    view?.job_name?.text = job!!.getJobtitle()
                    view?.det_company?.text = job!!.getCompanyname()
                    view?.wapi?.text = job!!.getJoblocation()
                    view?.mpakalini?.text = job!!.getJobtype()
                    view?.det_job?.text = job!!.getJobdescription()
                    view?.det_responsibility?.text = job!!.getResponsibilities()
                    view?.det_education?.text = job!!.getEducation()
                    view?.det_skills?.text = job!!.getSkills()
                    view?.title_app_text?.text  = job!!.getJobtitle()
                    view?.title_app_text_one?.text = job!!.getCompanyname()
                    view?.title_app_text_two?.text = job!!.getJoblocation()
                    view?.appl_io_one?.text = job!!.getDeadline()

                    val testing = job!!.getCategory()



                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun onStop() {
        super.onStop()

        val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
        pref?.putString("profileId",firebaseUser.uid)
        pref?.apply()

    }

    override fun onPause() {
        super.onPause()

        val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
        pref?.putString("profileId",firebaseUser.uid)
        pref?.apply()
    }

    override fun onDestroy() {
        super.onDestroy()
        val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
        pref?.putString("profileId",firebaseUser.uid)
        pref?.apply()
    }


}