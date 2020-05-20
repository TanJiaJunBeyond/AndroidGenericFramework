package com.tanjiajun.androidgenericframework.utils

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import java.io.Reader

/**
 * Created by TanJiaJun on 2019/5/3.
 */
inline fun <reified T> Gson.fromJson(string: String): T =
        fromJson(string, T::class.java)

inline fun <reified T> Gson.fromJsonByType(string: String): T =
        fromJson(string, object : TypeToken<T>() {}.type)

inline fun <reified T> Gson.fromJson(reader: Reader): T =
        fromJson(reader, T::class.java)

inline fun <reified T> Gson.fromJson(jsonElement: JsonElement): T =
        fromJson(jsonElement, T::class.java)