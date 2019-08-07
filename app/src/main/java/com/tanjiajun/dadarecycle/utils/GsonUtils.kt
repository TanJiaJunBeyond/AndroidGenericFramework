package com.tanjiajun.dadarecycle.utils

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import java.io.Reader

/**
 * Created by TanJiaJun on 2019/5/3.
 */
inline fun <reified T : Any> gsonFromJson(string: String): T = Gson().fromJson(string, T::class.java)

inline fun <reified T : Any> gsonFromJsonByType(string: String): T = Gson().fromJson(string, object : TypeToken<T>() {}.type)

inline fun <reified T : Any> gsonFromJson(reader: Reader): T = Gson().fromJson(reader, T::class.java)

inline fun <reified T : Any> gsonFromJson(jsonElement: JsonElement): T = Gson().fromJson(jsonElement, T::class.java)