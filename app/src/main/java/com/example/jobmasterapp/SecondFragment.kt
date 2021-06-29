package com.example.jobmasterapp

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.example.jobmasterapp.Adapter.*
import com.example.jobmasterapp.Model.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_basic.*
import kotlinx.android.synthetic.main.activity_main7.*
import kotlinx.android.synthetic.main.activity_main7.view.*
import kotlinx.android.synthetic.main.fragment_editprofile.view.*
import kotlinx.android.synthetic.main.second_layout.*
import kotlinx.android.synthetic.main.second_layout.cat
import kotlinx.android.synthetic.main.second_layout.hao
import kotlinx.android.synthetic.main.second_layout.view.*
import kotlinx.android.synthetic.main.second_layout.view.cat

class SecondFragment : Fragment(){
    private var postAdapter: PostAdapter? = null
    private var postList:MutableList<Post>? = null
    private var storyAdapter: StoryAdapter? = null
    private var storyList:MutableList<Story>? = null
    private lateinit var firebaseUser: FirebaseUser
    private var homeAdapter: HomeAdapter? = null
    private var mJob: MutableList<Job>? = null
    private var categoryAdapter: CategoryAdapter? = null
    private var searchAdapter:SearchAdapter? = null
    private var mCategory:MutableList<Category>? = null
    private var feedAdapter: FeedAdapter? = null
    private var jobAdapter:JobAdapter? = null
    private var mFeed:MutableList<Feed>? = null
    private var mUser:MutableList<User>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.second_layout,null)

        firebaseUser = FirebaseAuth.getInstance().currentUser!!

        var recyclerviewfeed:RecyclerView? = null
        var recyclerViewCategories:RecyclerView? = null
        var recyclerView: RecyclerView? = null
        var recyclerViewtafuta:RecyclerView? = null
        var recyclerViewSearch:RecyclerView? = null




        setupAPICall()
//        recyclerViewStory = view.findViewById(R.id.recycler_view_story)
//        recyclerViewStory.setHasFixedSize(true)
//        val linearLayoutManager2 = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
//        recyclerViewStory.layoutManager = linearLayoutManager2




//        storyList = ArrayList()
//        storyAdapter = context?.let { StoryAdapter(it, storyList as ArrayList<Story>) }
//        recyclerViewStory.adapter = storyAdapter

//        retrieveStories()
//        start of job category

        recyclerView = view.findViewById(R.id.recycler_view_home)
        recyclerView?.setHasFixedSize(true)
        recyclerView.itemAnimator = DefaultItemAnimator()
        val linearLayout = LinearLayoutManager(context)
        linearLayout.reverseLayout = true
        linearLayout.orientation = LinearLayoutManager.HORIZONTAL
        linearLayout.stackFromEnd = true
        recyclerView?.layoutManager = linearLayout
        mJob = ArrayList()
        jobAdapter = context?.let { JobAdapter(it,mJob as ArrayList<Job>,true) }
        recyclerView?.adapter = jobAdapter
        recyclerView?.visibility = View.VISIBLE

//        start of categories
        recyclerViewCategories = view.findViewById(R.id.recycler_view_category)
        recyclerViewCategories.setHasFixedSize(true)
        recyclerViewCategories.itemAnimator = DefaultItemAnimator()
        var linearLayoutcategories = LinearLayoutManager(context)
        linearLayoutcategories.reverseLayout = true
        linearLayoutcategories.orientation = LinearLayoutManager.HORIZONTAL
        linearLayoutcategories.stackFromEnd = true
        recyclerViewCategories.layoutManager = linearLayoutcategories
        mCategory = ArrayList()
        categoryAdapter = context?.let { CategoryAdapter(it,mCategory as ArrayList<Category>,true) }
        recyclerViewCategories?.adapter = categoryAdapter
        recyclerViewCategories?.visibility = View.VISIBLE

//        start of feed

        recyclerviewfeed = view.findViewById(R.id.recycler_view_feed)
        recyclerviewfeed.setHasFixedSize(true)
        recyclerviewfeed.itemAnimator = DefaultItemAnimator()
        var linearLayoutfeed = LinearLayoutManager(context)
        linearLayoutfeed.reverseLayout = true
        linearLayoutfeed.orientation = LinearLayoutManager.HORIZONTAL
        linearLayoutfeed.stackFromEnd = true
        recyclerviewfeed.layoutManager = linearLayoutfeed
        mFeed = ArrayList()
        feedAdapter = context?.let { FeedAdapter(it,mFeed as ArrayList<Feed>,true) }
        recyclerviewfeed?.adapter = feedAdapter
        recyclerviewfeed?.visibility = View.VISIBLE

        view.recommended.visibility = View.GONE
        view.cat.visibility = View.GONE

        //start of suggested search




        //start of search

        recyclerViewtafuta = view.findViewById(R.id.recycler_view_tafuta)
        recyclerViewtafuta?.setHasFixedSize(true)
        recyclerViewtafuta?.layoutManager = LinearLayoutManager(context)

        mJob = ArrayList()
        jobAdapter = context?.let { JobAdapter(it,mJob as ArrayList<Job>,true) }
        recyclerViewtafuta?.adapter = jobAdapter


//        view.wditwhat.addTextChangedListener(object: TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//
//            }
//
//
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                if (view.wditwhat.text.toString() == "")
//                {
//
//                }
//                else
//                {
//                    hao.setOnClickListener {
//                        recyclerViewtafuta?.visibility = View.VISIBLE
//                        inputHao()
//                        retrieveJobstwo()
//                        searchJob(s.toString().toLowerCase())
//                    }
//
//                }
//            }
//            override fun afterTextChanged(s: Editable?) {
//
//            }
//        })
//





        return view
    }

//    private fun inputHao() {
//        val searchtext = wditwhat.text
//
//        when {
//            TextUtils.isEmpty(searchtext) -> Toast.makeText(
//                context,
//                "Job title is required",
//                Toast.LENGTH_LONG
//            ).show()
//
//            else ->{
//                val currentUserID = FirebaseAuth.getInstance().currentUser!!.uid
//                val time = System.currentTimeMillis().toString()
//                val searchRef: DatabaseReference =
//                    FirebaseDatabase.getInstance().reference.child("Search")
//
//                val userMap = HashMap<String, Any>()
//                userMap["searchtext"] = searchtext
//                userMap["uid"] = currentUserID
//
//                searchRef.child(currentUserID).setValue(userMap)
//                    .addOnCompleteListener { task ->
//                        if (task.isSuccessful) {
//
//
//                            val intent = Intent(context, MainActivity6::class.java)
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
//                            startActivity(intent)
//
//                        } else {
//                            val message = task.exception!!.toString()
//                            Toast.makeText(context, "Error: $message", Toast.LENGTH_LONG).show()
//                            FirebaseAuth.getInstance().signOut()
//
//                        }
//                    }
//            }
//        }
//    }

    private fun searchJob(input: String) {
        val query = FirebaseDatabase.getInstance().getReference("Jobs")
            .orderByChild("jobtitle")
            .startAt(input).endAt(input + "\uf8ff")


        query.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot)
            {
                mJob?.clear()

                for (snapshot in p0.children)
                {
                    val job = snapshot.getValue(Job::class.java)
                    if (job != null)
                    {
                        mJob?.add(job)
                    }
                }

                jobAdapter?.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

//    private fun retrieveJobstwo() {
//        val jobsRef = FirebaseDatabase.getInstance().getReference("Jobs")
//        jobsRef.addValueEventListener(object : ValueEventListener{
//            override fun onDataChange(p0: DataSnapshot) {
//                if (view?.wditwhat?.text.toString() == "")
//                {
//                    mJob?.clear()
//
//                    for (snapshot in p0.children)
//                    {
//                        val job = snapshot.getValue(Job::class.java)
//                        if (job != null)
//                        {
//                            mJob?.add(job)
//                        }
//                    }
//
//                    jobAdapter?.notifyDataSetChanged()
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//        })
//    }
//
    private fun setupAPICall() {
        AndroidNetworking.initialize(context)
        AndroidNetworking.get("https://5e510330f2c0d300147c034c.mockapi.io/users")
            .build()
            .getAsObjectList(User::class.java, object : ParsedRequestListener<List<User>> {
                override fun onResponse(users: List<User>) {
                    shimmerFrameLayout.stopShimmerAnimation()
                    shimmerFrameLayout.visibility = View.GONE
//                    recycler_view_post.visibility = View.VISIBLE

                    userInfo()
                    retrieveFeed()
                    retrieveJobs()
                    retrieveCategories()
                    recommended.visibility = View.VISIBLE
                    cat.visibility = View.VISIBLE



                }

                override fun onError(anError: ANError) {
                    shimmerFrameLayout.visibility = View.GONE
                    recommended.visibility = View.GONE
                    cat.visibility = View.GONE
                    Toast.makeText(context, "Check your Internet connection..", Toast.LENGTH_LONG).show()
                }
            })
    }

//    private fun refreshApp() {
//
//    }


//    private fun retrieveStories() {
//        val storyRef = FirebaseDatabase.getInstance().reference.child("Story")
//
//        storyRef.addValueEventListener(object: ValueEventListener{
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                val timeCurrent = System.currentTimeMillis()
//
//                (storyList as ArrayList<Story>).clear()
//
//                (storyList as ArrayList<Story>).add(Story("",0,0, "", FirebaseAuth.getInstance().currentUser!!.uid))
//
//                var countStory = 0
//                var story: Story? = null
//
//                for (snapshot in dataSnapshot.children)
//                {
//                    story = snapshot.getValue(Story::class.java)
//
//                    if (timeCurrent>story!!.getTimeStart() && timeCurrent<story!!.getTimeEnd())
//                    {
//                        countStory++
//                    }
//                }
//
//                if (countStory>0)
//                {
//                    (storyList as ArrayList<Story>).add(story!!)
//                }
//                storyAdapter!!.notifyDataSetChanged()
//            }
//
//
//            override fun onCancelled(error: DatabaseError) {
//
//            }
//        })
//    }



    private fun retrieveCategories() {
        val categoriesRef = FirebaseDatabase.getInstance().reference.child("Categories")

        categoriesRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                mCategory?.clear()

                for (snapshot in p0.children)
                {
                    var category = snapshot.getValue(Category::class.java)

                    if (category != null)
                    {
                        mCategory!!.add(category)
                    }
                    categoryAdapter!!.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun retrieveFeed() {
        val feedRef = FirebaseDatabase.getInstance().reference.child("Feed")

        feedRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                mFeed?.clear()

                for (snapshot in p0.children)
                {
                    val feed = snapshot.getValue(Feed::class.java)

                    if (feed != null)
                    {
                        mFeed!!.add(feed)
                    }
                    feedAdapter!!.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun userInfo()
    {
        val jobsRef = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseUser.uid)

        jobsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {

                if (p0.exists())
                {
                    val user = p0.getValue<User>(User::class.java)

//                    Picasso.get().load(user!!.getImage()).placeholder(R.drawable.onee).into(prof)
//

                }
            }

            override fun onCancelled(error: DatabaseError) {
                startActivity(Intent(context,SigninActivity::class.java))
            }
        })
    }

    private fun retrieveJobs() {

        val jobsRef = FirebaseDatabase.getInstance().getReference("Jobs")
        jobsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {

                mJob?.clear()

                for (snapshot in p0.children)
                {
                    val job = snapshot.getValue(Job::class.java)
                    if (job != null)
                    {
                        mJob?.add(job)
                    }
                    jobAdapter?.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }




    override fun onResume() {
        super.onResume()
        shimmerFrameLayout.startShimmerAnimation()
    }
    override fun onPause() {
        shimmerFrameLayout.stopShimmerAnimation()
        super.onPause()
    }



}