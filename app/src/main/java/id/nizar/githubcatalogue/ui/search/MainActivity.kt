package id.nizar.githubcatalogue.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import id.nizar.githubcatalogue.R
import id.nizar.githubcatalogue.ui.base.BaseActivity
import id.nizar.githubcatalogue.viewmodel.ViewFactoryModel
import javax.inject.Inject


/**
 * Created By NIZAR NAUFAL 2020
 */

class MainActivity : BaseActivity<MainViewModel>(), HasSupportFragmentInjector {

    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var viewFactoryModel: ViewFactoryModel

    private lateinit var mainViewModel: MainViewModel

    override fun supportFragmentInjector() = fragmentDispatchingAndroidInjector

    override fun getViewModel(): MainViewModel {
        mainViewModel = ViewModelProvider(this, viewFactoryModel).get(MainViewModel::class.java)
        return mainViewModel
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.home_nav_host_fragment) as NavHostFragment? ?: return
    }
}
