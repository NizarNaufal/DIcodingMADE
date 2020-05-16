package id.nizar.githubcatalogue.data.remote

import id.nizar.githubcatalogue.data.model.Follower
import id.nizar.githubcatalogue.data.model.SearchResponse
import id.nizar.githubcatalogue.data.model.User
import io.reactivex.Single
import retrofit2.Response

/**
 * Created By NIZAR NAUFAL 2020
 */

interface IRemoteData {
    fun searchUsers(query: String, pageNumber: String)
            : Single<Response<SearchResponse>>

    fun getUserProfile(userId: String)
            : Single<Response<User>>

    fun getFollowers(userId: String)
            : Single<Response<List<Follower>>>

    fun getFollowings(userId: String)
            : Single<Response<List<Follower>>>
}