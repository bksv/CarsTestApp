package com.example.carstestapp.view.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.carstestapp.R
import com.example.carstestapp.databinding.FragmentLogInBinding
import com.example.carstestapp.view.activities.MainActivity


class LogInFragment : Fragment() {

    private lateinit var binding: FragmentLogInBinding

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_log_in, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkUserExistence()
        onClick()
    }

    private fun onClick(){
        binding.btnSignIn.setOnClickListener {
            if (binding.etUsername.text!!.isEmpty() || binding.etPassword.text!!.isEmpty()){
                showAlertDialog()
            }else{
                view?.let {
                    rememberUser()
                    val action = LogInFragmentDirections.actionLogInFragmentToAuthorizedUserMainScreenFragment()
                    Navigation.findNavController(it).navigate(action)
                }
            }
        }
        binding.btnLiveVideo.setOnClickListener {
            view?.let {
                Navigation.findNavController(it).navigate(R.id.action_logInFragment_to_unauthorizedUserScreenFragment)
            }
        }
    }

    private fun showAlertDialog(){
        AlertDialog.Builder(requireContext()).setMessage(R.string.invalid_sign_in)
            .setPositiveButton(R.string.alert_positive_btn) {dialog,_ ->
                dialog.dismiss()
            }.show()
    }

    private fun rememberUser(){
        val username = binding.etUsername.text.toString()
        sharedPreferences = requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.apply{
            putString("user", username)
        }.apply()
    }

    private fun checkUserExistence(){
        sharedPreferences = requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val user = sharedPreferences.getString("user", null)
        if (user != null){
            view?.let {
                val action = LogInFragmentDirections.actionLogInFragmentToAuthorizedUserMainScreenFragment()
                Navigation.findNavController(it).navigate(action)
            }
        }else{
            Log.e("LogInFragment", "No user found")
        }
    }

}