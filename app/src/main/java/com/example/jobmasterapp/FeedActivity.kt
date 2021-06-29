package com.example.jobmasterapp

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
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
import kotlinx.android.synthetic.main.activity_feed.*
import kotlinx.android.synthetic.main.activity_input_categories.*

class FeedActivity : AppCompatActivity() {

    private lateinit var firebaseUser: FirebaseUser
    private var checker = ""
    private var myUrl = ""
    private var imageUri: Uri? = null
    private var storageProfilePicRef: StorageReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)


        firebaseUser = FirebaseAuth.getInstance().currentUser!!

        storageProfilePicRef = FirebaseStorage.getInstance().reference.child("Feed Image")

        change_feed_logo.setOnClickListener {
            checker = "clicked"
            CropImage.activity()
                .setAspectRatio(1, 1)
                .start(this@FeedActivity)
        }

        postfeed.setOnClickListener {
            if (checker == "clicked") {

                uploadImageAndUploadjob()
            } else {

            }

        }
    }



    private fun uploadImageAndUploadjob() {
        val feedtext = feedtext.text.toString()
        val feeddetail = feeddetail.text.toString()
        val feedlink = feedlink.text.toString()


        when{
            imageUri == null -> Toast.makeText(this,"Please select image first", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(feedtext) -> Toast.makeText(this,"Feed text is required", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(feeddetail) -> Toast.makeText(this,"feed detail is required", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(feedlink) -> Toast.makeText(this,"feed link is required", Toast.LENGTH_LONG).show()



            else ->
            {
                val progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Posting Feed")
                progressDialog.setMessage("Please wait, this might take a while...")

                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()

                val fileRef  = storageProfilePicRef!!.child(firebaseUser!!.uid + ".jpg")

                var uploadTask: StorageTask<*>
                uploadTask = fileRef.putFile(imageUri!!)
                uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                    if (!task.isSuccessful)
                    {
                        task.exception?.let {
                            throw it
                            progressDialog.dismiss()
                        }
                    }
                    return@Continuation fileRef.downloadUrl

                }).addOnCompleteListener(OnCompleteListener<Uri>{ task ->
                    if (task.isSuccessful)
                    {
                        val downloadUrl = task.result
                        myUrl = downloadUrl.toString()
                        saveinfo(feedtext,feeddetail,feedlink,progressDialog)
                    }
                    else
                    {
                        progressDialog.dismiss()
                    }

                })
            }


        }
    }

    private fun saveinfo(feedtext: String, feeddetail: String, feedlink: String, progressDialog: ProgressDialog) {
        val time = System.currentTimeMillis().toString()
        val currentUserID = FirebaseAuth.getInstance().currentUser!!.uid
        val feedRef: DatabaseReference = FirebaseDatabase.getInstance().reference.child("Feed")


        val userMap = HashMap<String, Any>()
        userMap["feedtext"] = feedtext
        userMap["feeddetail"] = feeddetail
        userMap["feedlink"] = feedlink
        userMap["uid"] = currentUserID
        userMap["image"] = myUrl

        feedRef.child(time).setValue(userMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful)
                {
                    progressDialog.dismiss()
                    Toast.makeText(this,"Feed has been posted successfully", Toast.LENGTH_LONG).show()

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
            feed_image_job.setImageURI(imageUri)

        }

    }
}