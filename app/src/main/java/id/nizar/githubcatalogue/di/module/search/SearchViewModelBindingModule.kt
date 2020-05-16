package id.nizar.githubcatalogue.di.module.search

import androidx.lifecycle.ViewModel
import id.nizar.githubcatalogue.di.key.ViewModelKey
import id.nizar.githubcatalogue.ui.profile.UserProfileViewModel
import id.nizar.githubcatalogue.ui.search.userlist.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created By NIZAR NAUFAL 2020
 */

@Module
abstract class SearchViewModelBindingModule {

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindUserListViewModel(searchViewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserProfileViewModel::class)
    abstract fun bindUserProfileViewModel(userProfileViewModel: UserProfileViewModel): ViewModel
}