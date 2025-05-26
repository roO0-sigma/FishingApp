package com.example.fishingapp.ui.main.forum.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp

data class ForumPost(
    val id: String = "",
    val title: String = "",
    val content: String = "",
    val authorEmail: String = "",
    @ServerTimestamp
    val timestamp: Timestamp? = null,
    val topicId: String = ""
) 