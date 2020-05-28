package com.tanjiajun.androidgenericframework.data

import com.google.common.truth.Truth.assertThat
import com.tanjiajun.androidgenericframework.data.remote.user.UserRemoteDataSource
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

/**
 * Created by TanJiaJun on 2020/5/26.
 */
@RunWith(JUnit4::class)
class UserRemoteDataSourceTest {

    @get:Rule
    val mockWebServer = MockWebServer()

    private lateinit var remoteDataSource: UserRemoteDataSource

    @Before
    fun setUp() {
        remoteDataSource = UserRemoteDataSource(
                Retrofit.Builder()
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(mockWebServer.url("/").toString())
                        .build()
        )
    }

    @Test
    fun authorizations() {
        runBlocking {
            mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(userAccessTokenDataJson))
            remoteDataSource.authorizations().run {
                assertThat(id).isEqualTo(userAccessTokenData.id)
                assertThat(token).isEqualTo(userAccessTokenData.token)
                assertThat(url).isEqualTo(userAccessTokenData.url)
            }
        }
    }

    @Test
    fun fetchUserInfo() {
        runBlocking {
            mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(userInfoDataJson))
            remoteDataSource.fetchUserInfo().run {
                assertThat(avatarUrl).isEqualTo(userInfoData.avatarUrl)
                assertThat(blog).isEqualTo(userInfoData.blog)
                assertThat(collaborators).isEqualTo(userInfoData.collaborators)
                assertThat(createdAt).isEqualTo(userInfoData.createdAt)
                assertThat(diskUsage).isEqualTo(userInfoData.diskUsage)
                assertThat(email).isEqualTo(userInfoData.email)
                assertThat(eventsUrl).isEqualTo(userInfoData.eventsUrl)
                assertThat(followers).isEqualTo(userInfoData.followers)
                assertThat(followersUrl).isEqualTo(userInfoData.followersUrl)
                assertThat(following).isEqualTo(userInfoData.following)
                assertThat(followingUrl).isEqualTo(userInfoData.followingUrl)
                assertThat(gistsUrl).isEqualTo(userInfoData.gistsUrl)
                assertThat(gravatarId).isEqualTo(userInfoData.gravatarId)
                assertThat(htmlUrl).isEqualTo(userInfoData.htmlUrl)
                assertThat(id).isEqualTo(userInfoData.id)
                assertThat(location).isEqualTo(userInfoData.location)
                assertThat(login).isEqualTo(userInfoData.login)
                assertThat(name).isEqualTo(userInfoData.name)
                assertThat(nodeId).isEqualTo(userInfoData.nodeId)
                assertThat(organizationsUrl).isEqualTo(userInfoData.organizationsUrl)
                assertThat(ownedPrivateRepos).isEqualTo(userInfoData.ownedPrivateRepos)
                assertThat(privateGists).isEqualTo(userInfoData.privateGists)
                assertThat(publicGists).isEqualTo(userInfoData.publicGists)
                assertThat(publicRepos).isEqualTo(userInfoData.publicRepos)
                assertThat(receivedEventsUrl).isEqualTo(userInfoData.receivedEventsUrl)
                assertThat(reposUrl).isEqualTo(userInfoData.reposUrl)
                assertThat(siteAdmin).isEqualTo(userInfoData.siteAdmin)
                assertThat(starredUrl).isEqualTo(userInfoData.starredUrl)
                assertThat(subscriptionsUrl).isEqualTo(userInfoData.subscriptionsUrl)
                assertThat(totalPrivateRepos).isEqualTo(userInfoData.totalPrivateRepos)
                assertThat(twoFactorAuthentication).isEqualTo(userInfoData.twoFactorAuthentication)
                assertThat(type).isEqualTo(userInfoData.type)
                assertThat(updatedAt).isEqualTo(userInfoData.updatedAt)
                assertThat(url).isEqualTo(userInfoData.url)
            }
        }
    }

}