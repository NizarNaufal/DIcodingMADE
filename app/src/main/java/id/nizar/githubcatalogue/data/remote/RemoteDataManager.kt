package id.nizar.githubcatalogue.data.remote

import android.content.Context
import id.nizar.githubcatalogue.BuildConfig
import id.nizar.githubcatalogue.http.IGithubService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created By NIZAR NAUFAL 2020
 */

class RemoteDataManager
@Inject constructor(context: Context) : IRemoteData {

    private lateinit var iGithubService: IGithubService

    init {
        initializeGithubService()
    }

    private fun initializeGithubService() {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
        httpClient.connectTimeout(30, TimeUnit.SECONDS)
        httpClient.readTimeout(30, TimeUnit.SECONDS)
        httpClient.writeTimeout(30, TimeUnit.SECONDS)

        iGithubService = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL_GITHUB)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient.build())
            .build().create(IGithubService::class.java)
    }

    override fun searchUsers(query: String, pageNumber: String) =  iGithubService.searchUser(query, pageNumber)

    override fun getUserProfile(userId: String) = iGithubService.getUserProfile(userId)

    override fun getFollowers(userId: String) = iGithubService.getFollowers(userId)

    override fun getFollowings(userId: String) = iGithubService.getFollowings(userId)
}