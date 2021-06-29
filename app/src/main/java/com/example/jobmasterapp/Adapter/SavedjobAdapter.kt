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
import androidx.recyclerview.widget.RecyclerView
import com.example.jobmasterapp.MainActivity6
import com.example.jobmasterapp.Model.Job
import com.example.jobmasterapp.Model.Savedjob
import com.example.jobmasterapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class SavedjobAdapter(private var mContext: Context,
                      private  var mJobsaved: List<Savedjob>,
                      private var isFragment: Boolean = false ): RecyclerView.Adapter<SavedjobAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedjobAdapter.ViewHolder {
        val view  = LayoutInflater.from(mContext).inflate(R.layout.job_item_layout, parent, false)
        return SavedjobAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mJobsaved.size
    }

    override fun onBindViewHolder(holder: SavedjobAdapter.ViewHolder, position: Int) {
        val job = mJobsaved[position]
        holder.jobtitletextview.text = job.getJobtitle()
        holder.jobtimetextview.text = job.getJobtype()
        holder.jobcategorytextview.text = job.getCategory()
        holder.jobsalarytextview.text = job.getSalary()
        holder.jobcompanynametextview.text = job.getCompanyname()
        holder.joblocationtextview.text = job.getJoblocation()

        holder.savejobbutton.setImageResource(R.drawable.ic_baseline_delete_24)



        holder.savejobbutton.setOnClickListener(View.OnClickListener {
            val firebaseUser: FirebaseUser = FirebaseAuth.getInstance().currentUser!!
            val ref = FirebaseDatabase.getInstance().reference.child("New").child(firebaseUser.uid)
            ref.removeValue().addOnCompleteListener { task ->
                if (task.isSuccessful)
                {
                    Toast.makeText(mContext,"Job deleted successfully..", Toast.LENGTH_LONG).show()


                }
            }
        })
    }


    class ViewHolder(@NonNull itemView : View): RecyclerView.ViewHolder(itemView)
    {
        var jobtitletextview: TextView = itemView.findViewById(R.id.text_job)
        var jobtimetextview: TextView = itemView.findViewById(R.id.time)
        var jobcategorytextview: TextView = itemView.findViewById(R.id.industry)
        var jobsalarytextview: TextView = itemView.findViewById(R.id.money)
        var jobcompanynametextview: TextView = itemView.findViewById(R.id.companyname)
        var joblocationtextview: TextView = itemView.findViewById(R.id.place)
        var savejobbutton: ImageView = itemView.findViewById(R.id.ukweli)



    }
}

