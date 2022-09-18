package com.oluwafemi.exhibit.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.oluwafemi.exhibit.data.ExhibitLoader
import com.oluwafemi.exhibit.data.RestExhibitLoader
import com.oluwafemi.exhibit.data.network.Service
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Singleton
    @Provides
    fun provideCoroutineDispatchers(): CoroutineDispatcher = Dispatchers.IO
}

@InstallIn(SingletonComponent::class)
@Module
object RetrofitModule {

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl("https://my-json-server.typicode.com/")
        .build()

    @Singleton
    @Provides
    fun provideDataSource(service: Service, dispatcher: CoroutineDispatcher) =
        RestExhibitLoader(service, dispatcher) as ExhibitLoader

    @Provides
    fun provideNetworkService(retrofit: Retrofit): Service = retrofit.create(Service::class.java)

}