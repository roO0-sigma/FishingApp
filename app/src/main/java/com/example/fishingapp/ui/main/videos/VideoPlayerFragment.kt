package com.example.fishingapp.ui.main.videos

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.fishingapp.R

class VideoPlayerFragment : Fragment(R.layout.fragment_video_player) {

    private lateinit var args: VideoPlayerFragmentArgs

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        args = VideoPlayerFragmentArgs.fromBundle(requireArguments())
        
        openVideoExternally(args.video.videoUrl)
    }

    private fun openVideoExternally(videoUrl: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl))
        startActivity(intent)
        parentFragmentManager.popBackStack()
    }
}