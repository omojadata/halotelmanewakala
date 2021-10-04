package com.example.halotelmanewakala.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "wakala_table")
data class Wakala (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name="wakalaid")
    val wakalaid: String,
    @ColumnInfo(name="status")
    val status: Int,
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
)

