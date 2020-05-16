package id.nizar.githubcatalogue.di.component

import android.app.Application
import id.nizar.githubcatalogue.di.module.ActivityBindingModule
import id.nizar.githubcatalogue.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import id.nizar.githubcatalogue.GithubApp
import javax.inject.Singleton

/**
 * Created By NIZAR NAUFAL 2020
 */

@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class,
        AppModule::class, ActivityBindingModule::class]
)
interface AppComponent: AndroidInjector<GithubApp> {

    @Component.Builder
     interface  Builder{

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    override fun inject(instance: GithubApp?)
}