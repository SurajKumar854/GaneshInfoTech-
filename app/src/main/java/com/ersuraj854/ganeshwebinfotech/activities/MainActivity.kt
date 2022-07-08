package com.ersuraj854.ganeshwebinfotech.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.ersuraj854.ganeshwebinfotech.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    lateinit var bottomNav: BottomNavigationView

    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        initialize()

    }


    fun initialize() {
        supportActionBar?.hide()

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        bottomNav = findViewById(R.id.bottomNav) as BottomNavigationView
        loadFragment(FeedFragment())


        bottomNav.setOnNavigationItemReselectedListener {
            when (it.itemId) {
                R.id.feed -> {
                    loadFragment(FeedFragment())
                    return@setOnNavigationItemReselectedListener
                }
                R.id.company -> {
                    loadFragment(CompaniesFragment())
                    return@setOnNavigationItemReselectedListener
                }
                R.id.jobs -> {
                    loadFragment(JobsFragment())
                    return@setOnNavigationItemReselectedListener
                }

                R.id.projects -> {
                    loadFragment(ProjectsFragment())
                    return@setOnNavigationItemReselectedListener
                }

                R.id.forums -> {
                    loadFragment(ForumFragment())
                    return@setOnNavigationItemReselectedListener
                }
            }
        }


    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


}