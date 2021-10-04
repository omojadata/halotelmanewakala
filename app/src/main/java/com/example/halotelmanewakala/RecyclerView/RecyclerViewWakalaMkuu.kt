package com.example.halotelmanewakala.RecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.halotelmanewakala.R
import com.example.halotelmanewakala.databinding.WakalamkuuitemlistBinding
import com.example.halotelmanewakala.db.WakalaMkuu

class RecyclerViewWakalaMkuu(private val wakalaMkuuList:List<WakalaMkuu>
,private val clickListener: (WakalaMkuu)->Unit):RecyclerView.Adapter<MyWakalaMkuuViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyWakalaMkuuViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding:WakalamkuuitemlistBinding=
                DataBindingUtil.inflate(layoutInflater, R.layout.wakalamkuuitemlist,parent,false)
        return MyWakalaMkuuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyWakalaMkuuViewHolder, position: Int) {
        holder.bind(wakalaMkuuList[position],clickListener)
    }

    override fun getItemCount(): Int {
        return wakalaMkuuList.size
    }

}

class MyWakalaMkuuViewHolder(val binding: WakalamkuuitemlistBinding):RecyclerView.ViewHolder(binding.root){

    fun bind(wakalaMkuu: WakalaMkuu,clickListener: (WakalaMkuu)->Unit){
        binding.halotel.text=wakalaMkuu.halopesa+"\n"+wakalaMkuu.haloname+"\n"+wakalaMkuu.halophone
        binding.vodacom.text=wakalaMkuu.mpesa+"\n"+wakalaMkuu.vodaname+"\n"+wakalaMkuu.vodaphone
        binding.tigo.text=wakalaMkuu.tigopesa+"\n"+wakalaMkuu.tigoname+"\n"+wakalaMkuu.tigophone
        binding.ttcl.text=wakalaMkuu.tpesa+"\n"+wakalaMkuu.ttclname+"\n"+wakalaMkuu.ttclphone
        binding.airtel.text=wakalaMkuu.airtelmoney+"\n"+wakalaMkuu.airtelname+"\n"+wakalaMkuu.airtelphone
        binding.listItemLayout.setOnClickListener {
            clickListener(wakalaMkuu)
        }
    }
}