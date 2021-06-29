package com.example.jobmasterapp

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jobmasterapp.Model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_my.view.*


class MyFragment : Fragment() {
    private lateinit var firebaseUser: FirebaseUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_my, container, false)

        firebaseUser = FirebaseAuth.getInstance().currentUser!!

        fun GetAppVersion(context: Context): String{
            var version = ""
            try {
                val pInfo = context.packageManager.getPackageInfo(context.packageName,0)
                version = pInfo.versionName
            } catch (e: PackageManager.NameNotFoundException){
                e.printStackTrace()
            }
            return version
        }

        val versionName = context?.let { GetAppVersion(it) }

        view.versionn.text = "Version " + versionName

        view.accountinfo.setOnClickListener {
            val intent = Intent(context,BasicActivity::class.java)
            startActivity(intent)
        }

        view.postkazi.setOnClickListener {
            val intent = Intent(context,MainActivity8::class.java)
            startActivity(intent)
        }

        view.ujumbe.setOnClickListener {
            val intent = Intent(context,ujumbeActivity::class.java)
            startActivity(intent)
        }
        view.ccategory.setOnClickListener {
            val intent = Intent(context,inputCategoriesActivity::class.java)
            startActivity(intent)
        }
        view.ffeed.setOnClickListener {
            val intent = Intent(context,FeedActivity::class.java)
            startActivity(intent)
        }


        val userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseUser.uid)

        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {

                if (p0.exists())
                {
                    val user = p0.getValue<User>(User::class.java)


                    val testing = user!!.getFullname()
                    val career = user!!.getProfession()

                    view.you.text = testing
                    view.youtwo.text = career
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        view.out.setOnClickListener {
            FirebaseAuth.getInstance().signOut()

            val intent = Intent(context,SigninActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)

        }


        return view
    }



}