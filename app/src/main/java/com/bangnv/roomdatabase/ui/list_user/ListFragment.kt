package com.bangnv.roomdatabase.ui.list_user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangnv.roomdatabase.R
import com.bangnv.roomdatabase.databinding.FragmentListBinding
import com.bangnv.roomdatabase.model.User
import com.bangnv.roomdatabase.utils.showToast
import com.bangnv.roomdatabase.viewmodel.UserViewModel

class ListFragment : Fragment(), MenuProvider {

    private var _binding: FragmentListBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException(getString(R.string.str_binding_null_error))
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var userAdapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
        setupListeners()
        setupMenuProvider()
    }

    private fun setupRecyclerView() {
        binding.rcvUser.layoutManager = LinearLayoutManager(requireContext())
        userAdapter = UserAdapter(object : UserAdapter.OnUserDeleteClickListener {
            override fun onDeleteUserClick(user: User) {
                deleteUser(user)
            }
        })
        binding.rcvUser.adapter = userAdapter
    }

    private fun observeViewModel() {
        userViewModel.readAllData.observe(viewLifecycleOwner) { users ->
            userAdapter.setData(users)
        }
    }

    private fun setupListeners() {
        binding.fabAdd.setOnClickListener {
            navigateToAddFragment()
        }
    }

    private fun setupMenuProvider() {
        requireActivity().addMenuProvider(this, viewLifecycleOwner)
    }

    private fun navigateToAddFragment() {
        findNavController().navigate(R.id.action_listFragment_to_addFragment)
    }

    private fun deleteUser(user: User) {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.str_delete_user))
            .setMessage("Are you sure you want to delete ${user.firstName}?")
            .setPositiveButton(getString(R.string.str_yes)) { _, _ ->
                userViewModel.deleteUser(user)
                showToast("Successfully removed ${user.firstName}")
            }
            .setNegativeButton(getString(R.string.str_no), null)
            .create()
            .show()
    }

    private fun deleteAllUsers() {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.str_delete_everything))
            .setMessage(getString(R.string.str_message_delete_everything))
            .setPositiveButton(getString(R.string.str_yes)) { _, _ ->
                userViewModel.deleteAllUsers()
                showToast(getString(R.string.str_successfully_removed_everything))
            }
            .setNegativeButton(getString(R.string.str_no), null)
            .create()
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.action_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.item_delete_all -> {
                deleteAllUsers()
                true
            }

            else -> false
        }
    }
}

