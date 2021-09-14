package com.example.vodamanewakala.Fragments

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vodamanewakala.RecyclerView.RecyclerViewBalance
import com.example.vodamanewakala.databinding.FragmentBalanceBinding
import com.example.vodamanewakala.db.MobileRepository
import com.example.vodamanewakala.db.MoblieDatabase
import com.example.vodamanewakala.viewmodel.BalanceViewModel
import com.example.vodamanewakala.viewmodel.BalanceViewModelFactory

class Balance: Fragment() {
      private lateinit var binding: FragmentBalanceBinding
      private  lateinit var balanceViewModel: BalanceViewModel
    private lateinit var adapter:RecyclerViewBalance
    //    private lateinit var wakalaMkuuViewModel: WakalaMkuuViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentBalanceBinding.inflate(inflater,container,false)
        val dao = context?.let { MoblieDatabase.getInstance(it).MobileDAO }
        val repository = dao?.let { MobileRepository(it) }
        val factory = repository?.let { BalanceViewModelFactory(it) }

        balanceViewModel= factory?.let { ViewModelProvider(this, it).get(BalanceViewModel::class.java) }!!
        context?.registerReceiver(balanceReceiver, IntentFilter("balanceReceiver"));
        binding.balanceRecyclerView.layoutManager=LinearLayoutManager(activity)
        binding.myBalanceViewModel=balanceViewModel
        binding.lifecycleOwner=this

        initRecyclerView()
        return  binding.root
    }

    private fun initRecyclerView(){
        adapter= RecyclerViewBalance()
        binding.balanceRecyclerView.adapter=adapter
        displayTransactionList()
    }

    private fun displayTransactionList(){
        balanceViewModel.balance().observe(this, Observer {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }

    private val balanceReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        @RequiresApi(Build.VERSION_CODES.N)
        override fun onReceive(context: Context, intent: Intent) {
                return displayTransactionList()
            }
    }

    override fun onDestroyView() {
        Log.d("xyxv", "LifecycleFragment: onDestroyView() called")
        super.onDestroyView()
        context?.unregisterReceiver(balanceReceiver);
    }
}