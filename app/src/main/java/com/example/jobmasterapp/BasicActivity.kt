package com.example.jobmasterapp

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.jobmasterapp.Model.User
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_basic.*
import kotlinx.android.synthetic.main.fragment_editprofile.*

class BasicActivity : AppCompatActivity() {
    private lateinit var firebaseUser: FirebaseUser
    private var checker = ""
    private var myUrl = ""
    private var imageUri: Uri? = null
    private var storageProfilePicRef:StorageReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic)



        firebaseUser = FirebaseAuth.getInstance().currentUser!!

        storageProfilePicRef = FirebaseStorage.getInstance().reference.child("Profile Pictures")



        change_image.setOnClickListener {
            checker = "clicked"
            CropImage.activity()
                .setAspectRatio(1,1)
                .start(this@BasicActivity)
        }


        save_info.setOnClickListener {
            if (checker == "clicked")
            {

                uploadImageAndUpdateInfo()
            }
            else
            {
                updateUserInfoOnly()
            }

        }



        userInfo()


    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK && data!= null)
        {
            val result = CropImage.getActivityResult(data)
            imageUri = result.uri
            profile_image_settings.setImageURI(imageUri)

        }

    }



    private fun updateUserInfoOnly() {
        when {
            headline_text.text.toString() == "" -> {
                Toast.makeText(this,"Write fullname first", Toast.LENGTH_LONG).show()
            }
            highest_text.text.toString() == "" -> {
                Toast.makeText(this,"Write email first", Toast.LENGTH_LONG).show()
            }
            current_text.text.toString() == "" -> {
                Toast.makeText(this,"Write mobile first", Toast.LENGTH_LONG).show()
            }
            else -> {
                val jobsRef = FirebaseDatabase.getInstance().reference.child("Users")

                val userMap = HashMap<String, Any>()
                userMap["fullname"] = headline_text.text.toString().toLowerCase()
                userMap["email"] = highest_text.text.toString().toLowerCase()
                userMap["mobile"] = current_text.text.toString()

                jobsRef.child(firebaseUser.uid).updateChildren(userMap)

                Toast.makeText(this,"Account has been updated successfully", Toast.LENGTH_LONG).show()

                val intent = Intent(this,MainActivity6::class.java)
                startActivity(intent)

            }
        }



    }

    private fun userInfo()
    {
        val jobsRef = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseUser.uid)

        jobsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {

                if (p0.exists())
                {
                    val user = p0.getValue<User>(User::class.java)

                    Picasso.get().load(user!!.getImage()).placeholder(R.drawable.onee).into(profile_image_settings)
                    headline_text.setText(user!!.getFullname())
                    highest_text.setText(user!!.getEmail())
                    current_text.setText(user!!.getMobile())

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun uploadImageAndUpdateInfo()
    {


        when{
            imageUri == null -> Toast.makeText(this,"Please select image first",Toast.LENGTH_LONG).show()

            headline_text.text.toString() == "" -> {
                Toast.makeText(this,"Write fullname first", Toast.LENGTH_LONG).show()
            }
            highest_text.text.toString() == "" -> {
                Toast.makeText(this,"Write email first", Toast.LENGTH_LONG).show()
            }
            current_text.text.toString() == "" -> {
                Toast.makeText(this,"Write mobile first", Toast.LENGTH_LONG).show()
            }

            else -> {

                val progressDialog  = ProgressDialog(this)
                progressDialog.setTitle("Account Settings")
                progressDialog.setMessage("Please wait,this might take a while...")
                progressDialog.show()

                val fileRef  = storageProfilePicRef!!.child(firebaseUser!!.uid + ".jpg")

                var uploadTask: StorageTask<*>
                uploadTask = fileRef.putFile(imageUri!!)
                uploadTask.continueWithTask(Continuation <UploadTask.TaskSnapshot, Task<Uri>>{ task ->
                    if (!task.isSuccessful)
                    {
                        task.exception?.let {
                            throw it
                            progressDialog.dismiss()
                        }
                    }
                    return@Continuation fileRef.downloadUrl
                }).addOnCompleteListener (OnCompleteListener<Uri> {task ->
                    if (task.isSuccessful)
                    {
                        val downloadUrl = task.result
                        myUrl = downloadUrl.toString()

                        val ref  = FirebaseDatabase.getInstance().reference.child("Users")

                        val userMap = HashMap<String, Any>()
                        userMap["fullname"] = headline_text.text.toString().toLowerCase()
                        userMap["email"] = highest_text.text.toString().toLowerCase()
                        userMap["mobile"] = current_text.text.toString().toLowerCase()
                        userMap["image"] = myUrl

                        ref.child(firebaseUser.uid).updateChildren(userMap)

                        Toast.makeText(this,"Account has been updated successfully", Toast.LENGTH_LONG).show()

                        val intent = Intent(this,MainActivity6::class.java)
                        startActivity(intent)
                        finish()
                        progressDialog.dismiss()
                    }
                    else
                    {
                        progressDialog.dismiss()
                    }
                } )

//                else below
            }


        }
    }
}