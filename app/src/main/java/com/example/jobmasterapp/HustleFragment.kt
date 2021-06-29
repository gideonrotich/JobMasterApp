package com.example.jobmasterapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jobmasterapp.Adapter.PostAdapter
import com.example.jobmasterapp.Model.Post
import com.example.jobmasterapp.Model.User
import com.example.jobmasterapp.Model.Verified
import com.example.jobmasterapp.NewMessageActivity.Companion.USER_KEY
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_hustle.view.*
import kotlinx.android.synthetic.main.posts_layout.*
import kotlinx.android.synthetic.main.posts_layout.view.*

class HustleFragment : Fragment() {
    private var postAdapter: PostAdapter? = null
    private var postList:MutableList<Post>? = null
    private var verifiedlist:MutableList<Verified>? = null
    private lateinit var firebaseUser: FirebaseUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_hustle, container, false)

        firebaseUser = FirebaseAuth.getInstance().currentUser!!


        var recyclerView: RecyclerView? = null
//
        recyclerView = view.findViewById(R.id.recycler_view_post)
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.reverseLayout = true
        linearLayoutManager.stackFromEnd = true
        recyclerView.layoutManager = linearLayoutManager

        postList = ArrayList()
        verifiedlist = ArrayList()
        postAdapter = context?.let { PostAdapter(it, postList as ArrayList<Post>,verifiedlist as ArrayList<Verified>) }
        recyclerView.adapter = postAdapter

        retrievePosts()

        view.posthustle.setOnClickListener {
            val intent = Intent(context,AddPostActivity::class.java)
            startActivity(intent)
        }






        return view
    }

    private fun retrievePosts() {
        val postsRef = FirebaseDatabase.getInstance().reference.child("Posts")

        postsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                postList?.clear()

                for (snapshot in p0.children)
                {
                    val post = snapshot.getValue(Post::class.java)

                    if (post != null)
                    {
                        postList!!.add(post)
                    }
                    postAdapter!!.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }


}