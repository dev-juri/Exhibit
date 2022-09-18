package com.oluwafemi.exhibit.data

import kotlinx.coroutines.flow.Flow


interface ExhibitLoader {
    suspend fun getExhibitList(): Flow<List<Exhibit>>
}