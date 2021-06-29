package com.example.jobmasterapp

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_basic.*
import kotlinx.android.synthetic.main.activity_jobposting.*
import kotlinx.android.synthetic.main.activity_spinners.*

class JobpostingActivity : AppCompatActivity() {

    private lateinit var firebaseUser: FirebaseUser
    private var checker = ""
    private var myUrl = ""
    private var imageUri: Uri? = null
    private var storageProfilePicRef: StorageReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jobposting)

        val languages = resources.getStringArray(R.array.Languages)

        val spinner = findViewById<Spinner>(R.id.techtext)
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
                    wapi.text = me

                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }

        }

        firebaseUser = FirebaseAuth.getInstance().currentUser!!

        storageProfilePicRef = FirebaseStorage.getInstance().reference.child("Company Logo")

        change_image_job.setOnClickListener {
            checker = "clicked"
            CropImage.activity()
                .setAspectRatio(1, 1)
                .start(this@JobpostingActivity)
        }

        postjob.setOnClickListener {
            if (checker == "clicked") {

                uploadImageAndUploadjob()
            } else {

            }

        }
    }

    private fun uploadImageAndUploadjob() {
        val jobtitle = jobtitle.text.toString()
        val jobdescription = jobdescription.text.toString()
        val responsibilities = responsibilities.text.toString()
        val skills = skills.text.toString()
        val education = education.text.toString()
        val joblocation = joblocation.text.toString()
        val companyname = companyedit.text.toString()
        val category = wapi.text.toString()
        val salary = salatext.text.toString()
        val jobtype = jobtype.text.toString()
        val deadline = deadline.text.toString()

        when{
            imageUri == null -> Toast.makeText(this,"Please select image first",Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(jobtitle) -> Toast.makeText(this,"Job Title is required", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(jobdescription) -> Toast.makeText(this,"Job Description is required", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(responsibilities) -> Toast.makeText(this,"Responsibilities is required", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(skills) -> Toast.makeText(this,"skills is required", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(education) -> Toast.makeText(this,"education is required", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(joblocation) -> Toast.makeText(this,"location is required", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(jobtype) -> Toast.makeText(this,"job type is required", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(deadline) -> Toast.makeText(this,"deadline is required", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(companyname) -> Toast.makeText(this,"Company Name is required", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(category) -> Toast.makeText(this,"job category is required", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(salary) -> Toast.makeText(this,"salary is required", Toast.LENGTH_LONG).show()


            else ->
            {
                val progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Posting Job")
                progressDialog.setMessage("Please wait, this might take a while...")

                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()

                val fileRef  = storageProfilePicRef!!.child(firebaseUser!!.uid + ".jpg")

                var uploadTask: StorageTask<*>
                uploadTask = fileRef.putFile(imageUri!!)
                uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> {task ->
                    if (!task.isSuccessful)
                    {
                        task.exception?.let {
                            throw it
                            progressDialog.dismiss()
                        }
                    }
                    return@Continuation fileRef.downloadUrl

                }).addOnCompleteListener(OnCompleteListener<Uri>{task ->
                    if (task.isSuccessful)
                    {
                        val downloadUrl = task.result
                        myUrl = downloadUrl.toString()
                        saveinfo(jobtitle,jobdescription,responsibilities,skills,education,joblocation,jobtype,deadline,companyname,category,salary,progressDialog)
                    }
                    else
                    {
                        progressDialog.dismiss()
                    }

                })
            }


        }
    }

    private fun saveinfo(
        jobtitle: String,
        jobdescription: String,
        responsibilities: String,
        skills: String,
        education: String,
        joblocation: String,
        jobtype: String,
        deadline: String,
        companyname: String,
        category: String,
        salary: String,
        progressDialog: ProgressDialog
    ) {


        val currentUserID = FirebaseAuth.getInstance().currentUser!!.uid
        val jobsRef: DatabaseReference = FirebaseDatabase.getInstance().reference.child("Jobs").child(category)
        val time = (jobsRef.push().key).toString()

        val userMap = HashMap<String, Any>()
        userMap["jobtitle"] = jobtitle.toLowerCase()
        userMap["jobdescription"] = jobdescription
        userMap["responsibilities"] = responsibilities
        userMap["skills"] = skills
        userMap["education"] = education
        userMap["joblocation"] = joblocation
        userMap["uid"] = currentUserID
        userMap["jobtype"] = jobtype
        userMap["deadline"] = deadline
        userMap["companyname"] = companyname
        userMap["category"] = category
        userMap["salary"] = salary
        userMap["time"] = time
        userMap["image"] = myUrl


        jobsRef.child(time).setValue(userMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful)
                {
                    progressDialog.dismiss()
                    Toast.makeText(this,"Job has been posted successfully", Toast.LENGTH_LONG).show()

                    val intent = Intent(this,MainActivity6::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val result = CropImage.getActivityResult(data)
            imageUri = result.uri
            profile_image_job.setImageURI(imageUri)

        }

    }


}
