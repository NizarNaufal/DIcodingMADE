package id.nizar.githubcatalogue.di.module.search

import id.nizar.githubcatalogue.ui.profile.UserProfileFragment
import id.nizar.githubcatalogue.ui.profile.tab.follower.FollowersFragment
import id.nizar.githubcatalogue.ui.profile.tab.following.FollowingFragment
import id.nizar.githubcatalogue.ui.search.userlist.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created By NIZAR NAUFAL 2020
 */

@Module

abstract class SearchFragmentsBindingModule {

    @ContributesAndroidInjector
    abstract fun userListFragment(): SearchFragment

    @ContributesAndroidInjector
    abstract fun userProfileFragment(): UserProfileFragment

    @ContributesAndroidInjector
    abstract fun followersFragment(): FollowersFragment

    @ContributesAndroidInjector
    abstract  fun followingFragment(): FollowingFragment
}