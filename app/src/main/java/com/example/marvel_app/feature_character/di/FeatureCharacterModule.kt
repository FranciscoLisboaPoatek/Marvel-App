package com.example.marvel_app.feature_character.di

import com.example.marvel_app.BuildConfig
import com.example.marvel_app.feature_character.data.network.CharacterApi
import com.example.marvel_app.feature_character.domain.repository.CharacterRepository
import com.example.marvel_app.feature_character.domain.use_cases.CharactersListUseCase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.Retrofit
import java.math.BigInteger
import java.security.MessageDigest
import javax.inject.Singleton

private const val API_PUBLIC_KEY =BuildConfig.API_PUBLIC_KEY
private const val API_PRIVATE_KEY =BuildConfig.API_PRIVATE_KEY
@Module
@InstallIn(SingletonComponent::class)
object FeatureCharacterModule {
    @Provides
    @Singleton
    fun provideCharacterApi(): CharacterApi {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val log = HttpLoggingInterceptor()
        log.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient
            .Builder()
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val originalUrl = originalRequest.url

                val timestamp = System.currentTimeMillis()
                val apiKey = API_PUBLIC_KEY

                // Generate the hash
                val hash = generateHash("$timestamp$API_PRIVATE_KEY$API_PUBLIC_KEY")

                val newUrl = originalUrl.newBuilder()
                    .addQueryParameter("ts", timestamp.toString())
                    .addQueryParameter("apikey", apiKey)
                    .addQueryParameter("hash", hash)
                    .build()

                val newRequest = originalRequest.newBuilder().url(newUrl).build()
                chain.proceed(newRequest)
            }

            .addInterceptor(log)
            .build()

        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BuildConfig.MARVEL_BASE_URL)
            .client(client)
            .build()
            .create(CharacterApi::class.java)
    }
    private fun generateHash(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }

    @Provides
    @Singleton
    fun provideCharactersListUseCase(repository: CharacterRepository): CharactersListUseCase{
        return CharactersListUseCase(repository)
    }


}