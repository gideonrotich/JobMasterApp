package com.example.jobmasterapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.example.jobmasterapp.Adapter.StoryAdapter
import com.example.jobmasterapp.Model.Story
import com.example.jobmasterapp.Model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import jp.shts.android.storiesprogressview.StoriesProgressView
import kotlinx.android.synthetic.main.activity_story.*

class StoryActivity : AppCompatActivity(), StoriesProgressView.StoriesListener {
    var currentUserId:String = ""
    var userId: String = ""
    var counter = 0
    var pressTime = 0L
    var limit = 500L
    
    var imagesList: List<String>? = null
    var storyIdsList: List<String>? = null

    var storiesProgressView: StoriesProgressView? = null

   private val onTouchListener = View.OnTouchListener {view,motionEvent ->
       when(motionEvent?.action){
           MotionEvent.ACTION_DOWN ->
           {
               pressTime = System.currentTimeMillis()
               storiesProgressView!!.pause()
               return@OnTouchListener  false
           }
           MotionEvent.ACTION_UP ->
           {
               val now = System.currentTimeMillis()
               storiesProgressView!!.resume()
               return@OnTouchListener  limit < now - pressTime
           }
       }
       false
   }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story)
        
        currentUserId = FirebaseAuth.getInstance().currentUser!!.uid
        userId = intent.getStringExtra("userId").toString()


        storiesProgressView = findViewById(R.id.stories_progress)


        layout_seen.visibility = View.GONE
        story_delete.visibility = View.GONE

        if (userId == currentUserId)
        {
            layout_seen.visibility = View.VISIBLE
            story_delete.visibility = View.VISIBLE
        }

        getStories(userId!!)
        userInfo(userId!!)

        val reverse:  View = findViewById(R.id.reverse)
        reverse.setOnClickListener { storiesProgressView!!.reverse() }
        reverse.setOnTouchListener(onTouchListener)

        val skip:  View = findViewById(R.id.skip)
        skip.setOnClickListener { storiesProgressView!!.skip() }
        skip.setOnTouchListener(onTouchListener)


        story_delete.setOnClickListener {
            val ref = FirebaseDatabase.getInstance().reference.child("Story").child(userId!!).child(storyIdsList!![counter])

            ref.removeValue().addOnCompleteListener { task ->
                if (task.isSuccessful)
                {
                    Toast.makeText(this,"Story deleted..", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    
    
    private fun getStories(userId: String){
        imagesList = ArrayList()
        storyIdsList = ArrayList()

        val ref  = FirebaseDatabase.getInstance().reference.child("Story").child(userId!!)
        
        ref.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                (imagesList as ArrayList<String>).clear()
                (storyIdsList as ArrayList<String>).clear()
                
                for (snapshot in p0.children)
                {
                    val story: Story? = snapshot.getValue<Story>(Story::class.java)
                    val timeCurrent = System.currentTimeMillis()

                    if (timeCurrent>story!!.getTimeStart() && timeCurrent<story!!.getTimeEnd())
                    {
                        (imagesList as ArrayList<String>).add(story.getImageUrl())
                        (storyIdsList as ArrayList<String>).add(story.getStoryId())
                    }
                }

                storiesProgressView!!.setStoriesCount((imagesList as ArrayList<String>).size)
                storiesProgressView!!.setStoryDuration(7000L)
                storiesProgressView!!.setStoriesListener(this@StoryActivity)
                storiesProgressView!!.startStories(counter)
                Picasso.get().load(imagesList!!.get(counter)).into(image_story)

                addViewToStory(storyIdsList!!.get(counter))
                seenNumber(storyIdsList!!.get(counter))

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun userInfo(userId: String)
    {
        val jobsRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userId)

        jobsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {

                if (p0.exists())
                {
                    val user = p0.getValue<User>(User::class.java)

                    Picasso.get().load(user!!.getImage()).placeholder(R.drawable.onee).into(story_profile_image)

                   story_username.text = user.getFullname()

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    
    
    private fun addViewToStory(storyId: String)
    {
        FirebaseDatabase.getInstance().reference.child("Story").child(userId!!)
            .child(storyId).child("views").child(currentUserId).setValue(true)
    }
    
    
    private fun seenNumber(storyId: String)
    {
        val ref = FirebaseDatabase.getInstance().reference.child("Story").child(userId!!)
            .child(storyId).child("views")
        
        ref.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                seen_number.text = "" + snapshot.childrenCount
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun onComplete() {
        finish()
    }

    override fun onPrev() {

        if (counter - 1< 0) return
        Picasso.get().load(imagesList!![--counter]).into(image_story)


        seenNumber(storyIdsList!![counter])
    }

    override fun onNext() {
        Picasso.get().load(imagesList!![++counter]).into(image_story)

        addViewToStory(storyIdsList!![counter])
        seenNumber(storyIdsList!![counter])
    }

    override fun onDestroy() {
        super.onDestroy()
        storiesProgressView!!.destroy()
    }

    override fun onResume() {
        super.onResume()
        storiesProgressView!!.resume()

    }

    override fun onPause() {
        super.onPause()
        storiesProgressView!!.pause()
    }
}