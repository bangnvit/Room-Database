package com.bangnv.roomdatabase.ui.list_user

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bangnv.roomdatabase.databinding.ItemUserBinding
import com.bangnv.roomdatabase.model.User

class UserAdapter(private val deleteClickListener: OnUserDeleteClickListener) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    interface OnUserDeleteClickListener {
        fun onDeleteUserClick(user: User)
    }

    private var userList = emptyList<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemUserBinding =
            ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(itemUserBinding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = userList[position]
        holder.bind(currentUser)
    }

    override fun getItemCount(): Int = userList.size

    fun setData(newUsers: List<User>) {
        // Create an instance of DiffUtil
        val diffUtil = UserDiffUtil(userList, newUsers)
        val diffResult = DiffUtil.calculateDiff(diffUtil)

        // Update the userList with new data
        userList = newUsers

        // Notify the RecyclerView of the changes
        diffResult.dispatchUpdatesTo(this)
    }

    inner class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(user: User) {
            binding.tvId.text = user.id.toString()
            binding.tvFirstName.text = user.firstName
            binding.tvLastName.text = user.lastName
            binding.tvAge.text = "(${user.age})"

            binding.ibDelete.setOnClickListener {
                deleteClickListener.onDeleteUserClick(user)
            }

            binding.root.setOnClickListener {
                val action = ListFragmentDirections.actionListFragmentToUpdateFragment(user)
                binding.root.findNavController().navigate(action)
            }
        }
    }
}