package com.example.authentication_with_sqlite

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.authentication_with_sqlite.model.User
import com.example.authentication_with_sqlite.users_adapter.UsersAdapter
import kotlinx.android.synthetic.main.activity_users_list.*

class UsersList : AppCompatActivity() {

    private lateinit var listUsers: MutableList<User>
    private lateinit var usersRecyclerAdapter: UsersAdapter
    private lateinit var databaseHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_list)
        listUsers=ArrayList()
        usersRecyclerAdapter= UsersAdapter(listUsers)

        supportActionBar?.hide()

        val layoutManager= LinearLayoutManager(applicationContext)
        recyclerViewUsers.layoutManager=layoutManager
        recyclerViewUsers.itemAnimator=DefaultItemAnimator()
        recyclerViewUsers.hasFixedSize()
        recyclerViewUsers.adapter=usersRecyclerAdapter
        databaseHelper= DBHelper(this)

        val mailFromIntent=intent.getStringExtra("EMAIL")
        textViewName.text=mailFromIntent

        val dataFromSqlite=GetDataFromSQLite()
        dataFromSqlite.execute()

        logoutbtn.setOnClickListener { finish() }
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
