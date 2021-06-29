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
import com.example.jobmasterapp.Model.Searchitem
import com.example.jobmasterapp.NextFragment
import com.example.jobmasterapp.R

class SearchAdapter(private var mContext: Context,
                    private  var mSearch: List<Searchitem>,
                    private var isFragment: Boolean = false) : RecyclerView.Adapter<SearchAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapter.ViewHolder {
        val view  = LayoutInflater.from(mContext).inflate(R.layout.search_item_layout, parent, false)
        return SearchAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mSearch.size
    }

    override fun onBindViewHolder(holder: SearchAdapter.ViewHolder, position: Int) {
        val search = mSearch[position]
        holder.searchtextview.text = search.getSearchtext()

        holder.itemView.setOnClickListener(View.OnClickListener {
            val pref = mContext.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit()
            pref.putString("profileId",search.getSearchtext())
            pref.apply()

            (mContext as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, NextFragment()).commit()
        })
    }

    class ViewHolder(@NonNull itemView : View): RecyclerView.ViewHolder(itemView)
    {
        var searchtextview: TextView = itemView.findViewById(R.id.hallo)

    }


}