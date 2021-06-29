package com.example.jobmasterapp

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jobmasterapp.Adapter.*
import com.example.jobmasterapp.Model.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_basic.*
import kotlinx.android.synthetic.main.activity_main7.*
import kotlinx.android.synthetic.main.activity_main7.view.*
import kotlinx.android.synthetic.main.fragment_last.view.*
import kotlinx.android.synthetic.main.fragment_last.view.wditwhat
import kotlinx.android.synthetic.main.fragment_next.view.*
import kotlinx.android.synthetic.main.second_layout.*
import kotlinx.android.synthetic.main.second_layout.hao
import kotlinx.android.synthetic.main.second_layout.view.*

class LastFragment : Fragment() {
    private var postAdapter: PostAdapter? = null
    private var postList:MutableList<Post>? = null
    private var storyAdapter: StoryAdapter? = null
    private var storyList:MutableList<Story>? = null
    private lateinit var firebaseUser: FirebaseUser
    private var homeAdapter: HomeAdapter? = null
    private var mJob: MutableList<Job>? = null
    private var categoryAdapter: CategoryAdapter? = null
    private var searchAdapter: SearchAdapter? = null
    private var mSearchh:MutableList<Searchitem>? = null
    private var mCategory:MutableList<Category>? = null
    private var feedAdapter: FeedAdapter? = null
    private var jobAdapter: JobAdapter? = null
    private var mFeed:MutableList<Feed>? = null
    private var mUser:MutableList<User>? = null
    private lateinit var testing:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.activity_main7, container, false)

        view.optiven.setOnClickListener {
            startActivity(Intent(context,LatestMessagesActivity::class.java))
        }


        firebaseUser = FirebaseAuth.getInstance().currentUser!!

        var recyclerviewfeed: RecyclerView? = null
        var recyclerViewCategories: RecyclerView? = null
        var recyclerView: RecyclerView? = null
//        var recyclerViewtafuta: RecyclerView? = null
       var recycler_view_ser: RecyclerView? = null
        var recyclerviewuon:RecyclerView? = null

//        recyclerViewtafuta = view.findViewById(R.id.recycler_view_tafuta)
//        recyclerViewtafuta?.setHasFixedSize(true)
//        recyclerViewtafuta?.layoutManager = LinearLayoutManager(context)
//
//        mJob = ArrayList()
//        jobAdapter = context?.let { JobAdapter(it,mJob as ArrayList<Job>,true) }
//        recyclerViewtafuta?.adapter = jobAdapter

//Recommended jobs
        recyclerView = view.findViewById(R.id.recycler_view_home_t)
        recyclerView?.setHasFixedSize(true)
        recyclerView?.itemAnimator = DefaultItemAnimator()
        val linearLayout = LinearLayoutManager(context)
        linearLayout.reverseLayout = true
        linearLayout.orientation = LinearLayoutManager.HORIZONTAL
        linearLayout.stackFromEnd = true
        recyclerView?.layoutManager = linearLayout
        mJob = ArrayList()
        jobAdapter = context?.let { JobAdapter(it,mJob as ArrayList<Job>,true) }
        recyclerView?.adapter = jobAdapter
        recyclerView?.visibility = View.VISIBLE


        recyclerviewuon = view.findViewById(R.id.recycler_view_uonke)
        recyclerviewuon?.setHasFixedSize(true)
        recyclerviewuon?.itemAnimator = DefaultItemAnimator()
        val linearLayoutuon = LinearLayoutManager(context)
        linearLayoutuon.reverseLayout = true
        linearLayoutuon.orientation = LinearLayoutManager.HORIZONTAL
        linearLayoutuon.stackFromEnd = true
        recyclerviewuon?.layoutManager = linearLayoutuon
        mJob = ArrayList()
        jobAdapter = context?.let { JobAdapter(it,mJob as ArrayList<Job>,true) }
        recyclerviewuon?.adapter = jobAdapter
        recyclerviewuon?.visibility = View.VISIBLE
//end of recommended jobs


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


        recycler_view_ser = view.findViewById(R.id.recycler_view_ser)
        recycler_view_ser?.setHasFixedSize(true)
        recycler_view_ser?.itemAnimator = DefaultItemAnimator()
        val linearLayoutsearc = LinearLayoutManager(context)
        linearLayoutsearc.reverseLayout = true
        linearLayoutsearc.orientation = LinearLayoutManager.VERTICAL
        linearLayoutsearc.stackFromEnd = true
        recycler_view_ser?.layoutManager = linearLayoutsearc
        mSearchh = ArrayList()
        searchAdapter = context?.let { SearchAdapter(it,mSearchh as ArrayList<Searchitem>,true) }
        recycler_view_ser?.adapter = searchAdapter
        recycler_view_ser?.visibility = View.VISIBLE


//        retrieveJobs()
//        retrieveJobsthree()
        retrieveCategories()
        retrieveFeed()
        retrivesuggestedsearches()

        view.see_all.setOnClickListener(View.OnClickListener {

            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout,CategoryDetailFragment()).commit()
        })
        view.gotonext.setOnClickListener(View.OnClickListener {
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout,BottomFragment()).commit()
        })




//        val jobsReff = FirebaseDatabase.getInstance().getReference("Searchtext").child(firebaseUser.uid)
//        jobsReff.addChildEventListener(object : ChildEventListener{
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
//                mSearchh?.clear()
//
//                if (snapshot.exists()){
//                    val job = snapshot.getValue(Searchitem::class.java)
//                    if (job != null)
//                    {
//                        var maendeleo = job.getSearchtext()
//                        view.uon.text = maendeleo
//
//
//                        val query = FirebaseDatabase.getInstance().getReference("Jobs").child("Building and Construction")
//                            .orderByChild("jobtitle")
//                            .startAt(maendeleo).endAt(maendeleo + "\uf8ff")
//
//
//                        query.addValueEventListener(object : ValueEventListener {
//                            override fun onDataChange(p0: DataSnapshot)
//                            {
//                                mJob?.clear()
//
//                                for (snapshot in p0.children)
//                                {
//                                    val job = snapshot.getValue(Job::class.java)
//                                    if (job != null)
//                                    {
//                                        mJob?.add(job)
//                                    }
//                                }
//
//                                jobAdapter?.notifyDataSetChanged()
//                            }
//
//                            override fun onCancelled(error: DatabaseError) {
//                                TODO("Not yet implemented")
//                            }
//                        })
//                    }
//
//                }
//
//            }
//
//
//            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
//                mSearchh?.clear()
//
//                if (snapshot.exists()){
//                    val job = snapshot.getValue(Searchitem::class.java)
//                    if (job != null)
//                    {
//                        var maendeleo = job.getSearchtext()
//                        view.uon.text = maendeleo
//
//
//                        val query = FirebaseDatabase.getInstance().getReference("Jobs").child("Building and Construction")
//                            .orderByChild("jobtitle")
//                            .startAt(maendeleo).endAt(maendeleo + "\uf8ff")
//
//
//                        query.addValueEventListener(object : ValueEventListener {
//                            override fun onDataChange(p0: DataSnapshot)
//                            {
//                                mJob?.clear()
//
//                                for (snapshot in p0.children)
//                                {
//                                    val job = snapshot.getValue(Job::class.java)
//                                    if (job != null)
//                                    {
//                                        mJob?.add(job)
//                                    }
//                                }
//
//                                jobAdapter?.notifyDataSetChanged()
//                            }
//
//                            override fun onCancelled(error: DatabaseError) {
//                                TODO("Not yet implemented")
//                            }
//                        })
//                    }
//
//                }
//            }
//
//            override fun onChildRemoved(snapshot: DataSnapshot) {
//
//            }
//
//        })


        ///testing

        val userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseUser.uid)

        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {

                if (p0.exists())
                {
                    val user = p0.getValue<User>(User::class.java)


                    val testing = user!!.getMobile()

                    val jobsRef = FirebaseDatabase.getInstance().getReference("Jobs").child(testing)
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
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


        //endoftesting

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
//                        view.recommendeda.text = "Search Result"
//                        retrieveJobstwo()
//                        inputsearch()
//                        searchJob(s.toString().toLowerCase())
//                        see_all.visibility = View.GONE
//                    }
//
//
//
//                }
//            }
//            override fun afterTextChanged(s: Editable?) {
//
//            }
//        })
//
//

        return view
    }

    private fun retrivesuggestedsearches() {
        val jobsReff = FirebaseDatabase.getInstance().getReference("Searchtext").child(firebaseUser.uid)
        jobsReff.addChildEventListener(object : ChildEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                mSearchh?.clear()

                if (snapshot.exists()){
                    val job = snapshot.getValue(Searchitem::class.java)
                    if (job != null)
                    {
                        mSearchh?.add(job)
                    }
                    searchAdapter?.notifyDataSetChanged()
                }

            }


            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                mSearchh?.clear()

                if (snapshot.exists()){
                    val job = snapshot.getValue(Searchitem::class.java)
                    if (job != null)
                    {
                        mSearchh?.add(job)
                    }
                    searchAdapter?.notifyDataSetChanged()
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }

        })
    }

//    private fun inputsearch() {
//        val searchtext = wditwhat.text.toString()
//
//        when {
//            TextUtils.isEmpty(searchtext) -> Toast.makeText(
//                context,
//                "input job title first",
//                Toast.LENGTH_LONG
//            ).show()
//
//            else ->{
//                val currentUserID = FirebaseAuth.getInstance().currentUser!!.uid
//                val time = System.currentTimeMillis().toString()
//                val verifiedRef: DatabaseReference =
//                    FirebaseDatabase.getInstance().reference.child("Searchtext").child(currentUserID)
//
//                val userMap = HashMap<String, Any>()
//                userMap["searchtext"] = searchtext.toLowerCase()
//                userMap["uid"] = currentUserID
//
//                verifiedRef.child(time).setValue(userMap)
//                    .addOnCompleteListener { task ->
//                        if (task.isSuccessful) {
//                            Toast.makeText(
//                                context,
//                                "search has been posted successfully",
//                                Toast.LENGTH_LONG
//                            ).show()
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

//    private fun searchJob(input: String) {
//        val query = FirebaseDatabase.getInstance().getReference("Jobs").child("Building and Construction")
//            .orderByChild("jobtitle")
//            .startAt(input).endAt(input + "\uf8ff")
//
//
//        query.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(p0: DataSnapshot)
//            {
//                mJob?.clear()
//
//                for (snapshot in p0.children)
//                {
//                    val job = snapshot.getValue(Job::class.java)
//                    if (job != null)
//                    {
//                        mJob?.add(job)
//                    }
//                }
//
//                jobAdapter?.notifyDataSetChanged()
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//        })
//    }

    private fun retrieveJobs() {

        val jobsRef = FirebaseDatabase.getInstance().getReference("Jobs").child("Engineering")
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
    private fun retrieveJobstwo() {
        val jobsRef = FirebaseDatabase.getInstance().getReference("Jobs").child("Engineering")
        jobsRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                if (view?.wditwhat?.text.toString() == "")
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
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

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

    private fun retrieveJobsthree() {
        val jobsRef = FirebaseDatabase.getInstance().getReference("Jobs").child("Engineering")
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
                    }

                    jobAdapter?.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }


}