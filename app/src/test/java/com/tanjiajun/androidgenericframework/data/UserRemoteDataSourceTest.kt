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
            val json = "{\n" +
                    "\"id\": 432604074,\n" +
                    "\"token\": \"\",\n" +
                    "\"url\": \"https://api.github.com/authorizations/432604074\"\n" +
                    "}"
            mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(json))
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
            val json = "{\n" +
                    "\"avatar_url\": \"https://avatars1.githubusercontent.com/u/25838119?v=4\",\n" +
                    "\"blog\": \"\",\n" +
                    "\"collaborators\": 0,\n" +
                    "\"created_at\": \"2017-02-17T06:28:25Z\",\n" +
                    "\"disk_usage\": 46380,\n" +
                    "\"email\": \"1120571286@qq.com\",\n" +
                    "\"events_url\": \"https://api.github.com/users/TanJiaJunBeyond/events{/privacy}\",\n" +
                    "\"followers\": 15,\n" +
                    "\"followers_url\": \"https://api.github.com/users/TanJiaJunBeyond/followers\",\n" +
                    "\"following\": 5,\n" +
                    "\"following_url\": \"https://api.github.com/users/TanJiaJunBeyond/following{/other_user}\",\n" +
                    "\"gists_url\": \"https://api.github.com/users/TanJiaJunBeyond/gists{/gist_id}\",\n" +
                    "\"gravatar_id\": \"\",\n" +
                    "\"html_url\": \"https://github.com/TanJiaJunBeyond\",\n" +
                    "\"id\": 25838119,\n" +
                    "\"location\": \"中国广东省广州市\",\n" +
                    "\"login\": \"TanJiaJunBeyond\",\n" +
                    "\"name\": \"TanJiaJun\",\n" +
                    "\"node_id\": \"MDQ6VXNlcjI1ODM4MTE5\",\n" +
                    "\"organizations_url\": \"https://api.github.com/users/TanJiaJunBeyond/orgs\",\n" +
                    "\"owned_private_repos\": 0,\n" +
                    "\"private_gists\": 0,\n" +
                    "\"public_gists\": 0,\n" +
                    "\"public_repos\": 11,\n" +
                    "\"received_events_url\": \"https://api.github.com/users/TanJiaJunBeyond/received_events\",\n" +
                    "\"repos_url\": \"https://api.github.com/users/TanJiaJunBeyond/repos\",\n" +
                    "\"site_admin\": \"false\",\n" +
                    "\"starred_url\": \"https://api.github.com/users/TanJiaJunBeyond/starred{/owner}{/repo}\",\n" +
                    "\"subscriptions_url\": \"https://api.github.com/users/TanJiaJunBeyond/subscriptions\",\n" +
                    "\"total_private_repos\": 0,\n" +
                    "\"two_factor_authentication\": false,\n" +
                    "\"type\": \"User\",\n" +
                    "\"updated_at\": \"2020-05-26T07:19:39Z\",\n" +
                    "\"url\": \"https://api.github.com/users/TanJiaJunBeyond\"\n" +
                    "}"
            mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(json))
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