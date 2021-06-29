package com.example.jobmasterapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.jobmasterapp.JobdetailFragment
import com.example.jobmasterapp.Model.Job
import com.example.jobmasterapp.Model.User
import com.example.jobmasterapp.R
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class UserAdapter(private var mContext: Context,
                  private  var mUser: List<User>,
                  private var isFragment: Boolean = false ): RecyclerView.Adapter<UserAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.ViewHolder {
        val view  = LayoutInflater.from(mContext).inflate(R.layout.fragment_editprofile, parent, false)
        return UserAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mUser.size
    }

    override fun onBindViewHolder(holder: UserAdapter.ViewHolder, position: Int) {
        val user = mUser[position]
        holder.fullnametextview.text = user.getFullname()
        holder.emailtextview.text = user.getEmail()
        holder.mobiletextview.text = user.getMobile()
        Picasso.get().load(user.getImage()).placeholder(R.drawable.onee).into(holder.userimage)

        holder.itemView.setOnClickListener(View.OnClickListener {
            val pref = mContext.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit()
            pref.putString("profileId",user.getUid())
            pref.apply()

            (mContext as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, JobdetailFragment()).commit()
        })
    }


    class ViewHolder(@NonNull itemView : View): RecyclerView.ViewHolder(itemView)
    {
        var fullnametextview: TextView = itemView.findViewById(R.id.headline_text)
        var emailtextview: TextView = itemView.findViewById(R.id.highest_text)
        var mobiletextview: TextView = itemView.findViewById(R.id.current_text)
        var userimage: CircleImageView = itemView.findViewById(R.id.profile_image_settings)


    }

}