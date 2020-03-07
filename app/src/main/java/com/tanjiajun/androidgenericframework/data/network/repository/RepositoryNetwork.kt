package com.tanjiajun.androidgenericframework.data.network.repository

import com.tanjiajun.androidgenericframework.data.model.ListData
import com.tanjiajun.androidgenericframework.data.model.repository.RepositoryData
import com.tanjiajun.androidgenericframework.data.model.repository.RepositoryMapper
import com.tanjiajun.androidgenericframework.data.model.repository.RepositoryResponseData
import com.tanjiajun.androidgenericframework.data.network.BaseRetrofit
import com.tanjiajun.androidgenericframework.utils.dateFormatForRepository
import retrofit2.http.GET
import retrofit2.http.Query
import java.time.LocalDateTime

/**
 * Created by TanJiaJun on 2020-02-07.
 */
class RepositoryNetwork {

    private val service by lazy { RepositoryRetrofit().service }

    suspend fun fetchRepositories(languageName: String,
                                  fromDateTime: LocalDateTime): List<RepositoryData> =
            service
                    .fetchRepositories(
                            query = "language:${languageName} created:>${fromDateTime.format(dateFormatForRepository())}"
                    )
                    .items
                    ?.map { RepositoryMapper.toRepositoryData(it) }
                    ?: emptyList()

    interface RepositoryService {

        @GET("search/repositories")
        suspend fun fetchRepositories(@Query("q") query: String,
                                      @Query("sort") sort: String = "stars"): ListData<RepositoryResponseData>

    }

    inner class RepositoryRetrofit : BaseRetrofit() {

        val service: RepositoryService = retrofit.create(RepositoryService::class.java)

    }

}