package id.nizar.githubcatalogue.ui.search.userlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.nizar.githubcatalogue.data.AppDataConfigManager
import id.nizar.githubcatalogue.data.model.SearchResponse
import id.nizar.githubcatalogue.data.model.SearchUser
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject

/**
 * Created By NIZAR NAUFAL 2020
 */

class SearchViewModel
@Inject
constructor(var appDataManager: AppDataConfigManager) : ViewModel() {
    var loading = MutableLiveData<Boolean>()
    var message = MutableLiveData<String>()
    var searchedUsersList = MutableLiveData<List<SearchUser>>()
    private val compositeDisposable = CompositeDisposable()
    fun searchUsers(query: String, pageNumber: Int) {
        loading.value = true
        val searchDisposable = appDataManager.searchUsers(query, pageNumber.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<Response<SearchResponse>>() {
                override fun onSuccess(response: Response<SearchResponse>) {
                    loading.value = false
                    if (response.isSuccessful && response.code() == 200) {
                        if (response.body()?.userList != null){
                            searchedUsersList.value = response?.body()?.userList as ArrayList<SearchUser>
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