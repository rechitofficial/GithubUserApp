package com.rechit.githubuserapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rechit.githubuserapp.databinding.ItemRowUserBinding

class ListUserAdapter(private val listUser: ArrayList<User>) : RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {


    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemRowUserBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_user, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name, user_name, avatar) = listUser[position]
        with(holder) {
            binding.tvItemName.text = name
            binding.tvItemUsername.text = user_name
            binding.imgAvatar.setImageResource(avatar)
            // move data with parcelable when an item is clicked
            holder.itemView.setOnClickListener {
                val moveIntent = Intent(holder.itemView.context, DetailUserActivity::class.java)
                val data = User(
                    name,
                    user_name,
                    avatar,
                    listUser[position].location,
                    listUser[position].company,
                    listUser[position].following,
                    listUser[position].repository,
                    listUser[position].follower
                )
                moveIntent.putExtra(DetailUserActivity.EXTRA_NAME, data)
                holder.itemView.context.startActivity(moveIntent)
            }
        }
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

}