package id.nizar.githubcatalogue.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.util.ArrayList

/**
 * Created By NIZAR NAUFAL 2020
 */

data class SearchResponse(
    @SerializedName("total_count") val total_count: Int,
    @SerializedName("incomplete_results") val incompleteResults: Boolean,
    @SerializedName("items") val userList: ArrayList<SearchUser>?
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readInt(),
        1 == source.readInt(),
        source.createTypedArrayList(SearchUser.CREATOR)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(total_count)
        writeInt((if (incompleteResults) 1 else 0))
        writeTypedList(userList)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<SearchResponse> = object : Parcelable.Creator<SearchResponse> {
            override fun createFromParcel(source: Parcel): SearchResponse = SearchResponse(source)
            override fun newArray(size: Int): Array<SearchResponse?> = arrayOfNulls(size)
        }
    }
}