package com.example.admin.weathershow.ui.adapters

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.admin.weathershow.R
import com.example.admin.weathershow.data.entity.FollowersEntity
import com.squareup.picasso.Picasso

/**
 * 使用BaseRecyclerViewAdapterHelper万能适配器
 */
class MyAdapter1(data: MutableList<FollowersEntity>?) :
    BaseQuickAdapter<FollowersEntity, BaseViewHolder>(R.layout.item_show, data) {

    override fun convert(helper: BaseViewHolder?, item: FollowersEntity?) {
        if (item == null) {
            return
        }

        Picasso.with(mContext)
            .load(item.avatar_url)
            .placeholder(R.mipmap.ic_launcher)
            .into(helper!!.getView<ImageView>(R.id.iv_icon))

        helper!!.setText(R.id.tv_show_id, item.id.toString())

        helper!!.setText(R.id.tv_show_name, item.login)
    }

}