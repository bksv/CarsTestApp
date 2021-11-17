package com.example.carstestapp.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.carstestapp.R
import com.example.carstestapp.databinding.FragmentUnauthorizedUserScreenBinding
import com.example.carstestapp.model.mock.VideoUrlMock
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.util.Util
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UnauthorizedUserScreenFragment : Fragment() {

    private lateinit var binding: FragmentUnauthorizedUserScreenBinding

    private lateinit var player1: ExoPlayer
    private lateinit var player2: ExoPlayer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_unauthorized_user_screen, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT >=24){
            initializePlayers()
        }
    }

    override fun onResume() {
        super.onResume()
        if ((Util.SDK_INT < 24)) {
            initializePlayers()
        }
    }

    /**
     * Players setup, passing links to resources from Mock data
     */
    private fun initializePlayers(){
        player1 = ExoPlayer.Builder(requireContext())
            .build()
            .also { exoPlayer ->
                binding.vvUnauthorizedFirstResource.player = exoPlayer
                binding.vvUnauthorizedFirstResource.useController = false
                val mediaItem = MediaItem.fromUri(VideoUrlMock.urls[2])
                exoPlayer.setMediaItem(mediaItem)
                exoPlayer.playWhenReady = true
                //exoPlayer.seekTo(0, 0L)
                exoPlayer.prepare()
            }
        player2 = ExoPlayer.Builder(requireContext())
            .build()
            .also { exoPlayer ->
                binding.vvUnauthorizedSecondResource.player = exoPlayer
                binding.vvUnauthorizedSecondResource.useController = false
                val mediaItem = MediaItem.fromUri(VideoUrlMock.urls[3])
                exoPlayer.setMediaItem(mediaItem)
                exoPlayer.playWhenReady = true
                //exoPlayer.seekTo(0, 0L)
                exoPlayer.prepare()
            }
    }

}