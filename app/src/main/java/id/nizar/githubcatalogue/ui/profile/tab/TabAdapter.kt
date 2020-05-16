package id.nizar.githubcatalogue.ui.profile.tab

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import id.nizar.githubcatalogue.data.model.SearchUser
import id.nizar.githubcatalogue.ui.profile.tab.follower.FollowersFragment
import id.nizar.githubcatalogue.ui.profile.tab.following.FollowingFragment

/**
 * Created By NIZAR NAUFAL 2020
 */

class TabAdapter(fm: FragmentManager, var titles: List<String>, var searchUser: SearchUser,
                 BEHAVIOR_SET_USER_VISIBLE_HINT: Int) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> FollowersFragment.newInstance(searchUser)
            1 -> FollowingFragment.newInstance(searchUser)
            else -> FollowersFragment()
        }
    }

    override fun getCount() = 2

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> {
                return "Followers  (${titles[0]})"
            }

            1 -> {
                return "Following  (${titles[1]})"
            }

            else -> "Followers  (${titles[0]})"
        }
    }
}