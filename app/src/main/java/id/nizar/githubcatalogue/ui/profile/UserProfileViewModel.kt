package id.nizar.githubcatalogue.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.nizar.githubcatalogue.data.AppDataConfigManager
import id.nizar.githubcatalogue.data.model.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject

/**
 * Created By NIZAR NAUFAL 2020.
 */

class UserProfileViewModel
@Inject
constructor(var appDataManager: AppDataConfigManager) : ViewModel() {
    var loading = MutableLiveData<Boolean>()
    var message = MutableLiveData<String>()

    var userProfile = MutableLiveData<User>()

    private val compositeDisposable = CompositeDisposable()

    fun getUserProfile(userId: String) {
        loading.value = true
        val searchDisposable = appDataManager.getUserProfile(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<Response<User>>() {
                override fun onSuccess(response: Response<User>) {
                    loading.value = false
                    if (response.isSuccessful && response.code() == 200) {
                        userProfile.value = response.body()
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