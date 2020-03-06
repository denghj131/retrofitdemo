package com.example.admin.weathershow.exception

/**
 * 自定义异常处理服务器code码异常
 */
class ApiException(msg: String, val code: String) : Exception(msg)