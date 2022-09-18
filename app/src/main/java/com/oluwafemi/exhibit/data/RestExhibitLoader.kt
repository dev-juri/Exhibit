package com.oluwafemi.exhibit.data

import com.oluwafemi.exhibit.data.network.Service
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RestExhibitLoader @Inject constructor(
    private val service: Service,
    private val dispatcher: CoroutineDispatcher
) : ExhibitLoader {

    override suspend fun getExhibitList(): Flow<List<Exhibit>> {
        return flow {
            val response = service.getExhibitList()

            if (response.isSuccessful) emit(response.body()!!)
            else emit(emptyList())

        }.flowOn(dispatcher)
    }
}