package id.nizar.githubcatalogue.ui.profile.tab.following

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.nizar.githubcatalogue.data.AppDataConfigManager
import id.nizar.githubcatalogue.data.model.Follower
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject

/**
 * Created By NIZAR NAUFAL 2020
 */

class FollowingViewModel
@Inject
constructor(var appDataManager: AppDataConfigManager): ViewModel(){

    var loading = MutableLiveData<Boolean>()
    var message = MutableLiveData<String>()
    var followingList = MutableLiveData<List<Follower>>()

    private val compositeDisposable = CompositeDisposable()

    fun getFollowing(userId: String) {
        loading.value = true
        val searchDisposable = appDataManager.getFollowings(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<Response<List<Follower>>>() {
                override fun onSuccess(response: Response<List<Follower>>) {
                    loading.value = false
                    if (response.isSuccessful && response.code() == 200) {
                        if (response.body() != null &&
                            (response.body() as List).isNotEmpty()){
                            followingList.value = response.body() as ArrayList<Follower>
                        }
                    } else {
                        message.value = response.message()
                    }
                }

                override fun onError(throwable: Throwable) {
                    loading.value = false
                    if (throwable.message != null)
                        message.value = throwable.message
                }
            })
        compositeDisposable.add(searchDisposable)
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}