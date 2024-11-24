package com.example.feature_news.adapters

import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.core.response.ArticleResponse
import com.example.feature_news.R
import com.example.feature_news.databinding.ItemNewsBinding

class NewsPagingAdapter(
    private var menuInflater: MenuInflater
) : PagingDataAdapter<ArticleResponse, NewsPagingAdapter.NewsViewHolder>(DIFF_CALLBACK), View.OnCreateContextMenuListener {
    private var selectedPosition: Int = -1
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ArticleResponse>() {
            override fun areItemsTheSame(oldItem: ArticleResponse, newItem: ArticleResponse): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: ArticleResponse, newItem: ArticleResponse): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class NewsViewHolder(private val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: ArticleResponse) {
            binding.titleTextView.text = article.title
            Glide.with(binding.bannerImageView.context)
                .load(article.urlToImage)
                .into(binding.bannerImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = getItem(position)
        article?.let { holder.bind(it) }
        holder.itemView.setOnLongClickListener {
            selectedPosition = position
            false
        }
        holder.itemView.setOnCreateContextMenuListener(this)
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        menuInflater.inflate(R.menu.menu, menu)
    }

    fun getSelectedArticle(): ArticleResponse? {
        return if (selectedPosition != -1) getItem(selectedPosition) else null
    }

}
