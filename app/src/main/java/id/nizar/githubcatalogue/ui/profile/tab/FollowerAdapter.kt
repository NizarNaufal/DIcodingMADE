package id.nizar.githubcatalogue.ui.profile.tab

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.nizar.githubcatalogue.R
import id.nizar.githubcatalogue.data.model.Follower
import kotlinx.android.synthetic.main.item_searched_user.view.*

/**
 * Created By NIZAR NAUFAL 2020
 */

class FollowerAdapter (val context: Context, private  var userList: ArrayList<Follower>):
    RecyclerView.Adapter<FollowerAdapter.FollowerViewHolder>(){

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): FollowerViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_searched_user, p0, false)
        return FollowerViewHolder(view)
    }

    override fun getItemCount() = userList.size

    override fun onBindViewHolder(holder: FollowerViewHolder, position: Int) {
        holder.ivUserAvatar?.let {
            Glide.with(context)
                .load(userList[position].avatarUrl)
                .into(it)
        }

        holder.tvUserName?.text = userList[position].login
    }


    class FollowerViewHolder(view: View): RecyclerView.ViewHolder(view){
        val ivUserAvatar: AppCompatImageView? = view.civUserAvatar
        val tvUserName: AppCompatTextView? = view.tvLoginId
    }
}