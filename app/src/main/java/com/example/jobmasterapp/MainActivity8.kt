package com.example.jobmasterapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity8 : AppCompatActivity() {
    lateinit var employerhome:employerHome
    lateinit var employerjobs:employerJobs
    lateinit var employercandidates:employerCandidates
    lateinit var employernotifications: employerNotifications
    lateinit var employerprofile: employerProfile

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main8)

        val bottomNavigation : BottomNavigationView = findViewById(R.id.btn_nav_two)

        val window: Window = this@MainActivity8.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = ContextCompat.getColor(this@MainActivity8,R.color.giddy)

        employerhome = employerHome()
        supportFragmentManager
            .beginTransaction().replace(R.id.frame_layout_two, employerhome).setTransition(
                FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()

        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {

                R.id.home -> {
                    employerhome = employerHome()
                    supportFragmentManager
                        .beginTransaction().replace(R.id.frame_layout_two, employerhome).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()
                }

                R.id.notifications -> {

                    employerprofile = employerProfile()
                    supportFragmentManager
                        .beginTransaction().replace(R.id.frame_layout_two, employerprofile).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()
                }


                R.id.messages -> {
                    employercandidates = employerCandidates()
                    supportFragmentManager
                        .beginTransaction().replace(R.id.frame_layout_two, employercandidates).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()
                }

                R.id.notif -> {
                    employernotifications = employerNotifications()
                    supportFragmentManager
                        .beginTransaction().replace(R.id.frame_layout_two, employernotifications).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()

                }

                R.id.jobs -> {
                    employerjobs = employerJobs()
                    supportFragmentManager
                        .beginTransaction().replace(R.id.frame_layout_two, employerjobs).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()
                }

            }
            true
        }


    }
}