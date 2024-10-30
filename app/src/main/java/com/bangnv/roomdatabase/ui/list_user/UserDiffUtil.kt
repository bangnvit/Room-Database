package com.bangnv.roomdatabase.ui.list_user

import androidx.recyclerview.widget.DiffUtil
import com.bangnv.roomdatabase.model.User

class UserDiffUtil(
    private val oldList: List<User>,
    private val newList: List<User>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        // Compare ID or unique property
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        // Compare the contents of the object
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
