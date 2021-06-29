package com.example.jobmasterapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_input_categories.*
import kotlinx.android.synthetic.main.activity_verification.*

class verificationActivity : AppCompatActivity() {
    private lateinit var firebaseUser: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification)

        firebaseUser = FirebaseAuth.getInstance().currentUser!!


        postuser.setOnClickListener {
            uploaduser()
        }
    }

    private fun uploaduser() {
        val fullname = verificationname.text.toString()

        when {
            TextUtils.isEmpty(fullname) -> Toast.makeText(
                this,
                "FullName is required",
                Toast.LENGTH_LONG
            ).show()

            else ->{
                val currentUserID = FirebaseAuth.getInstance().currentUser!!.uid
                val time = System.currentTimeMillis().toString()
                val verifiedRef: DatabaseReference =
                    FirebaseDatabase.getInstance().reference.child("Verified")

                val userMap = HashMap<String, Any>()
                userMap["fullname"] = fullname.toLowerCase()
                userMap["uid"] = currentUserID

                verifiedRef.child(time).setValue(userMap)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                this,
                                "User has been verified successfully",
                                Toast.LENGTH_LONG
                            ).show()

                            val intent = Intent(this, MainActivity6::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)

                        } else {
                            val message = task.exception!!.toString()
                            Toast.makeText(this, "Error: $message", Toast.LENGTH_LONG).show()
                            FirebaseAuth.getInstance().signOut()

                        }
                    }
            }
        }

    }

    }
