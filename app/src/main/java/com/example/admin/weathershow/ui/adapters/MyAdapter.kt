package com.example.admin.weathershow.ui.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.admin.weathershow.R
import com.example.admin.weathershow.data.entity.FollowersEntity
import com.squareup.picasso.Picasso

/**
 * 使用RecyclerView自带的适配器
 */
class MyAdapter(private val context: Context, private val data: List<FollowersEntity>) :
    RecyclerView.Adapter<MyAdapter.MyVH>() {
    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyVH {
        return MyVH(LayoutInflater.from(context).inflate(R.layout.item_show, p0, false))
    }

    override fun onBindViewHolder(p0: MyVH, p1: Int) {
        val mFollowersEntity = data[p1]

        Picasso.with(context).load(mFollowersEntity.avatar_url).placeholder(R.mipmap.ic_launcher).into(p0.ivIcon)
        p0.tvId.text = data[p1].id.toString()
        p0.tvName.text = data[p1].login

        p0.itemView.setOnClickListener {
            listener!!.setOnItemClickListener(p0.itemView, p1)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

//    class MyVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        var tvId: TextView = itemView.findViewById(R.id.tv_show_id)
//        var tvName: TextView = itemView.findViewById(R.id.tv_show_name)
//        var ivIcon: ImageView = itemView.findViewById(R.id.iv_icon)
//    }
    class MyVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvId: TextView = itemView.findViewById(R.id.tv_show_id)
        var tvName: TextView = itemView.findViewById(R.id.tv_show_name)
        var ivIcon: ImageView = itemView.findViewById(R.id.iv_icon)
    }

    interface OnItemClickListener {
        fun setOnItemClickListener(view: View, position: Int)
    }

    fun setOnClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}