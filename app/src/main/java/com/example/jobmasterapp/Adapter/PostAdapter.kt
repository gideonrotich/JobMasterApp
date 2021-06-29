package com.example.jobmasterapp.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.replace
import androidx.recyclerview.widget.RecyclerView
import com.example.jobmasterapp.*
import com.example.jobmasterapp.Model.Post
import com.example.jobmasterapp.Model.User
import com.example.jobmasterapp.Model.Verified
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class PostAdapter(private var mContext:Context,
                  private val mPost:List<Post>,
                    private val mVerified: List<Verified>): RecyclerView.Adapter<PostAdapter.ViewHolder>()
{
    private var firebaseUser:FirebaseUser? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view = LayoutInflater.from(mContext).inflate(R.layout.posts_layout,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
       return mPost.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       firebaseUser = FirebaseAuth.getInstance().currentUser

        val post = mPost[position]

        Picasso.get().load(post.getPostimage()).into(holder.postImage)

        if (post.getDescription().equals(""))
        {
            holder.description.visibility = View.GONE
        }
        else{
            holder.description.visibility = View.VISIBLE
            holder.description.setText(post.getDescription())
        }

        publisherInfo(holder.profileImage, holder.userName,post.getPublisher(),holder.profession,holder.verification)

        isLikes(post.getPostid(), holder.likeButton)

        numberOfLikes(holder.likes, post.getPostid())

        getTotalComments(holder.comments, post.getPostid())

        holder.menu.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(
                mContext,R.style.BottomSheetDialogTheme
            )
            val bottomSheetView = LayoutInflater.from(mContext).inflate(
                R.layout.layout_bottom_sheet,null)

            
            bottomSheetView.findViewById<View>(R.id.buttonShare).setOnClickListener {
                Toast.makeText(mContext,"Post will be removed from your feed..", Toast.LENGTH_LONG).show()
                bottomSheetDialog.dismiss()
            }
            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()
        }






        holder.likeButton.setOnClickListener {
            if (holder.likeButton.tag == "Like")
            {
                FirebaseDatabase.getInstance().reference
                    .child("Likes")
                    .child(post.getPostid())
                    .child(firebaseUser!!.uid)
                    .setValue(true)
            }
            else{
                FirebaseDatabase.getInstance().reference
                    .child("Likes")
                    .child(post.getPostid())
                    .child(firebaseUser!!.uid)
                    .removeValue()
                val intent = Intent(mContext, MainActivity6::class.java)
                mContext.startActivity(intent)
            }
        }

        holder.commentButton.setOnClickListener {
            val intentComment = Intent(mContext,CommentsActivity::class.java)
            intentComment.putExtra("postId", post.getPostid())
            intentComment.putExtra("publisherId", post.getPublisher())
            mContext.startActivity(intentComment)
        }





        holder.comments.setOnClickListener {
            val intentComment = Intent(mContext,CommentsActivity::class.java)
            intentComment.putExtra("postId", post.getPostid())
            intentComment.putExtra("publisherId", post.getPublisher())
            mContext.startActivity(intentComment)
        }
    }

    private fun numberOfLikes(likes: TextView, postid: String) {
        val LikesRef = FirebaseDatabase.getInstance().reference
            .child("Likes").child(postid)

        LikesRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists())
                {
                    likes.text = p0.childrenCount.toString() + ""
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }


    private fun getTotalComments(comments: TextView, postid: String) {
        val commentsRef = FirebaseDatabase.getInstance().reference
            .child("Comments").child(postid)

        commentsRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists())
                {
                    comments.text = "View all " + p0.childrenCount.toString() + " comments"
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun isLikes(postid: String, likeButton: ImageView) {
        val firebaseUser = FirebaseAuth.getInstance().currentUser

        val LikesRef = FirebaseDatabase.getInstance().reference
            .child("Likes").child(postid)

        LikesRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.child(firebaseUser!!.uid).exists())
                {
                   likeButton.setImageResource(R.drawable.ic_thumb_up)
                    likeButton.tag = "Liked"
                }
                else
                {
                    likeButton.setImageResource(R.drawable.ic_likeone)
                    likeButton.tag = "Like"
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })


    }


    inner class ViewHolder(@NonNull itemView:View): RecyclerView.ViewHolder(itemView)
    {
        var profileImage: CircleImageView
        var postImage: ImageView
        var likeButton: ImageView
        var commentButton: ImageView
        var shareButton: ImageView
        var userName: TextView
        var likes: TextView
        var profession:TextView
        var description: TextView
        var comments: TextView
        var menu: ImageView
        var verification:ImageView






        init {
            profileImage  = itemView.findViewById(R.id.pichayako)
            postImage = itemView.findViewById(R.id.picture_post)
            likeButton = itemView.findViewById(R.id.pichalike)
            commentButton = itemView.findViewById(R.id.pichacomment)
            shareButton = itemView.findViewById(R.id.pichashare)
            userName = itemView.findViewById(R.id.fullname_post)
            likes = itemView.findViewById(R.id.likes)
            profession = itemView.findViewById(R.id.proffesion_post)
            description = itemView.findViewById(R.id.text_post)
            comments = itemView.findViewById(R.id.totalcomments)
            menu = itemView.findViewById(R.id.menusheet)
            verification = itemView.findViewById(R.id.verification)



        }

    }

    private fun publisherInfo(profileImage: CircleImageView, userName: TextView,  publisherID: String, profession:TextView,verification:ImageView) {

        val  usersRef = FirebaseDatabase.getInstance().reference.child("Users").child(publisherID)

        usersRef.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists())
                {
                    val user = p0.getValue<User>(User::class.java)

                    Picasso.get().load(user!!.getImage()).placeholder(R.drawable.onee).into(profileImage)
                    userName.text = user!!.getFullname()
                    profession.text = user!!.getProfession()





                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }


}