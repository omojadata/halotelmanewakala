package com.example.halotelmanewakala.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [WakalaMkuu::class, Wakala::class, FloatIn::class,FloatOut::class,Balance::class],version =9 )
abstract class MoblieDatabase:RoomDatabase() {
    abstract  val MobileDAO:MobileDAO

    companion object{
        @Volatile
        private var INSTANCE : MoblieDatabase? =null
        fun getInstance(context: Context):MoblieDatabase{
            synchronized(this){
                var instance:MoblieDatabase? = INSTANCE
                if (instance== null){
                    instance= Room.databaseBuilder(
                        context.applicationContext,
                        MoblieDatabase::class.java,
                        "transaction_database"
                    ).fallbackToDestructiveMigration()
                        .build()
                }
                return instance
            }
        }
    }

}