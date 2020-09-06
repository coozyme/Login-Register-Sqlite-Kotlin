package apps.programmeranak.loreg.sql

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import apps.programmeranak.loreg.modal.User
import java.util.ArrayList


public class DatabaseHelper(context: Context): SQLiteOpenHelper (context, DATABASE_NAME, null, DATABASE_VERSION){


//    //DATABASE NAME
//    val DATABASE_NAME = "sqlite_db"
//
//    //DATABASE VERSION
//    val DATABASE_VERSION = 1
//
//    //TABLE NAME
//    val TABLE_USERS = "users"
//
//    //TABLE USERS COLUMNS
//    //ID COLUMN @primaryKey
//    val KEY_ID = "id"
//
//    //COLUMN user name
//    val KEY_NAMA_LENGKAP = "namaLengkap"
//
//    //COLUMN email
//    val KEY_EMAIL = "email"
//
//    //COLUMN password
//    val KEY_PASSWORD = "password"
//
//    //SQL for creating users table
//    val SQL_TABLE_USERS = (" CREATE TABLE " + TABLE_USERS
//            + " ( "
//            + KEY_ID + " INTEGER PRIMARY KEY, "
//            + KEY_NAMA_LENGKAP + " TEXT, "
//            + KEY_EMAIL + " TEXT, "
//            + KEY_PASSWORD + " TEXT"
//            + " ) ")
//
//
//    fun SqliteHelper(context: Context) {
//        (context; DATABASE_NAME; null; DATABASE_VERSION)
//    }
//
//
//
//    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
//        //Create Table when oncreate gets called
//        sqLiteDatabase.execSQL(SQL_TABLE_USERS)
//    }
//
//    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, i1: Int) {
//        //drop table to create new one if database version updated
//        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS $TABLE_USERS")
//    }
//
//    //using this method we can add users to user table
//    fun addUser(user: User) {
//
//        //get writable database
//        val db = this.writableDatabase
//
//        //create content values to insert
//        val values = ContentValues()
//
//        //Put username in  @values
//        values.put(KEY_NAMA_LENGKAP, user.namaLengkap)
//
//        //Put email in  @values
//        values.put(KEY_EMAIL, user.email)
//
//        //Put password in  @values
//        values.put(KEY_PASSWORD, user.password)
//
//        // insert row
//        val todo_id = db.insert(TABLE_USERS, null, values)
//    }
//
//    fun Authenticate(user: User): User? {
//        val db = this.readableDatabase
//        val cursor: Cursor? = db.query(TABLE_USERS,
//            arrayOf(KEY_ID,
//                KEY_NAMA_LENGKAP,
//                KEY_EMAIL,
//                KEY_PASSWORD),  //Selecting columns want to query
//            "$KEY_EMAIL=?",
//            arrayOf(user.email),  //Where clause
//            null,
//            null,
//            null)
//        if (cursor != null && cursor.moveToFirst() && cursor.getCount() > 0) {
//            //if cursor has value then in user database there is user associated with this given email
//            val user1 = User(cursor.getString(0),
//                cursor.getString(1),
//                cursor.getString(2),
//                cursor.getString(3))
//
//            //Match both passwords check they are same or not
//            if (user.password.equals(user1.password, ignoreCase = true)) {
//                return user1
//            }
//        }
//
//        //if user password does not matches or there is no record with that email then return @false
//        return null
//    }
//
//    fun isEmailExists(email: String): Boolean {
//        val db = this.readableDatabase
//        val cursor: Cursor? = db.query(TABLE_USERS,
//            arrayOf(KEY_ID,
//                KEY_NAMA_LENGKAP,
//                KEY_EMAIL,
//                KEY_PASSWORD),  //Selecting columns want to query
//            "$KEY_EMAIL=?",
//            arrayOf(email),  //Where clause
//            null,
//            null,
//            null)
//        return if (cursor != null && cursor.moveToFirst() && cursor.getCount() > 0) {
//            //if cursor has value then in user database there is user associated with this given email so return true
//            true
//        } else false
//
//        //if email does not exist return false
//    }
//
//

    // create table sql query
    private val CREATE_USER_TABLE = ("CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")")
    // drop table sql query
    private val DROP_USER_TABLE = "DROP TABLE IF EXISTS $TABLE_USER"
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_USER_TABLE)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE)
        // Create tables again
        onCreate(db)
    }
    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    fun getAllUser(): List<User> {
        // array of columns to fetch
        val columns = arrayOf(COLUMN_USER_ID, COLUMN_USER_EMAIL, COLUMN_USER_NAME, COLUMN_USER_PASSWORD)
        // sorting orders
        val sortOrder = "$COLUMN_USER_NAME ASC"
        val userList =  ArrayList<User>()
        val db = this.readableDatabase
        // query the user table
        val cursor = db.query(TABLE_USER, //Table to query
            columns,            //columns to return
            null,     //columns for the WHERE clause
            null,  //The values for the WHERE clause
            null,      //group the rows
            null,       //filter by row groups
            sortOrder)         //The sort order
        if (cursor.moveToFirst()) {
            do {
                val user = User(id = cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID)).toInt(),
                    namaLengkap = cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)),
                    email = cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)),
                    password = cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)))
                userList.add(user)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return userList
    }
    /**
     * This method is to create user record
     *
     * @param user
     */
    fun addUser(user: User) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_USER_NAME, user.namaLengkap)
        values.put(COLUMN_USER_EMAIL, user.email)
        values.put(COLUMN_USER_PASSWORD, user.password)
        // Inserting Row
        db.insert(TABLE_USER, null, values)
        db.close()
    }
    /**
     * This method to update user record
     *
     * @param user
     */
    fun updateUser(user: User) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_USER_NAME, user.namaLengkap)
        values.put(COLUMN_USER_EMAIL, user.email)
        values.put(COLUMN_USER_PASSWORD, user.password)
        // updating row
        db.update(TABLE_USER, values, "$COLUMN_USER_ID = ?",
            arrayOf(user.id.toString()))
        db.close()
    }
    /**
     * This method is to delete user record
     *
     * @param user
     */
    fun deleteUser(user: User) {
        val db = this.writableDatabase
        // delete user record by id
        db.delete(TABLE_USER, "$COLUMN_USER_ID = ?",
            arrayOf(user.id.toString()))
        db.close()
    }
    /**
     * This method to check user exist or not
     *
     * @param email
     * @return true/false
     */
    fun checkUser(email: String): Boolean {
        // array of columns to fetch
        val columns = arrayOf(COLUMN_USER_ID)
        val db = this.readableDatabase
        // selection criteria
        val selection = "$COLUMN_USER_EMAIL = ?"
        // selection argument
        val selectionArgs = arrayOf(email)
        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        val cursor = db.query(TABLE_USER, //Table to query
            columns,        //columns to return
            selection,      //columns for the WHERE clause
            selectionArgs,  //The values for the WHERE clause
            null,  //group the rows
            null,   //filter by row groups
            null)  //The sort order
        val cursorCount = cursor.count
        cursor.close()
        db.close()
        if (cursorCount > 0) {
            return true
        }
        return false
    }
    /**
     * This method to check user exist or not
     *
     * @param email
     * @param password
     * @return true/false
     */
    fun checkUser(email: String, password: String): Boolean {
        // array of columns to fetch
        val columns = arrayOf(COLUMN_USER_ID)
        val db = this.readableDatabase
        // selection criteria
        val selection = "$COLUMN_USER_EMAIL = ? AND $COLUMN_USER_PASSWORD = ?"
        // selection arguments
        val selectionArgs = arrayOf(email, password)
        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        val cursor = db.query(TABLE_USER, //Table to query
            columns, //columns to return
            selection, //columns for the WHERE clause
            selectionArgs, //The values for the WHERE clause
            null,  //group the rows
            null, //filter by row groups
            null) //The sort order
        val cursorCount = cursor.count
        cursor.close()
        db.close()
        if (cursorCount > 0)
            return true
        return false
    }
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

 }