package com.alaa.checkindemo.feature_admin.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alaa.checkindemo.databinding.ItemUsersBinding
import com.alaa.checkindemo.feature_auth.domain.model.User

class UsersAdapter(
    private val users: List<User>,
    private val onClick: (String) -> Unit
) :
    RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemUsersBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = users[position]

        holder.tvName.text = item.name
        holder.itemView.setOnClickListener {
            onClick(item.id)
        }
    }

    override fun getItemCount(): Int =
        users.size

    class ViewHolder(binding: ItemUsersBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvName = binding.name
    }
}