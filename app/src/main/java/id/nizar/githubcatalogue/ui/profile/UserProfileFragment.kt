package id.nizar.githubcatalogue.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import id.nizar.githubcatalogue.R
import id.nizar.githubcatalogue.data.model.SearchUser
import id.nizar.githubcatalogue.ui.base.BaseFragment
import id.nizar.githubcatalogue.ui.profile.tab.TabAdapter
import id.nizar.githubcatalogue.ui.search.MainActivity
import id.nizar.githubcatalogue.ui.search.userlist.SearchFragment
import id.nizar.githubcatalogue.viewmodel.ViewFactoryModel
import id.nizar.githubcatalogue.widgets.NetworkUtils
import kotlinx.android.synthetic.main.fragment_user_profile.*
import javax.inject.Inject


/**
 * Created By NIZAR NAUFAL 2020
 */

class UserProfileFragment : BaseFragment<UserProfileViewModel>() {

    private lateinit var userProfileViewModel: UserProfileViewModel

    @Inject
    lateinit var viewFactoryModel: ViewFactoryModel

     lateinit var currentUser: SearchUser

    override fun getViewModel(): UserProfileViewModel {
        userProfileViewModel = ViewModelProviders.of(this@UserProfileFragment, viewFactoryModel)
            .get(UserProfileViewModel::class.java)
        return userProfileViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        observeLoading()
        observeMessage()
        observeUserProfile()

        currentUser = arguments?.getParcelable(SearchFragment.SEARCHED_USER)!!

        if (NetworkUtils.isNetworkConnected(activity as MainActivity)){
            userProfileViewModel.getUserProfile(currentUser.login.toString())
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private  fun observeLoading(){
        userProfileViewModel.loading.observe(this, Observer {
            (activity as MainActivity).setProgressVisibility(it)
        })
    }

    private  fun observeMessage(){
        userProfileViewModel.message.observe(this, Observer {
            (activity as MainActivity).showInfoToast(it)
        })
    }

    private fun observeUserProfile() {
    /*    Log.d("data user",userProfileViewModel.toString())*/
        userProfileViewModel.userProfile.observe(this, Observer {
            Glide.with(activity as MainActivity)
                .load(it.avatarUrl)
                .into(civProfileAvatar)
            tvUserName.text = it.name
            name_company.text = it.bio
            tvLocation.text = it.location
            tvFollowers.text = it.followers.toString()
            tvFollowing.text = it.following.toString()
            tvNumberOfRepos.text = it.publicRepos.toString()
            val titleArray = listOf(it.followers.toString(), it.following.toString())
            val adapter = TabAdapter(activity?.supportFragmentManager!!, titleArray, currentUser, 1)
            viewPager.adapter = adapter
            tabLayout.setupWithViewPager(viewPager)
        })
    }
}