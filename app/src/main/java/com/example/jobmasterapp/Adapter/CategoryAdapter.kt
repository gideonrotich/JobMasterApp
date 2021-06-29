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
import com.example.jobmasterapp.CategoryDetailFragment
import com.example.jobmasterapp.JobdetailFragment
import com.example.jobmasterapp.Model.Category
import com.example.jobmasterapp.Model.Job
import com.example.jobmasterapp.R
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class CategoryAdapter(private var mContext: Context,
                      private  var mCategory: List<Category>,
                      private var isFragment: Boolean = false) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>()

{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.ViewHolder {
        val view  = LayoutInflater.from(mContext).inflate(R.layout.category_item_layout, parent, false)
        return CategoryAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mCategory.size
    }

    override fun onBindViewHolder(holder: CategoryAdapter.ViewHolder, position: Int) {
        val category = mCategory[position]
        holder.categorydetail.text = category.getCategorydetail()
        holder.categoryname.text = category.getCategoryname()

        holder.itemView.setOnClickListener(View.OnClickListener {
            val pref = mContext.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit()
            pref.putString("nyinyi",category.getCategoryname())
            pref.apply()

            (mContext as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, CategoryDetailFragment()).commit()
        })

    }



    class ViewHolder(@NonNull itemView : View): RecyclerView.ViewHolder(itemView)
    {
        var categoryname: TextView = itemView.findViewById(R.id.namecategory)
        var categorydetail: TextView = itemView.findViewById(R.id.detailcategory)
        var categorylogo: ImageView = itemView.findViewById(R.id.pichacategory)



    }


}