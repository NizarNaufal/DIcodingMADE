package id.nizar.githubcatalogue.ui.profile.tab.following

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import id.nizar.githubcatalogue.data.model.Follower
import id.nizar.githubcatalogue.data.model.SearchUser
import id.nizar.githubcatalogue.ui.base.BaseFragment
import id.nizar.githubcatalogue.ui.profile.tab.FollowerAdapter
import id.nizar.githubcatalogue.ui.search.MainActivity
import id.nizar.githubcatalogue.viewmodel.ViewFactoryModel
import id.nizar.githubcatalogue.widgets.NetworkUtils
import id.nizar.githubcatalogue.R
import kotlinx.android.synthetic.main.fragment_followers.*
import javax.inject.Inject

/**
 * Created By NIZAR NAUFAL 2020
 */

class FollowingFragment : BaseFragment<FollowingViewModel>() {

    @Inject
    lateinit var viewFactoryModel: ViewFactoryModel

    private lateinit var followingViewModel: FollowingViewModel


    private lateinit var followingListAdapter: FollowerAdapter
    private lateinit var layoutManager1: LinearLayoutManager
    private lateinit var followingList: ArrayList<Follower>

    private lateinit var currentUser: SearchUser

    companion object {
        private const val CURRENT_USER = "current_user"

        fun newInstance(currentUser: SearchUser): FollowingFragment {
            val args: Bundle = Bundle()
            args.putParcelable(CURRENT_USER, currentUser)
            val fragment = FollowingFragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        currentUser = arguments?.getParcelable(CURRENT_USER)!!

        followingList = ArrayList()
        observeFollowingList()

        if (NetworkUtils.isNetworkConnected(activity as MainActivity))
            followingViewModel.getFollowing(currentUser.login.toString())
    }

    override fun getViewModel(): FollowingViewModel {
        followingViewModel = ViewModelProviders.of(this@FollowingFragment, viewFactoryModel)
            .get(FollowingViewModel::class.java)
        return followingViewModel
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        followingListAdapter = FollowerAdapter(activity as MainActivity, followingList)

        layoutManager1 = LinearLayoutManager(activity as MainActivity)

        rvFollowers.apply {
            layoutManager = layoutManager1
            adapter = followingListAdapter
        }
    }

    private fun observeFollowingList() {
        followingViewModel.followingList.observe(this, Observer {
            followingList.clear()
            followingList.addAll(it!!)
            followingListAdapter.notifyDataSetChanged()
        })
    }
}