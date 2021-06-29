package com.example.jobmasterapp.Adapter

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.jobmasterapp.JobdetailFragment
import com.example.jobmasterapp.MainActivity6
import com.example.jobmasterapp.Model.Job
import com.example.jobmasterapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.StorageTask
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_jobposting.*

class JobAdapter(private var mContext: Context,
                private  var mJob: List<Job>,
                private var isFragment: Boolean = false ): RecyclerView.Adapter<JobAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobAdapter.ViewHolder {
        val view  = LayoutInflater.from(mContext).inflate(R.layout.job_item_layout, parent, false)
        return JobAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mJob.size
    }

    override fun onBindViewHolder(holder: JobAdapter.ViewHolder, position: Int) {
        val job = mJob[position]
        Picasso.get().load(job.getImage()).placeholder(R.drawable.bag).into(holder.joblogo)
        holder.jobtitletextview.text = job.getJobtitle()
        holder.jobtimetextview.text = job.getJobtype()
        holder.jobcategorytextview.text = job.getCategory()
        holder.jobsalarytextview.text = job.getSalary()
        holder.jobcompanynametextview.text = job.getCompanyname()
        holder.joblocationtextview.text = job.getJoblocation()




        holder.itemView.setOnClickListener(View.OnClickListener {
            val pref = mContext.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit()
            pref.putString("profileId",job.getTime())
            pref.putString("lawama",job.getCategory())
            pref.apply()

            (mContext as  FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout,JobdetailFragment()).commit()
        })

        holder.savejobbutton.setOnClickListener(View.OnClickListener {

            holder.savejobbutton.setImageResource(R.drawable.ic_baseline_star_24)
            Toast.makeText(mContext,"Job Saved successfully",Toast.LENGTH_LONG).show()



            val jobtitle = job.getJobtitle()
            val jobdescription = job.getJobdescription()
            val responsibilities = job.getResponsibilities()
            val skills = job.getSkills()
            val education = job.getEducation()
            val joblocation = job.getJoblocation()
            val companyname = job.getCompanyname()
            val category = job.getCategory()
            val salary = job.getSalary()
            val jobtype = job.getJobtype()
            val deadline = job.getDeadline()

            when{

                else ->
                {

                            saveinfo(jobtitle,jobdescription,responsibilities,skills,education,joblocation,jobtype,deadline,companyname,category,salary)
                        }
            }


        })
    }

    private fun saveinfo(
        jobtitle: String,
        jobdescription: String,
        responsibilities: String,
        skills: String,
        education: String,
        joblocation: String,
        jobtype: String,
        deadline: String,
        companyname: String,
        category: String,
        salary: String

    ) {

        val currentUserID = FirebaseAuth.getInstance().currentUser!!.uid
        val jobsRef: DatabaseReference = FirebaseDatabase.getInstance().reference.child("New").child(currentUserID)
        val time = System.currentTimeMillis().toString()

        val userMap = HashMap<String, Any>()
        userMap["jobtitle"] = jobtitle.toLowerCase()
        userMap["jobdescription"] = jobdescription
        userMap["responsibilities"] = responsibilities
        userMap["skills"] = skills
        userMap["education"] = education
        userMap["joblocation"] = joblocation
        userMap["uid"] = currentUserID
        userMap["jobtype"] = jobtype
        userMap["deadline"] = deadline
        userMap["companyname"] = companyname
        userMap["category"] = category
        userMap["salary"] = salary
        userMap["time"] = time



        jobsRef.child(time).setValue(userMap)




    }


    class ViewHolder(@NonNull itemView : View): RecyclerView.ViewHolder(itemView)
    {
        var joblogo: CircleImageView = itemView.findViewById(R.id.logo)
        var jobtitletextview: TextView = itemView.findViewById(R.id.text_job)
        var jobtimetextview: TextView = itemView.findViewById(R.id.time)
        var jobcategorytextview: TextView = itemView.findViewById(R.id.industry)
        var jobsalarytextview: TextView = itemView.findViewById(R.id.money)
        var jobcompanynametextview: TextView = itemView.findViewById(R.id.companyname)
        var joblocationtextview: TextView = itemView.findViewById(R.id.place)
        var savejobbutton:ImageView = itemView.findViewById(R.id.ukweli)



    }




}