package com.example.halotelmanewakala.RecyclerView

import android.os.Build
import android.view.*
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.halotelmanewakala.R
import com.example.halotelmanewakala.databinding.WakalaitemlistBinding
import com.example.halotelmanewakala.db.Wakala


class RecyclerViewWakala(private val clickListener: (Wakala)->Unit): RecyclerView.Adapter<MyWakalaViewHolder>()
{

     val wakalaList= ArrayList<Wakala>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyWakalaViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding: WakalaitemlistBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.wakalaitemlist,parent,false)
        return MyWakalaViewHolder(binding)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MyWakalaViewHolder, position: Int) {

        holder.bind(wakalaList[position],clickListener)

    }
    fun setList(wakala: List<Wakala>){
        wakalaList.clear()
        wakalaList.addAll(wakala)
//        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return wakalaList.size
    }
    
}


class MyWakalaViewHolder(val binding: WakalaitemlistBinding):RecyclerView.ViewHolder(binding.root){

    fun bind(wakala: Wakala,clickListener: (Wakala)->Unit){

        binding.airtelname.text="AIRTEL-> "+wakala.airtelname+" - "+wakala.airtelmoney
        binding.tigoname.text="TIGO-> "+wakala.tigoname+" - "+wakala.tigopesa
        binding.vodaname.text="VODA-> "+wakala.vodaname+" - "+wakala.mpesa
        binding.haloname.text="HALO-> "+wakala.haloname+" - "+wakala.halopesa
        binding.contactname.text= "CONTACT-> "+wakala.contact
        binding.listItemLayout.setOnClickListener {
            clickListener(wakala)
        }
    }
}
