package com.example.vodamanewakala.RecyclerView

import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.vodamanewakala.R
import com.example.vodamanewakala.databinding.FloatoutitemlistBinding
import com.example.vodamanewakala.db.FloatOut
import com.example.vodamanewakala.getDate

//class RecyclerViewWakalaMkuu(private val wakalaMkuuList:List<WakalaMkuu>):RecyclerView.Adapter<MyWakalaMkuuViewHolder>()

class RecyclerViewFloatOut(private val clickListener: (FloatOut)->Unit): RecyclerView.Adapter<MyFloatOutViewHolder>()
{
    val floatOutList= ArrayList<FloatOut>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyFloatOutViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding: FloatoutitemlistBinding= DataBindingUtil.inflate(layoutInflater,R.layout.floatoutitemlist,parent,false)
        return  MyFloatOutViewHolder(binding)
//        val binding: WakalaitemlistBinding =
//            DataBindingUtil.inflate(layoutInflater, R.layout.wakalaitemlist,parent,false)
//        return MyWakalaViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MyFloatOutViewHolder, position: Int) {
        holder.bind(floatOutList[position],clickListener)
    }

    override fun getItemCount(): Int {
        return floatOutList.size
    }
    fun setList(floatOut: List<FloatOut>){
        floatOutList.clear()
        floatOutList.addAll(floatOut)
//        notifyDataSetChanged()
    }
}

class MyFloatOutViewHolder(val binding: FloatoutitemlistBinding):RecyclerView.ViewHolder(binding.root){

    @RequiresApi(Build.VERSION_CODES.O)
    fun bind(floatOut: FloatOut, clickListener: (FloatOut) -> Unit){
        if (floatOut.status == 0) {
            binding.floatoutcardView.setCardBackgroundColor(Color.parseColor("#add8e6"))
            // Set text color what should be for Bus left
        } else if (floatOut.status == 1) {
            binding.floatoutcardView.setCardBackgroundColor(Color.parseColor("#00ab66"))
            // Set text color what should be for upcoming buses
        } else if (floatOut.status == 2) {
            binding.floatoutcardView.setCardBackgroundColor(Color.parseColor("#ffa500"))
            // Set text color what should be for upcoming buses
        } else if (floatOut.status == 3) {
            binding.floatoutcardView.setCardBackgroundColor(Color.parseColor("#ff0f0f"))
            // Set text color what should be for upcoming buses
        }
        binding.sectionone.text="Amount -"+floatOut.amount+"\n"+floatOut.comment+"\n"+floatOut.transid+"\n"+getDate(floatOut.createdAt)
        binding.sectiontwo.text=floatOut.wakalaname+"\n"+floatOut.wakalanumber
        binding.sectionthree.text=floatOut.network+"\n"+ getDate(floatOut.modifiedAt)
        binding.listItemLayout.setOnClickListener {
            clickListener(floatOut)
        }
    }

}
