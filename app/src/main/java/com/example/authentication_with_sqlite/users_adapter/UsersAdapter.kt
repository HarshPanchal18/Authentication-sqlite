package com.example.authentication_with_sqlite.users_adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.authentication_with_sqlite.R
import com.example.authentication_with_sqlite.model.User

class UsersAdapter(private val listUsers:List<User>) : RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    inner class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvName:TextView
        var tvEmail:TextView
        var tvPass:TextView

        init {
            tvName=view.findViewById(R.id.textViewName) as TextView
            tvEmail=view.findViewById(R.id.textViewEmail) as TextView
            tvPass=view.findViewById(R.id.textViewPassword) as TextView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.item_user_recycler,parent,false)
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.tvName.text=listUsers[position].name
        holder.tvEmail.text=listUsers[position].email
        holder.tvPass.text=listUsers[position].password
    }

    override fun getItemCount(): Int {
        return listUsers.size
    }
}
