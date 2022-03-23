package com.rechit.githubuserapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rechit.githubuserapp.databinding.ItemRowFollowerBinding
import com.rechit.githubuserapp.databinding.ItemRowUserBinding

class ListFollowerAdapter(private val listFollowerData: ArrayList<Follower>) : RecyclerView.Adapter<ListFollowerAdapter.ViewHolder>(){
    fun setData(items: ArrayList<Follower>){
        listFollowerData.clear()
        listFollowerData.addAll(items)
        notifyDataSetChanged()
    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val binding = ItemRowUserBinding.bind(itemView)
        fun bind(dataFollower: Follower){
            Glide.with(itemView.context)
                .load(dataFollower.avatar)
                .apply(RequestOptions().override(100, 100))
                .into(binding.imgAvatar)
            binding.tvItemName.text = dataFollower.name
            binding.tvItemUsername.text = dataFollower.username
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_user, parent , false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listFollowerData[position])
    }

    override fun getItemCount(): Int {
        return listFollowerData.size
    }
}