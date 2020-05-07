package com.tanjiajun.androidgenericframework.data.repository

import com.tanjiajun.androidgenericframework.data.apiclient.repository.RepositoryApiClient
import com.tanjiajun.androidgenericframework.data.model.repository.RepositoryData
import com.tanjiajun.androidgenericframework.utils.Language
import java.time.LocalDateTime

/**
 * Created by TanJiaJun on 2020-02-08.
 */
class GitHubRepository(
        private val apiClient: RepositoryApiClient
) {

    fun getDefaultLanguageNames(): List<String> =
            listOf(
                    Language.KOTLIN.languageName,
                    Language.JAVA.languageName,
                    Language.SWIFT.languageName,
                    Language.JAVA_SCRIPT.languageName,
                    Language.PYTHON.languageName,
                    Language.GO.languageName,
                    Language.CSS.languageName
            )

    fun getMoreLanguageNames(): List<String> =
            listOf(
                    Language.PHP.languageName,
                    Language.RUBY.languageName,
                    Language.C_PLUS_PLUS.languageName,
                    Language.C.languageName,
                    Language.OTHER.languageName
            )

    suspend fun getRepositories(languageName: String): List<RepositoryData> =
            apiClient.fetchRepositories(
                    languageName = languageName,
                    fromDateTime = LocalDateTime.now().minusMonths(1)
            )

}