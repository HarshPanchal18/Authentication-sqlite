package com.example.authentication_with_sqlite

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.authentication_with_sqlite.databinding.ActivityUsersListBinding
import com.example.authentication_with_sqlite.model.User
import com.example.authentication_with_sqlite.users_adapter.UsersAdapter

class UsersList : AppCompatActivity() {

    private lateinit var listUsers: MutableList<User>
    private lateinit var usersRecyclerAdapter: UsersAdapter
    private lateinit var databaseHelper: DBHelper
    private lateinit var binding: ActivityUsersListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityUsersListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listUsers=ArrayList()
        usersRecyclerAdapter= UsersAdapter(listUsers)

        supportActionBar?.hide()

        val layoutManager= LinearLayoutManager(applicationContext)
        binding.recyclerViewUsers.layoutManager=layoutManager
        binding.recyclerViewUsers.apply {
            itemAnimator=DefaultItemAnimator()
            hasFixedSize()
            adapter=usersRecyclerAdapter
        }
        databaseHelper= DBHelper(this)

        val mailFromIntent=intent.getStringExtra("EMAIL")
        binding.textViewName.text=mailFromIntent

        val dataFromSqlite=GetDataFromSQLite()
        dataFromSqlite.execute()

        binding.logoutbtn.setOnClickListener { finish() }
    }

    inner class GetDataFromSQLite : AsyncTask<Void, Void, List<User>>() {

        override fun doInBackground(vararg p0: Void?): List<User> {
            return databaseHelper.getAllUser()
        }

        override fun onPostExecute(result: List<User>?) {
            super.onPostExecute(result)
            listUsers.clear()
            listUsers.addAll(result!!)
        }

    }
}
