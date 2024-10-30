package com.bangnv.roomdatabase.ui.update_user

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bangnv.roomdatabase.R
import com.bangnv.roomdatabase.databinding.FragmentUpdateBinding
import com.bangnv.roomdatabase.model.User
import com.bangnv.roomdatabase.utils.showToast
import com.bangnv.roomdatabase.viewmodel.UserViewModel

class UpdateFragment : Fragment() {

    private var _binding: FragmentUpdateBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException(getString(R.string.str_binding_null_error))
    private val userViewModel: UserViewModel by viewModels()

    private val args by navArgs<UpdateFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTextUI()
        setupListeners()
    }

    @SuppressLint("SetTextI18n")
    private fun setTextUI() {
        binding.edtFirstName.setText(args.currentUser.firstName)
        binding.edtLastName.setText(args.currentUser.lastName)
        binding.edtAge.setText(args.currentUser.age.toString())
    }

    private fun setupListeners() {
        binding.btnUpdate.setOnClickListener {
            updateItem()
        }
    }

    private fun updateItem() {
        val firstName = binding.edtFirstName.text.toString().trim()
        val lastName = binding.edtLastName.text.toString().trim()
        val age = binding.edtAge.text

        if (inputCheck(firstName, lastName, age)) {
            userViewModel.updateUser(createUser(firstName, lastName, age))
            showToast(getString(R.string.str_successfully_updated))
            navigateBackToListFragment()
        } else {
            showToast(getString(R.string.str_fill_fields_and_age_valid))
        }
    }

    private fun createUser(firstName: String, lastName: String, age: Editable): User {
        val ageValue = age.toString().toInt()
        return User(args.currentUser.id, firstName, lastName, ageValue)
    }

    private fun inputCheck(firstName: String, lastName: String, age: Editable): Boolean {
        return firstName.isNotBlank() && lastName.isNotBlank() && age.toString().toIntOrNull()
            .isAgeValid()
    }

    private fun Int?.isAgeValid(): Boolean = this != null && this in 0..120

    private fun navigateBackToListFragment() {
        findNavController().navigate(R.id.action_updateFragment_to_listFragment)
    }

}