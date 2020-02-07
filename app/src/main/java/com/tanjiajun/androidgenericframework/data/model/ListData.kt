package com.tanjiajun.androidgenericframework.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by TanJiaJun on 2020-02-06.
 */
data class ListData<T>(
        @SerializedName("total_count") val totalCount: Int? = null,
        @SerializedName("incomplete_results") val incompleteResults: Boolean? = null,
        var items: List<T>? = null
)