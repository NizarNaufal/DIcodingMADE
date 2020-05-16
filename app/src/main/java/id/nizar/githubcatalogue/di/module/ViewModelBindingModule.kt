package id.nizar.githubcatalogue.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.nizar.githubcatalogue.di.key.ViewModelKey
import id.nizar.githubcatalogue.di.module.search.SearchViewModelBindingModule
import id.nizar.githubcatalogue.ui.profile.tab.follower.FollowerViewModel
import id.nizar.githubcatalogue.ui.profile.tab.following.FollowingViewModel
import id.nizar.githubcatalogue.ui.search.MainViewModel
import id.nizar.githubcatalogue.viewmodel.ViewFactoryModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created By NIZAR NAUFAL 2020
 */

@Module(includes = [SearchViewModelBindingModule::class])
abstract class ViewModelBindingModule {

    @Binds
    abstract fun bindViewModelFactory(factoryModel: ViewFactoryModel): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FollowerViewModel::class)
    abstract fun bindFollowerViewModel(followerViewModel: FollowerViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FollowingViewModel::class)
    abstract fun bindFollowingViewModel(followingViewModel: FollowingViewModel): ViewModel
}