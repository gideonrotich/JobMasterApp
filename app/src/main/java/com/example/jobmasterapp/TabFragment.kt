package com.example.jobmasterapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import java.lang.IllegalStateException

class TabFragment : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val x = inflater.inflate(R.layout.tab_layout,null)

        tabLayout = x.findViewById<View>(R.id.tabs) as TabLayout
        viewPager = x.findViewById<View>(R.id.viewpager) as ViewPager

        viewPager.adapter = MyAdapter(childFragmentManager)
        tabLayout.post { tabLayout.setupWithViewPager(viewPager) }

        return x
    }

    internal inner class MyAdapter(fm : FragmentManager) :FragmentPagerAdapter(fm){
        override fun getItem(position: Int): Fragment {
            when(position){
                0 -> return SecondFragment()
                1 -> return FirstFragment()

            }
            throw IllegalStateException("position $position is invalid for this viewpager")
        }

        override fun getCount(): Int {
            return items
        }

        override fun getPageTitle(position: Int): CharSequence? {

            when(position){
                0 -> return "HOME"
                1 -> return "JOBS"

            }
            return null
        }


    }

    companion object{
        lateinit var tabLayout: TabLayout
        lateinit var viewPager: ViewPager
        var items:Int = 2
    }
}