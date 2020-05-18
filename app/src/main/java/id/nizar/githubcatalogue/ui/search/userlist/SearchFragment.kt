package id.nizar.githubcatalogue.ui.search.userlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import id.nizar.githubcatalogue.ui.base.BaseFragment
import id.nizar.githubcatalogue.viewmodel.ViewFactoryModel
import kotlinx.android.synthetic.main.fragment_user_list.*
import javax.inject.Inject
import io.reactivex.subjects.PublishSubject
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.nizar.githubcatalogue.data.model.SearchUser
import id.nizar.githubcatalogue.ui.search.MainActivity
import id.nizar.githubcatalogue.widgets.NetworkUtils
import id.nizar.githubcatalogue.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit

class SearchFragment : BaseFragment<SearchViewModel>(), SearchListAdapter.OnUserClickListener {
    private lateinit var searchViewModel: SearchViewModel
    @Inject
    lateinit var viewFactoryModel: ViewFactoryModel
    private lateinit var searchListAdapter: SearchListAdapter
    private lateinit var layoutManager1: LinearLayoutManager
    private var pageNumber = 1
    private var loading = false
    private var lastVisibleItem: Int = 0
    private var totalItemCount: Int = 0
    private val VISIBLE_THRESHOLD = 1
    private var currentQuery = ""
    private var lastQuery = ""
    private lateinit var searchUsersList: ArrayList<SearchUser>
    companion object {
        const val SEARCHED_USER = "searched_user"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchUsersList = ArrayList()
    }
    override fun getViewModel(): SearchViewModel {
        searchViewModel = ViewModelProviders.of(this@SearchFragment, viewFactoryModel)
            .get(SearchViewModel::class.java)
        return searchViewModel
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_list, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLoading()
        observeMessage()
        observeSearching()
        observeSearchedUserList()
        searchView.setOnClickListener {
            searchView.isIconified = false
        }
        searchListAdapter = SearchListAdapter(activity as MainActivity, searchUsersList)
        searchListAdapter.setOnUserClickListener(this)
        layoutManager1 = LinearLayoutManager(activity as MainActivity)
        rvUserList.apply {
            layoutManager = layoutManager1
            adapter = searchListAdapter
        }
        setUpLoadMoreListener()
    }

    private fun observeMessage() {
        searchViewModel.message.observe(viewLifecycleOwner, Observer {
            (activity as MainActivity).showInfoToast(it)
        })
    }

    private fun observeLoading() {
        searchViewModel.loading.observe(viewLifecycleOwner, Observer {
            (activity as MainActivity).setProgressVisibility(it)
        })
    }

    private fun observeSearchedUserList() {
        searchViewModel.searchedUsersList.observe(viewLifecycleOwner, Observer {
            if (currentQuery == lastQuery) {
                searchUsersList.addAll(it)
            } else {
                searchUsersList.clear()
                searchUsersList.addAll(it)
            }

            searchListAdapter.notifyDataSetChanged()
            loading = false
            lastQuery = currentQuery
        })
    }


    private fun observeSearching() {
        val disposable = fromView(searchView)
            .debounce(1200, TimeUnit.MILLISECONDS)
            .filter { v ->
                v.isNotEmpty() && v.length >= 3
            }.map { text ->
                text.toLowerCase().trim()
            }.distinctUntilChanged().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Timber.i("Entered string is: %s", it)
                currentQuery = it
                if (it.length >= 3)
                    if (NetworkUtils.isNetworkConnected(activity as MainActivity))
                        searchViewModel.searchUsers(it, pageNumber)
            }
    }
    private fun fromView(searchView: SearchView): Observable<String> {
        val subject = PublishSubject.create<String>()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                subject.onComplete()
                searchView.clearFocus()
                (activity as MainActivity).hideKeyboard()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null && newText.length >= 3) {
                    pageNumber = 1
                    subject.onNext(newText)
                } else {
                    searchUsersList.clear()
                    searchListAdapter.notifyDataSetChanged()
                }
                return false
            }
        })
        return subject
    }

    private fun setUpLoadMoreListener() {
        rvUserList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(
                recyclerView: RecyclerView,
                dx: Int, dy: Int
            ) {
                super.onScrolled(recyclerView, dx, dy)
                totalItemCount = layoutManager1.itemCount
                lastVisibleItem = layoutManager1.findLastVisibleItemPosition()
                if (!loading && totalItemCount <= lastVisibleItem + VISIBLE_THRESHOLD) {
                    pageNumber++
                    if (currentQuery.length >= 3) {
                        if (NetworkUtils.isNetworkConnected(activity as MainActivity))
                            searchViewModel.searchUsers(currentQuery, pageNumber)
                    } else {
                        pageNumber = 1
                    }
                    loading = true
                }
            }
        })
    }

    override fun onUserClick(searchUser: SearchUser) {
        val bundle = Bundle()
        bundle.putParcelable(SEARCHED_USER, searchUser)
        Navigation.findNavController(view!!).navigate(R.id.action_userListFragment_to_userProfileFragment, bundle)
    }
}