    package com.example.jetpackcompose2.Api

    import android.content.Context
    import okhttp3.OkHttpClient
    import okhttp3.logging.HttpLoggingInterceptor
    import retrofit2.Retrofit
    import retrofit2.converter.gson.GsonConverterFactory
    import com.example.jetpackcompose2.Repository.AuthRepository

    object RetrofitClient {

        private const val BASE_URL = "http://10.0.2.2:8080/"  // Dùng IP của máy tính cho Android Emulator// Hoặc IP máy thật
        private var retrofit: Retrofit? = null

        fun getClient(context: Context, authRepository: AuthRepository): Retrofit {
            if (retrofit == null) {
                val logging = HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }

                val client = OkHttpClient.Builder()
                    .addInterceptor(com.example.jetpackcompose2.Api.AuthInterceptor(context)) // Thêm AuthInterceptor
                    .authenticator(
                        com.example.jetpackcompose2.Api.TokenAuthenticator(
                            context,
                            authRepository
                        )
                    ) // Thêm TokenAuthenticator
                    .addInterceptor(logging)
                    .build()

                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }

            return retrofit!!
        }
        fun resetInstance() {
            retrofit = null
        }
    }
