package id.nizar.githubcatalogue.http

import id.nizar.githubcatalogue.data.model.Follower
import id.nizar.githubcatalogue.data.model.SearchResponse
import id.nizar.githubcatalogue.data.model.User
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created By NIZAR NAUFAL 2020
 */

interface IGithubService {

    @GET("search/users")
    fun searchUser(@Query("q") name: String,
                    @Query("page")  pageNumber: String): Single<Response<SearchResponse>>

    @GET("users/{login_id}")
    fun getUserProfile(@Path("login_id") loginId: String): Single<Response<User>>

    @GET("users/{userId}/followers")
    fun getFollowers(@Path("userId") userId: String):  Single<Response<List<Follower>>>

    @GET("users/{userId}/following")
    fun getFollowings(@Path("userId") userId: String):  Single<Response<List<Follower>>>
}