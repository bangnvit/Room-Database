package com.bangnv.roomdatabase.ui.add_user

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bangnv.roomdatabase.R
import com.bangnv.roomdatabase.model.User
import com.bangnv.roomdatabase.databinding.FragmentAddBinding
import com.bangnv.roomdatabase.utils.showToast
import com.bangnv.roomdatabase.viewmodel.UserViewModel

class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException(getString(R.string.str_binding_null_error))
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        binding.btnAdd.setOnClickListener { insertDataToDatabase() }
    }

    private fun insertDataToDatabase() {
        val firstName = binding.edtFirstName.text.toString().trim()
        val lastName = binding.edtLastName.text.toString().trim()
        val age = binding.edtAge.text

        if (inputCheck(firstName, lastName, age)) {
            userViewModel.addUser(createUser(firstName, lastName, age))
            showToast(getString(R.string.str_successfully_added))
            navigateBackToListFragment()
        } else {
            showToast(getString(R.string.str_fill_fields_and_age_valid))
        }
    }

    private fun createUser(firstName: String, lastName: String, age: Editable): User {
        val ageValue = age.toString().toInt()
        return User(id = 0, firstName = firstName, lastName = lastName, age = ageValue)
    }

    private fun inputCheck(firstName: String, lastName: String, age: Editable): Boolean {
        return firstName.isNotBlank() && lastName.isNotBlank() && age.toString().toIntOrNull()
            .isAgeValid()
    }

    private fun Int?.isAgeValid(): Boolean = this != null && this in 0..120

    private fun navigateBackToListFragment() {
        findNavController().navigate(R.id.action_addFragment_to_listFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}