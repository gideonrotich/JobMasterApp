package com.example.jobmasterapp

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_company.*
import kotlinx.android.synthetic.main.activity_signup.*

class CompanyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company)


        individual.setOnClickListener {
            startActivity(Intent(this,SignupActivity::class.java))
        }

        btnsignup_company.setOnClickListener {
            CreateAccount()
        }

        val languages = resources.getStringArray(R.array.Languages)

        val spinner = findViewById<Spinner>(R.id.techtextonetest)
        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item, languages
            )
            spinner.adapter = adapter


            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    val me = languages[position]
                    wapionetest.text = me

                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }

        }
    }

    private fun CreateAccount() {
        val fullnamerep = fullname_signuprep.text.toString()
        val emailrep = email_signuprep.text.toString()
        val mobilerep = mobile_signuprep.text.toString()
        val industry = wapionetest.text.toString()
        val website = reg_signup.text.toString()
        val companyname = reg.text.toString()
        val aboutcompany = reg_me.text.toString()
        val headquarters = location_one.text.toString()
        val founded = location_one_test.text.toString()
        val companysize = location_one_size.text.toString()
        val companyemail = mail_company_one.text.toString()
        val companypassword = pass_two.text.toString()


        when{
            TextUtils.isEmpty(fullnamerep) -> Toast.makeText(this,"FullName is required", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(emailrep) -> Toast.makeText(this,"Personal Email is required", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(mobilerep) -> Toast.makeText(this,"Mobile Number is required", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(companyname) -> Toast.makeText(this,"Company Name is required", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(companyemail) -> Toast.makeText(this,"Company Email is required", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(companypassword) -> Toast.makeText(this,"Company Password is required", Toast.LENGTH_LONG).show()


            else -> {

                val progressDialog = ProgressDialog(this@CompanyActivity)
                progressDialog.setTitle("Sign Up")
                progressDialog.setMessage("Please wait, this might take a while...")
                val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()


                mAuth.createUserWithEmailAndPassword(companyemail,companypassword)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful)
                        {
                            saveUserInfo(fullnamerep,emailrep,mobilerep,industry,website,companyname,aboutcompany,headquarters,founded,companysize,companyemail,progressDialog)
                        }
                        else
                        {
                            val message = task.exception!!.toString()
                            Toast.makeText(this, "Error: $message", Toast.LENGTH_LONG).show()
                            mAuth.signOut()
                            progressDialog.dismiss()
                        }
                    }
            }
        }

    }

    private fun saveUserInfo(
        fullnamerep: String,
        emailrep: String,
        mobilerep: String,
        industry: String,
        website: String,
        companyname: String,
        aboutcompany: String,
        headquarters: String,
        founded: String,
        companysize: String,
        companyemail: String,
        progressDialog: ProgressDialog
    ) {

        val currentUserID = FirebaseAuth.getInstance().currentUser!!.uid
        val usersRef: DatabaseReference = FirebaseDatabase.getInstance().reference.child("Company")

        val userMap = HashMap<String, Any>()
        userMap["fullnamerepresentative"] = fullnamerep
        userMap["emailrepresentative"] = emailrep
        userMap["mobilerepresentative"] = mobilerep
        userMap["industry"] = industry
        userMap["website"] = website
        userMap["companyname"] = companyname
        userMap["aboutcompany"] = aboutcompany
        userMap["headquarters"] = headquarters
        userMap["uid"] = currentUserID
        userMap["founded"] = founded
        userMap["companysize"] = companysize
        userMap["companyemail"] = companyemail
        userMap["image"] = "gs://jobstock-17213.appspot.com/Default Images/person.png"

        usersRef.child(currentUserID).setValue(userMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful)
                {
                    progressDialog.dismiss()
                    Toast.makeText(this,"Account has been created successfully", Toast.LENGTH_LONG).show()

                    val intent = Intent(this@CompanyActivity,MainActivity8::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                }
                else
                {
                    val message = task.exception!!.toString()
                    Toast.makeText(this, "Error: $message",Toast.LENGTH_LONG).show()
                    FirebaseAuth.getInstance().signOut()
                    progressDialog.dismiss()
                }
            }

    }
}