package com.example.tsisocialapp.views.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tsisocialapp.R
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

        val posts = layoutInflater.inflate(R.layout.options_card, linearContainer, false)
        posts.txtBtn.text = "Meus Posts"
        linearContainer.addView(posts)

        val favoritos = layoutInflater.inflate(R.layout.options_card, linearContainer, false)
        favoritos.txtBtn.text = "Meus Favoritos"
        linearContainer.addView(favoritos)

        posts.setOnClickListener {
           Log.e("Click posts", "Posts")
        }

        favoritos.setOnClickListener {
            Log.e("Click favoritos", "Favoritos")
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}