package com.tanjiajun.androidgenericframework.data.model.order.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by TanJiaJun on 2019-09-01.
 */
@Parcelize
data class OrderData(
        var id: Long,
        var title: String,
        var orderNumber: String,
        var address: String,
        var date: String,
        var weight: Int,
        var noteInformation: String,
        var firstImageUrl: String,
        var secondImageUrl: String?,
        var thirdImageUrl: String?
) : Parcelable