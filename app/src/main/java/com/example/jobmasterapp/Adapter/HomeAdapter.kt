package com.example.jobmasterapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.jobmasterapp.JobdetailFragment
import com.example.jobmasterapp.Model.Job
import com.example.jobmasterapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class HomeAdapter(private var mContext: Context,
                  private  var mJob: List<Job>,
                  private var isFragment: Boolean = false) : RecyclerView.Adapter<HomeAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.ViewHolder {
        val view  = LayoutInflater.from(mContext).inflate(R.layout.job_item_layout, parent, false)
        return HomeAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mJob.size
    }

    override fun onBindViewHolder(holder: HomeAdapter.ViewHolder, position: Int) {
        val job = mJob[position]
        holder.jobtitletextview.text = job.getJobtitle()
        holder.jobtimetextview.text = job.getJobtype()
        holder.jobcategorytextview.text = job.getCategory()
        holder.jobsalarytextview.text = job.getSalary()
        holder.jobcompanynametextview.text = job.getCompanyname()
        holder.joblocationtextview.text = job.getJoblocation()
        Picasso.get().load(job.getImage()).placeholder(R.drawable.bag).into(holder.joblogo)



        holder.itemView.setOnClickListener(View.OnClickListener {
            val pref = mContext.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit()
            pref.putString("profileId",job.getTime())
            pref.apply()

            (mContext as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, JobdetailFragment()).commit()
        })

    }


    class ViewHolder(@NonNull itemView : View): RecyclerView.ViewHolder(itemView)
    {
        var jobtitletextview: TextView = itemView.findViewById(R.id.text_job)
        var jobtimetextview: TextView = itemView.findViewById(R.id.time)
        var jobcategorytextview: TextView = itemView.findViewById(R.id.industry)
        var jobsalarytextview: TextView = itemView.findViewById(R.id.money)
        var jobcompanynametextview: TextView = itemView.findViewById(R.id.companyname)
        var joblogo: CircleImageView = itemView.findViewById(R.id.logo)
        var joblocationtextview: TextView = itemView.findViewById(R.id.place)


    }



}