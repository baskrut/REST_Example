package com.andrewbas.restapiclient

import io.reactivex.Observable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface UdemyAipService {

    @GET("?price=price-free, fields[course]=title")
    fun search(@Header("Authorization")header: String, @Query("q")query: String = "Learn+Flutter+%26+Dart+to+Build+iOS+%26+Android+Apps", @Query("page") page: Int): Observable<Result>

    companion object Factory {


        fun create(): UdemyAipService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://www.udemy.com/api-2.0/courses/")
                .build()

            return retrofit.create(UdemyAipService::class.java)
        }
    }
}