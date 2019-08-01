package com.tanjiajun.dadarecycle.user.data.model.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by TanJiaJun on 2019-07-31.
 */
@Parcelize
data class UserInfoData(
    var code: String,
    var data: Data
) : Parcelable {

    @Parcelize
    data class Data(
        var avatar: String,
        var birthday: String,
        var introduce: String,
        var nick: String,
        var is_business: Boolean,
        var qr_code: String,
        var token: String,
        var user_id: String,
        var username: String,
        var active_timestamp: Long,
        var certificated: Int
    ) : Parcelable

}