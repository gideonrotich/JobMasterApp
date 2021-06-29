package com.example.jobmasterapp.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.jobmasterapp.JobdetailFragment
import com.example.jobmasterapp.JobpostingActivity
import com.example.jobmasterapp.Model.Category
import com.example.jobmasterapp.Model.Feed
import com.example.jobmasterapp.R
import com.squareup.picasso.Picasso

class FeedAdapter(private var mContext: Context,
                  private  var mFeed: List<Feed>,
                  private var isFragment: Boolean = false) : RecyclerView.Adapter<FeedAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedAdapter.ViewHolder {
        val view  = LayoutInflater.from(mContext).inflate(R.layout.feed_item_layout, parent, false)
        return FeedAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mFeed.size
    }

    override fun onBindViewHolder(holder: FeedAdapter.ViewHolder, position: Int) {
        val feed = mFeed[position]
        holder.feedtext.text = feed.getFeedtext()
        holder.feeddetail.text = feed.getFeeddetail()
        holder.feedlink.text = feed.getFeedlink()

        holder.itemView.setOnClickListener(View.OnClickListener {
            if (feed.getFeedlink() == "Post job")
            {
                (mContext as  FragmentActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_layout,JobdetailFragment()).commit()

            }

        })

    }

    class ViewHolder(@NonNull itemView : View): RecyclerView.ViewHolder(itemView)
    {
        var feedtext: TextView = itemView.findViewById(R.id.textfeed)
        var feeddetail: TextView = itemView.findViewById(R.id.detailfeed)
        var feedlink: TextView = itemView.findViewById(R.id.linkfeed)




    }

}