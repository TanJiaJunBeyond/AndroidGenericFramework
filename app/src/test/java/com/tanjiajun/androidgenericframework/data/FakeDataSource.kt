package com.tanjiajun.androidgenericframework.data

import com.tanjiajun.androidgenericframework.data.model.repository.RepositoryData
import com.tanjiajun.androidgenericframework.data.model.user.response.UserAccessTokenData
import com.tanjiajun.androidgenericframework.data.model.user.response.UserInfoData
import com.tanjiajun.androidgenericframework.utils.Language

/**
 * Created by TanJiaJun on 2020/5/28.
 */
val userAccessTokenData = UserAccessTokenData(
        id = 432604074,
        token = "",
        url = "https://api.github.com/authorizations/432604074"
)

val userInfoData = UserInfoData(
        id = 25838119,
        login = "TanJiaJunBeyond",
        nodeId = "MDQ6VXNlcjI1ODM4MTE5",
        avatarUrl = "https://avatars1.githubusercontent.com/u/25838119?v=4",
        gravatarId = "",
        url = "https://api.github.com/users/TanJiaJunBeyond",
        htmlUrl = "https://github.com/TanJiaJunBeyond",
        followersUrl = "https://api.github.com/users/TanJiaJunBeyond/followers",
        followingUrl = "https://api.github.com/users/TanJiaJunBeyond/following{/other_user}",
        gistsUrl = "https://api.github.com/users/TanJiaJunBeyond/gists{/gist_id}",
        starredUrl = "https://api.github.com/users/TanJiaJunBeyond/starred{/owner}{/repo}",
        subscriptionsUrl = "https://api.github.com/users/TanJiaJunBeyond/subscriptions",
        organizationsUrl = "https://api.github.com/users/TanJiaJunBeyond/orgs",
        reposUrl = "https://api.github.com/users/TanJiaJunBeyond/repos",
        eventsUrl = "https://api.github.com/users/TanJiaJunBeyond/events{/privacy}",
        receivedEventsUrl = "https://api.github.com/users/TanJiaJunBeyond/received_events",
        type = "User",
        siteAdmin = false,
        name = "TanJiaJun",
        company = "",
        blog = "",
        location = "中国广东省广州市",
        email = "1120571286@qq.com",
        hireable = "",
        bio = "",
        publicRepos = 11,
        publicGists = 0,
        followers = 15,
        following = 5,
        createdAt = "2017-02-17T06:28:25Z",
        updatedAt = "2020-05-26T07:19:39Z",
        privateGists = 0,
        totalPrivateRepos = 0,
        ownedPrivateRepos = 0,
        diskUsage = 46380,
        collaborators = 0,
        twoFactorAuthentication = false
)

val repositoryData = RepositoryData(
        id = 0,
        name = "谭嘉俊",
        description = "描述",
        language = Language.KOTLIN,
        starCount = 0,
        forkCount = 0
)