package com.example.admin.weathershow.data.entity

class BaseResponse<T> {
    var data: T? = null

    override fun toString(): String {
        return "BaseResponse{" +
                "data=" + data +
                '}'.toString()
    }
}
