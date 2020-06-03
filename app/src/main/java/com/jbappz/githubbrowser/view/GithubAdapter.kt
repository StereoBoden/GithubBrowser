package com.jbappz.githubbrowser.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jbappz.githubbrowser.model.GithubRepo

class GithubAdapter: RecyclerView.Adapter<GithubAdapter.GithubViewHolder>() {
    var itemClick: ((GithubRepo) -> Unit)? = null
    private var githubDataSet: List<GithubRepo> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        GithubViewHolder(LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false))

    override fun getItemCount(): Int = githubDataSet.size

    override fun onBindViewHolder(holder: GithubViewHolder, position: Int) {
        holder.bind(githubDataSet[position], itemClick)
    }

    fun addData(data: List<GithubRepo>) {
        githubDataSet = data
        notifyDataSetChanged()
    }

    inner class GithubViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: GithubRepo, itemClick: ((GithubRepo) -> Unit)?) {
            val textView = itemView.findViewById<TextView>(android.R.id.text1)
            textView.text = item.name
            itemView.setOnClickListener {
                itemClick?.invoke(item)
            }
        }
    }
}