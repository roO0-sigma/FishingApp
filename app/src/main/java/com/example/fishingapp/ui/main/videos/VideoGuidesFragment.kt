package com.example.fishingapp.ui.main.videos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fishingapp.R
import com.example.fishingapp.data.model.VideoGuide
import com.example.fishingapp.databinding.FragmentVideoGuidesBinding
import com.example.fishingapp.ui.main.videos.adapter.VideoAdapter

class VideoGuidesFragment : Fragment() {
    private var _binding: FragmentVideoGuidesBinding? = null
    private val binding get() = _binding!!
    private lateinit var videoList: List<VideoGuide>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideoGuidesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageView.setOnClickListener { 
            findNavController().navigate(R.id.action_videoGuidesFragment_to_homeFragment) 
        }

        // Пример данных (можно заменить на данные из БД или API)
        videoList = listOf(
            VideoGuide(
                id = "1",
                title = "Как ловить окуня летом",
                description = "Лучшие техники и снасти для рыбалки в июле.",
                videoUrl = "https://www.youtube.com/watch?v=m_alBKtKKho",
                thumbnailUrl = "https://i.ytimg.com/vi/m_alBKtKKho/hqdefault.jpg",
                duration = "12:30",
                views = 1234
            ),
            VideoGuide(
                id = "2",
                title = "Выбор спиннинга для новичков",
                description = "Разбор основных параметров при выборе спиннинга.",
                videoUrl = "https://rutube.ru/video/0239098628c5c92de5421ad5d0bc159f/",
                thumbnailUrl = "https://pic.rutubelist.ru/video/fa/c2/fac29dff4fb9835137fc39a90a871a82.jpg?width=300",
                duration = "15:45",
                views = 2345
            ),
            VideoGuide(
                id = "3",
                title = "Как ловить окуня летом",
                description = "Лучшие техники и снасти для рыбалки в июле.",
                videoUrl = "https://www.youtube.com/watch?v=m_alBKtKKho",
                thumbnailUrl = "https://i.ytimg.com/vi/m_alBKtKKho/hqdefault.jpg",
                duration = "12:30",
                views = 1234
            )
        )

        binding.recyclerViewVideos.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = VideoAdapter(videoList) { video ->
                val action = VideoGuidesFragmentDirections.actionVideoGuidesToVideoPlayer(video)
                findNavController().navigate(action)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}