package com.rechit.githubuserapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rechit.githubuserapp.databinding.ItemRowUserBinding

class ListFollowingAdapter(private val listfollowingData: ArrayList<Following>) : RecyclerView.Adapter<ListFollowingAdapter.ViewHolder>() {
    fun setData(item: ArrayList<Following>){
        listfollowingData.clear()
        listfollowingData.addAll(item)
        notifyDataSetChanged()
    }
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val binding = ItemRowUserBinding.bind(itemView)
        fun bind(dataFollowing: Following){
            Glide.with(itemView.context)
                .load(dataFollowing.avatar)
                .apply(RequestOptions().override(100, 100))
                .into(binding.imgAvatar)
            binding.tvItemName.text = dataFollowing.name
            binding.tvItemUsername.text = dataFollowing.username
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listfollowingData[position])
    }

    override fun getItemCount(): Int {
        return listfollowingData.size
    }
}