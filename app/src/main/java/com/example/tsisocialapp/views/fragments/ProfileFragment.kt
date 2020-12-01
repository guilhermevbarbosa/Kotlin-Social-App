package com.example.tsisocialapp.views.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tsisocialapp.R
import com.example.tsisocialapp.views.activities.MyPostsActivity
import com.example.tsisocialapp.views.activities.SavedPostsActivity
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.options_card.view.*

class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        return view
    }

    override fun onResume() {
        super.onResume()
        linearContainer.removeAllViews()

        val posts = layoutInflater.inflate(R.layout.options_card, linearContainer, false)
        posts.txtBtn.text = "Meus Posts"
        linearContainer.addView(posts)

        val favoritos = layoutInflater.inflate(R.layout.options_card, linearContainer, false)
        favoritos.txtBtn.text = "Meus Favoritos"
        linearContainer.addView(favoritos)

        posts.setOnClickListener {
            activity?.let{
                val intent = Intent(it, MyPostsActivity::class.java)
                it.startActivity(intent)
            }
        }

        favoritos.setOnClickListener {
            activity?.let{
                val intent = Intent(it, SavedPostsActivity::class.java)
                it.startActivity(intent)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}