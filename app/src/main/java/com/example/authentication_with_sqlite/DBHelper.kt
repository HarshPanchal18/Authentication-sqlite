package com.example.authentication_with_sqlite

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.authentication_with_sqlite.model.User

class DBHelper(context:Context) : SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {

    companion object {
        // Database Version
        private val DATABASE_VERSION = 1

        // Database Name
        private val DATABASE_NAME = "UserManager.db"

        // User table name
        private val TABLE_USER = "user"

        // User Table Columns names
        private val COLUMN_USER_ID = "user_id"
        private val COLUMN_USER_NAME = "user_name"
        private val COLUMN_USER_EMAIL = "user_email"
        private val COLUMN_USER_PASSWORD = "user_password"
    }

    // create table sql query
    private val CREATE_USER_TABLE = ("CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")")

    // drop table sql query
    private val DROP_USER_TABLE = "DROP TABLE IF EXISTS $TABLE_USER"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_USER_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL(DROP_USER_TABLE) // drop user table if exists
        onCreate(db) // create table again
    }

    @SuppressLint("Range")
    fun getAllUser(): List<User> { // fetch all user and return the list of user records
        // array of columns to fetch
        val columns = arrayOf(COLUMN_USER_ID, COLUMN_USER_EMAIL, COLUMN_USER_NAME, COLUMN_USER_PASSWORD)

        // sorting orders
        val sortOrder = "$COLUMN_USER_NAME ASC"
        val userList = ArrayList<User>()

        val db = this.readableDatabase
        val cursor=db.query(
            TABLE_USER,
            columns, // columns to return
            null, // columns for the where clause
            null, // values for the where clause
            null, // group the rows
            null, // filter by row groups
            sortOrder // sort order
        )

        if(cursor.moveToFirst() && cursor !=null){
            do {
                val user = User(
                    id = cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID)).toInt(),
                    name = cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)),
                    email = cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)),
                    password = cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD))
                )
                userList.add(user)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return userList
    }

    fun addUser(user:User) { // create user record
        val db=this.writableDatabase

        val values=ContentValues()
        values.put(COLUMN_USER_NAME,user.name)
        values.put(COLUMN_USER_EMAIL,user.email)
        values.put(COLUMN_USER_PASSWORD,user.password)

        db.insert(TABLE_USER,null,values)
        db.close()
    }

    fun updateUser(user:User) { // update user record
        val db=this.writableDatabase

        val values=ContentValues()
        values.put(COLUMN_USER_NAME,user.name)
        values.put(COLUMN_USER_EMAIL,user.email)
        values.put(COLUMN_USER_PASSWORD,user.password)

        db.update(TABLE_USER,values,"$COLUMN_USER_ID=?", arrayOf(user.id.toString()))
        db.close()
    }

    fun deleteUser(user:User) {
        val db=this.writableDatabase
        db.delete(TABLE_USER,"$COLUMN_USER_ID=?", arrayOf(user.id.toString()))
        db.close()
    }

    fun checkUser(email:String) : Boolean {
        // to check if user exists or not
        val columns= arrayOf(COLUMN_USER_ID)
        val db=this.readableDatabase

        val selection="$COLUMN_USER_EMAIL=?"
        val selectionArgs= arrayOf(email)

        // SELECT user_id FROM user WHERE user_email = 'john@yahoo.com';
        val cursor=db.query(TABLE_USER,
        columns,
        selection,
        selectionArgs,
        null,
        null,
        null)

        val cursorCount=cursor.count
        cursor.close()
        db.close()

        if(cursorCount>0)
            return true

        return false
    }

    fun checkUser(email: String,password:String) : Boolean {
        val columns= arrayOf(COLUMN_USER_ID)
        val db=this.readableDatabase

        val selection="$COLUMN_USER_EMAIL=? and $COLUMN_USER_PASSWORD=?"
        val selectionArgs= arrayOf(email,password)

        // SELECT user_id FROM user WHERE user_email = 'john@yahoo.com';
        val cursor=db.query(TABLE_USER,
            columns,
            selection,
            selectionArgs,
            null,
            null,
            null)

        val cursorCount=cursor.count
        cursor.close()
        db.close()

        if(cursorCount>0)
            return true
        return false
    }
}
