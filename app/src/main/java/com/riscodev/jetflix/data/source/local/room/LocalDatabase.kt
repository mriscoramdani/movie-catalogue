package com.riscodev.jetflix.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.riscodev.jetflix.data.source.local.entity.FavoriteEntity
import com.riscodev.jetflix.data.source.local.entity.MovieEntity
import com.riscodev.jetflix.data.source.local.entity.ShowEntity

@Database(
    entities = [MovieEntity::class, ShowEntity::class, FavoriteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun localDao(): LocalDao

    companion object {
        @Volatile
        private var INSTANCE: LocalDatabase? = null
        private var DB_NAME: String = "App.db"

        fun getInstance(context: Context): LocalDatabase =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    LocalDatabase::class.java,
                    DB_NAME
                ).build().apply {
                    INSTANCE = this
                }
            }
    }
}