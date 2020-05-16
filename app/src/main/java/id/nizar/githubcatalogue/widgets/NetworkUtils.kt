package id.nizar.githubcatalogue.widgets

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created By NIZAR NAUFAL 2020
 */

object NetworkUtils {
    fun isNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }
}