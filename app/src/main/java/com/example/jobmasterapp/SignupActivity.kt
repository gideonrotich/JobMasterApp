package com.example.jobmasterapp

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_jobposting.*
import kotlinx.android.synthetic.main.activity_main9.*
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        val window: Window = this@SignupActivity.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = ContextCompat.getColor(this@SignupActivity,R.color.giddy)

        wajuakwambaniniwetu.setOnClickListener {
            startActivity(Intent(this,CompanyActivity::class.java))
        }

        val languages = resources.getStringArray(R.array.Languages)

        val spinner = findViewById<Spinner>(R.id.techtextone)
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
                    wapione.text = me

                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }

        }


        btnsignup.setOnClickListener {
            CreateAccount()
        }
        email_textsijui.setOnClickListener {
            val intent = Intent(this,SigninActivity::class.java)
            startActivity(intent)
        }


    }

    private fun CreateAccount()
    {
        val fullname = fullname_signup.text.toString()
        val email = email_signup.text.toString()
        val mobile = wapione.text.toString()
        val password = password_signup.text.toString()
        val profession = proffession_text.text.toString()



        when{
            TextUtils.isEmpty(fullname) -> Toast.makeText(this,"FullName is required", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(email) -> Toast.makeText(this,"Email is required", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(mobile) -> Toast.makeText(this,"Job Category is required", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(password) -> Toast.makeText(this,"Password is required", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(profession) -> Toast.makeText(this,"Professional title is required", Toast.LENGTH_LONG).show()

            else -> {

                val progressDialog = ProgressDialog(this@SignupActivity)
                progressDialog.setTitle("Sign Up")
                progressDialog.setMessage("Please wait, this might take a while...")
                val mAuth:FirebaseAuth = FirebaseAuth.getInstance()
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()


                mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful)
                        {
                            saveUserInfo(fullname,email,mobile,profession,progressDialog)
                        }
                        else
                        {
                            val message = task.exception!!.toString()
                            Toast.makeText(this, "Error: $message",Toast.LENGTH_LONG).show()
                            mAuth.signOut()
                            progressDialog.dismiss()
                        }
                    }
            }
        }
    }

    private fun saveUserInfo(fullname: String, email: String, mobile: String,profession: String, progressDialog: ProgressDialog) {
        val currentUserID = FirebaseAuth.getInstance().currentUser!!.uid
        val usersRef:DatabaseReference = FirebaseDatabase.getInstance().reference.child("Users")

        val userMap = HashMap<String, Any>()
        userMap["fullname"] = fullname
        userMap["email"] = email
        userMap["mobile"] = mobile
        userMap["uid"] = currentUserID
        userMap["profession"] = profession
        userMap["image"] = "gs://jobstock-17213.appspot.com/Default Images/person.png"

        usersRef.child(currentUserID).setValue(userMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful)
                {
                    progressDialog.dismiss()

                    val intent = Intent(this@SignupActivity,MainActivity6::class.java)
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