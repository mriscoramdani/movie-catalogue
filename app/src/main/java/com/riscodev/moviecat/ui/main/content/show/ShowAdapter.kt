package com.riscodev.moviecat.ui.main.content.show

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.riscodev.moviecat.R
import com.riscodev.moviecat.data.source.local.entity.ShowEntity
import com.riscodev.moviecat.databinding.ItemContentBinding
import com.riscodev.moviecat.ui.detail.DetailActivity

class ShowAdapter :
    PagedListAdapter<ShowEntity, ShowAdapter.ShowViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ShowEntity>() {
            override fun areItemsTheSame(oldItem: ShowEntity, newItem: ShowEntity): Boolean {
                return oldItem.showId == newItem.showId
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: ShowEntity, newItem: ShowEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowViewHolder {
        val itemsAcademyBinding =
            ItemContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShowViewHolder(itemsAcademyBinding)
    }

    override fun onBindViewHolder(holder: ShowViewHolder, position: Int) {
        val show = getItem(position)
        if (show != null) {
            holder.bind(show)
        }
    }

    class ShowViewHolder(private val binding: ItemContentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(show: ShowEntity) {
            with(binding) {
                this.tvTitle.text = show.originalName
                this.tvScore.text = show.voteAverage.toString()
                this.tvDescription.text = show.overview
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_CONTENT_ID, show.showId)
                    intent.putExtra(DetailActivity.EXTRA_CONTENT_CATEGORY, DetailActivity.TV_SHOW)
                    itemView.context.startActivity(intent)
                }
                Glide.with(itemView.context)
                    .load(show.posterPath)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.bg_reload_dark_blue)
                            .error(R.drawable.bg_broken_image_dark_blue))
                    .into(this.ivPoster)
            }
        }
    }
}