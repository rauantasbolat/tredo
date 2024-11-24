package com.example.feature_news.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.feature_news.R

class SavedNewsDetailsFragment : Fragment() {

    var imageView: ImageView? = null
    var author: TextView? = null
    var content: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_saved_news_details, container, false)
        imageView = view.findViewById(R.id.detailsIv)
        author = view.findViewById(R.id.detailsAuthorTv)
        content = view.findViewById(R.id.detailsTv)
        arguments?.getString("urlToImage")?.let {
            Glide.with(requireContext())
            .load(it)
            .centerCrop()
            .into(imageView!!) }
        arguments?.getString("author")?.let {
            author!!.setText(getString(R.string.author_placeholder, it))
        }
        arguments?.getString("content")?.let {
            content!!.setText(it)
        }
        return view
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SavedNewsDetailsFragment().apply {
            }
    }
}