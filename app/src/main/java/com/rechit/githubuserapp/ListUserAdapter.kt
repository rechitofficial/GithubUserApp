package com.rechit.githubuserapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rechit.githubuserapp.databinding.ItemRowUserBinding

class ListUserAdapter(private val listUser: ArrayList<User>) : RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

    fun setData(items: ArrayList<User>) {
        listUser.clear()
        listUser.addAll(items)
        notifyDataSetChanged()
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemRowUserBinding.bind(itemView)
        fun bind(user: User) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(user.avatar)
                    .apply(RequestOptions().override(100, 100))
                    .into(binding.imgAvatar)

                binding.tvItemName.text = user.name
                binding.tvItemUsername.text = user.user_name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_user, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name, user_name, avatar) = listUser[position]
        with(holder) {
            bind(listUser[position])
//            binding.tvItemName.text = name
//            binding.tvItemUsername.text = user_name
//            binding.imgAvatar.setImageResource(avatar)
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
                moveIntent.putExtra(DetailUserActivity.EXTRA_DETAIl, data)
                holder.itemView.context.startActivity(moveIntent)
            }
        }
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

}