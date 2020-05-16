package id.nizar.githubcatalogue.di.module

import id.nizar.githubcatalogue.di.scope.ActivityScoped
import id.nizar.githubcatalogue.ui.search.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created By NIZAR NAUFAL 2020
 */

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector()
    @ActivityScoped
    abstract fun mainActivity(): MainActivity
}