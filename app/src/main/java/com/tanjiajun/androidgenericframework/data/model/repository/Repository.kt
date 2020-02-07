package com.tanjiajun.androidgenericframework.data.model.repository

import com.google.gson.annotations.SerializedName
import com.tanjiajun.androidgenericframework.utils.Language

/**
 * Created by TanJiaJun on 2020-02-06.
 */
data class RepositoryResponseData(
        val id: Int,
        val name: String? = null,
        val description: String? = null,
        val language: String? = null,
        @SerializedName("stargazers_count") val stargazersCount: Int? = null,
        @SerializedName("forks_count") val forksCount: Int? = null
)

data class RepositoryData(
        val id: Int,
        val name: String,
        val description: String,
        val language: Language,
        val starCount: Int,
        val forkCount: Int
)

object RepositoryMapper {

    fun toRepositoryData(data: RepositoryResponseData): RepositoryData =
            RepositoryData(
                    id = data.id,
                    name = data.name ?: "",
                    description = data.description ?: "",
                    language = Language.of(data.language ?: ""),
                    starCount = data.stargazersCount ?: 0,
                    forkCount = data.forksCount ?: 0
            )

}