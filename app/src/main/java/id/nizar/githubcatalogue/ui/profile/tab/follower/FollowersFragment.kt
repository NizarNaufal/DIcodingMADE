package id.nizar.githubcatalogue.ui.profile.tab.follower

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import id.nizar.githubcatalogue.R
import id.nizar.githubcatalogue.data.model.Follower
import id.nizar.githubcatalogue.data.model.SearchUser
import id.nizar.githubcatalogue.ui.base.BaseFragment
import id.nizar.githubcatalogue.ui.profile.tab.FollowerAdapter
import id.nizar.githubcatalogue.ui.search.MainActivity
import id.nizar.githubcatalogue.viewmodel.ViewFactoryModel
import id.nizar.githubcatalogue.widgets.NetworkUtils
import kotlinx.android.synthetic.main.fragment_followers.*
import javax.inject.Inject

/**
 * Created By NIZAR NAUFAL 2020
 */

class FollowersFragment : BaseFragment<FollowerViewModel>() {

    @Inject
    lateinit var viewFactoryModel: ViewFactoryModel

    private lateinit var followerViewModel: FollowerViewModel

    private lateinit var followerListAdapter: FollowerAdapter
    private lateinit var layoutManager1: LinearLayoutManager
    private lateinit var followersList: ArrayList<Follower>
    private lateinit var currentUser: SearchUser

    companion object {
        private const val CURRENT_USER = "current_user"

        fun newInstance(currentUser: SearchUser): FollowersFragment {
            val args: Bundle = Bundle()
            args.putParcelable(CURRENT_USER, currentUser)
            val fragment = FollowersFragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        currentUser = arguments?.getParcelable(CURRENT_USER)!!

        followersList = ArrayList()
        observeFollowersList()

        if (NetworkUtils.isNetworkConnected(activity as MainActivity))
            followerViewModel.getFollowers(currentUser.login.toString())
    }

    override fun getViewModel(): FollowerViewModel {
        followerViewModel = ViewModelProviders.of(this@FollowersFragment, viewFactoryModel)
            .get(FollowerViewModel::class.java)
        return followerViewModel
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        followerListAdapter = FollowerAdapter(activity as MainActivity, followersList)

        layoutManager1 = LinearLayoutManager(activity as MainActivity)

        rvFollowers.apply {
            layoutManager = layoutManager1
            adapter = followerListAdapter
        }
    }

    private fun observeFollowersList() {
        followerViewModel.followersList.observe(this, Observer {
            followersList.clear()
            followersList.addAll(it!!)
            followerListAdapter.notifyDataSetChanged()
        })
    }
}