package com.example.githubuser_submission2

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [FavoriteDataUser::class],
    version = 1
)
abstract class NoteRoomDatabase: RoomDatabase(){
    companion object{
        var INSTANCE : NoteRoomDatabase? = null

        fun getDatabase(context: Context): NoteRoomDatabase?{
            if(INSTANCE==null){
                synchronized(NoteRoomDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, NoteRoomDatabase::class.java, "note_database").build()
                }
            }
            return INSTANCE
        }
    }
    abstract fun userFavoriteDao(): NoteDao
}