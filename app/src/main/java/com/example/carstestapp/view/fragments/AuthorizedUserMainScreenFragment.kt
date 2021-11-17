package com.example.carstestapp.view.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.carstestapp.R
import com.example.carstestapp.databinding.FragmentAuthorizedUserMainScreenBinding
import com.example.carstestapp.model.mock.VideoUrlMock
import com.example.carstestapp.util.extensions.toast
import com.example.carstestapp.view.adapters.CarAdapter
import com.example.carstestapp.viewmodels.AuthorizedUserMainScreenViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.util.Log
import com.google.android.exoplayer2.util.Util
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class AuthorizedUserMainScreenFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var binding: FragmentAuthorizedUserMainScreenBinding

    private val viewModel: AuthorizedUserMainScreenViewModel by viewModels()

    private val carAdapter: CarAdapter = CarAdapter(this@AuthorizedUserMainScreenFragment)

    private val carCallHandler: Handler = Handler(Looper.getMainLooper())

    private val carCallRunnable: Runnable = Runnable {
        viewModel.getCars()
        setupHandler()
    }

    private lateinit var player1: ExoPlayer
    private lateinit var player2: ExoPlayer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_authorized_user_main_screen,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showUserInfo()

        binding.rvMainScreen.adapter = carAdapter

        onClick()

        viewModel.cars.observe(viewLifecycleOwner) {
            carAdapter.updateData(it.cars)
        }
        viewModel.error.observe(viewLifecycleOwner) {
            context?.toast(it.toString())
        }
    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT >= 24) {
            initializePlayers()
        }
    }

    override fun onResume() {
        super.onResume()
        if ((Util.SDK_INT < 24)) {
            initializePlayers()
        }
        setupHandler()
    }

    override fun onPause() {
        super.onPause()
        destroyHandler()
    }

    private fun onClick() {
        binding.spinnerFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                carAdapter.filter(adapterView?.getItemAtPosition(position).toString())
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        binding.tvMainScreenLogOut.setOnClickListener {
            sharedPreferences.edit().clear().commit()
            val action = AuthorizedUserMainScreenFragmentDirections
                .actionAuthorizedUserMainScreenFragmentToLogInFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }

    private fun setupHandler() {
        carCallHandler.postDelayed(carCallRunnable, TimeUnit.SECONDS.toMillis(10L))
    }

    private fun destroyHandler() {
        carCallHandler.removeCallbacks(carCallRunnable)
    }

    private fun showUserInfo(){
        sharedPreferences = requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val user = sharedPreferences.getString("user", null)
        binding.user = user
    }

    private fun logOut(){

    }

    /**
     * Players setup, passing links to resources from Mock data
     */
    private fun initializePlayers() {
        player1 = ExoPlayer.Builder(requireContext())
            .build()
            .also { exoPlayer ->
                binding.vvAuthorizedFirstResource.player = exoPlayer
                binding.vvAuthorizedFirstResource.useController = false
                val mediaItem = MediaItem.fromUri(VideoUrlMock.urls[5])
                exoPlayer.setMediaItem(mediaItem)
                exoPlayer.playWhenReady = true
                //exoPlayer.seekTo(0, 0L)
                exoPlayer.prepare()
            }
        player2 = ExoPlayer.Builder(requireContext())
            .build()
            .also { exoPlayer ->
                binding.vvAuthorizedSecondResource.player = exoPlayer
                binding.vvAuthorizedSecondResource.useController = false
                val mediaItem = MediaItem.fromUri(VideoUrlMock.urls[6])
                exoPlayer.setMediaItem(mediaItem)
                exoPlayer.playWhenReady = true
                //exoPlayer.seekTo(0, 0L)
                exoPlayer.prepare()
            }
    }

}