package com.tanjiajun.androidgenericframework.data.model.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by TanJiaJun on 2019-07-31.
 */
@Parcelize
data class UserInfoData(
        var code: String,
        var errorMessage: String,
        var data: Data
) : Parcelable {

    @Parcelize
    data class Data(
            var headPortraitUrl: String,
            var userName: String,
            var gender: String,
            var age: Int
    ) : Parcelable

}