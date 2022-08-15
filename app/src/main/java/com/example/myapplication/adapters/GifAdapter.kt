package com.example.myapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.api.Data

class GifAdapter(): RecyclerView.Adapter<GifAdapter.GifViewHolder>() {

    var itemList: List<Data> = emptyList()
    var listener: () -> Unit = { }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        val viewObject = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
        return GifViewHolder(viewObject)
    }

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        holder.insertImageByUrl(itemList[position].images.original.url)
        if (itemList.size-position < 10 ){
            listener.invoke()
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun setItems(list: List<Data>){
        itemList = list
        notifyDataSetChanged()
    }

    inner class GifViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var itemImage: ImageView

        init{
            itemImage = itemView.findViewById(R.id.gifImageView)
        }

        fun insertImageByUrl(url: String) {
            val circularProgressDrawable = CircularProgressDrawable(itemView.context)
            circularProgressDrawable.strokeWidth = 20f
            circularProgressDrawable.centerRadius = 100f
            circularProgressDrawable.setColorSchemeColors(ContextCompat.getColor(itemView.context ,
                R.color.teal_700
            ))
            circularProgressDrawable.start()

            Glide.with(itemView.context)
                .load(url)
                .placeholder(circularProgressDrawable)
                .into(itemImage)
        }

    }

}