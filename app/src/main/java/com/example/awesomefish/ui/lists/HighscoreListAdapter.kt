package com.example.awesomefish.ui.lists;

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.awesomefish.R
import com.example.awesomefish.data.Score
import kotlinx.android.synthetic.main.high_score_item.view.*

class HighscoreListAdapter(
    val items: MutableList<Score> = mutableListOf()
) : RecyclerView.Adapter<HighscoreListAdapter.HighScoreListViewHolder>() {

    init {
        notifyDataSetChanged()
    }

    fun addAll(list: List<Score>) {
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HighScoreListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.high_score_item, parent, false)
        return HighScoreListViewHolder(view)
    }

    fun removeItemAt(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, items.size);
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: HighScoreListViewHolder, position: Int) {
        val item = items.get(position)
        holder.bindItem(item)
    }

    class HighScoreListViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindItem(item: Score) {
            view.scoreValue.text =
                view.context.getString(R.string.score_value, "${item.scoreValue}")
        }
    }
}
