package id.nizar.githubcatalogue.di.module

import android.app.Application
import android.content.Context
import id.nizar.githubcatalogue.data.AppDataConfigManager
import id.nizar.githubcatalogue.data.IAppDataConfig
import id.nizar.githubcatalogue.data.remote.IRemoteData
import id.nizar.githubcatalogue.data.remote.RemoteDataManager
import id.nizar.githubcatalogue.di.module.search.SearchFragmentsBindingModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created By NIZAR NAUFAL 2020
 */

@Module(includes = [ViewModelBindingModule::class, SearchFragmentsBindingModule::class])
class AppModule {

    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context = application

    @Provides
    @Singleton
    internal fun getRemoteSource(remoteDataManager: RemoteDataManager): IRemoteData = remoteDataManager

    @Provides
    @Singleton
    internal fun provideDataManger(appDataManager: AppDataConfigManager): IAppDataConfig = appDataManager
}