package com.example.feature_news.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.core.response.ArticleResponse
import com.example.feature_news.R

class NewsAdapter(
    private val context: Context,
    private var originalList: List<ArticleResponse>,
    private var menuInflater: MenuInflater
) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    var onItemClick: ((ArticleResponse) -> Unit)? = null
    private var selectedPosition: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_news, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = originalList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = originalList[position]
        holder.title.text = article.title

        Glide.with(context)
            .load(article.urlToImage)
            .centerCrop()
            .into(holder.banner)

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(article)
        }

        holder.itemView.setOnLongClickListener {
            selectedPosition = position
            false
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.titleTextView)
        val banner: ImageView = view.findViewById(R.id.bannerImageView)
    }
}
