package com.example.tsisocialapp.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.tsisocialapp.R
import com.example.tsisocialapp.views.fragments.HomePageFragment
import com.example.tsisocialapp.views.fragments.PostFragment
import com.example.tsisocialapp.views.fragments.ProfileFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        showFragment(HomePageFragment.newInstance())

        bottomNav.setOnNavigationItemSelectedListener  {
            when(it.itemId){
                R.id.home -> {
                    showFragment(HomePageFragment.newInstance())
                }
                R.id.post -> {
                    showFragment(PostFragment.newInstance())
                }
                R.id.profile -> {
                    showFragment(ProfileFragment.newInstance())
                }
            }

            true
        }
    }

    private fun showFragment(frag: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, frag).commit()
    }
}