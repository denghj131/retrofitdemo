package com.example.admin.weathershow.ui.activities

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Toast
import com.blankj.utilcode.util.ToastUtils
import com.example.admin.weathershow.R
import com.example.admin.weathershow.data.entity.FollowersEntity
import com.example.admin.weathershow.http.RetrofitClient
import com.example.admin.weathershow.ui.adapters.MyAdapter1
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    lateinit var data: kotlin.collections.MutableList<FollowersEntity>
    lateinit var adapter: MyAdapter1
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById<RecyclerView>(R.id.rv_show)

        data = mutableListOf<FollowersEntity>()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        adapter = MyAdapter1(data)

        recyclerView.adapter = adapter

        adapter.setOnItemClickListener { adapter, view, position ->
            Toast.makeText(this@MainActivity, "点击了===" + data[position].login, Toast.LENGTH_SHORT).show()
        }

        getData1()
    }

    @SuppressLint("CheckResult")
    private fun getData1() {
        RetrofitClient.instance.api
            .getFollowers()
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                Log.e("tmd", "封装后的数据大小===" + result.size)
                runOnUiThread {
                    data.addAll(result)
                    adapter.notifyDataSetChanged()
                }
            }, { error ->
                println(error.message)
                Log.e("tmd", "封装后的错误信息===" + error.message)
                ToastUtils.showShort("网络获取数据失败，请稍后再试！")
            })
    }
}
