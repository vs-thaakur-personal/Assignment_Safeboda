package com.safeboda.android.api

import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object ApiServiceProvider {
    private const val baseUrl = "https://api.github.com"
    fun providesApiService(): ApiService {
        val builder = OkHttpClient.Builder()
        // TODO: configure ssl
        builder.hostnameVerifier { str, sslSession -> true }

        builder.connectTimeout(30, TimeUnit.SECONDS)
        builder.readTimeout(30, TimeUnit.SECONDS)
        builder.writeTimeout(30, TimeUnit.SECONDS)


        // Add headers
        builder.interceptors().add(Interceptor { chain ->
            val request = chain.request()

            val requestBuilder = request.newBuilder()
            requestBuilder.addHeader("Content-Type", "application/json")
            requestBuilder.addHeader("Accept", "application/vnd.github.v3+json")
            chain.proceed(requestBuilder.build())
        })

        // Logging
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        builder.networkInterceptors().add(loggingInterceptor)

        val moshi = Moshi.Builder().build()

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(builder.build())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        return retrofit.create(ApiService::class.java)
    }
}