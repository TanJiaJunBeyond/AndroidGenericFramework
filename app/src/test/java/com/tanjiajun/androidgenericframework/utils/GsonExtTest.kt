package com.tanjiajun.androidgenericframework.utils

import com.google.gson.Gson
import com.tanjiajun.androidgenericframework.data.model.user.response.UserInfoData
import com.tanjiajun.androidgenericframework.data.userAccessTokenData
import com.tanjiajun.androidgenericframework.data.userInfoData
import com.tanjiajun.androidgenericframework.data.userInfoDataJson
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Created by TanJiaJun on 2020/5/28.
 */
@RunWith(JUnit4::class)
class GsonExtTest {

    @Test
    fun fromJson_success() {
        assertEquals(userInfoData.id, Gson().fromJson<UserInfoData>(userInfoDataJson).id)
    }

    @Test
    fun fromJson_failure() {
        assertNotEquals(userAccessTokenData.id, Gson().fromJson<UserInfoData>(userInfoDataJson).id)
    }

}