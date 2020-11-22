package com.example.tsisocialapp.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.tsisocialapp.MainActivity
import com.example.tsisocialapp.R
import com.example.tsisocialapp.views.fragments.HomePageFragment
import com.example.tsisocialapp.views.fragments.PostFragment
import com.example.tsisocialapp.views.fragments.ProfileFragment
import com.google.firebase.auth.FirebaseAuth
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.logout, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.logout){
            FirebaseAuth.getInstance().signOut()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showFragment(frag: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, frag).commit()
    }
}