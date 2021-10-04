package com.example.halotelmanewakala.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wakalamkuu_table")
data class WakalaMkuu (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name="wakalamkuuid")
    val wakalamkuuid: String,
    @ColumnInfo(name="tigopesa")
    val tigopesa: String,
    @ColumnInfo(name="mpesa")
    val mpesa: String,
    @ColumnInfo(name="halopesa")
    val halopesa: String,
    @ColumnInfo(name="tpesa")
    val tpesa: String,
    @ColumnInfo(name = "airtelmoney")
    val airtelmoney: String,
    @ColumnInfo(name="tigophone")
    val tigophone: String,
    @ColumnInfo(name="vodaphone")
    val vodaphone: String,
    @ColumnInfo(name="halophone")
    val halophone: String,
    @ColumnInfo(name="ttclphone")
    val ttclphone: String,
    @ColumnInfo(name = "airtelphone")
    val airtelphone: String,
    @ColumnInfo(name="tigoname")
    val tigoname: String,
    @ColumnInfo(name="vodaname")
    val vodaname: String,
    @ColumnInfo(name="haloname")
    val haloname: String,
    @ColumnInfo(name="ttclname")
    val ttclname: String,
    @ColumnInfo(name = "airtelname")
    val airtelname: String,
    @ColumnInfo(name="maxamount")
    val maxamount: String,
    @ColumnInfo(name = "contact")
    val contact: String,
    @ColumnInfo(name="status")
    val status: Int,

)