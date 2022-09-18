package com.oluwafemi.exhibit.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeRestExhibitLoader : ExhibitLoader {

    private var shouldReturnError = false

    companion object {
        val dummyExhibitData = arrayListOf(
            Exhibit("iPhone 6s", arrayListOf("https://google.com", "https://somelink.com")),
            Exhibit("iPhone 7", arrayListOf("https://google.com", "https://somelink.com")),
            Exhibit("iPhone X", arrayListOf("https://google.com", "https://somelink.com"))
        )
    }

    fun returnError(value: Boolean) {
        shouldReturnError = value
    }

    override suspend fun getExhibitList(): Flow<List<Exhibit>> {
        return flow {
            //Return an empty list if network call is not successful.
            if (shouldReturnError) emit(emptyList())
            else emit(dummyExhibitData)
        }
    }


}