package id.nizar.githubcatalogue.data

import android.content.Context
import id.nizar.githubcatalogue.data.remote.RemoteDataManager
import javax.inject.Inject

/**
 * Created By NIZAR NAUFAL 2020
 */

class AppDataConfigManager
@Inject constructor(
    private var context: Context,
    private var remoteDataManager: RemoteDataManager
) : IAppDataConfig {
    override fun getUserProfile(userId: String) = remoteDataManager.getUserProfile(userId)

    override fun searchUsers(query: String, pageNumber: String) =
        remoteDataManager.searchUsers(query, pageNumber)

    override fun getFollowers(userId: String) = remoteDataManager.getFollowers(userId)

    override fun getFollowings(userId: String) = remoteDataManager.getFollowings(userId)
}