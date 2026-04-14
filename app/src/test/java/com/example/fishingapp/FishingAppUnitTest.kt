package com.example.fishingapp


import org.junit.Test
import org.junit.Assert.*
import com.example.fishingapp.data.model.Fish
import com.example.fishingapp.data.model.VideoGuide
import com.example.fishingapp.ui.main.forum.model.ForumPost
import com.google.firebase.Timestamp
import java.util.Date

/**
 * Набор юнит-тестов для проверки моделей данных и бизнес-логики приложения FishingApp.
 */
class FishingAppUnitTest {

    // Тест 1: Проверка создания объекта Fish с корректными данными
    @Test
    fun fishModel_creation_withValidData_success() {
        val fish = Fish(
            id = "fish_001",
            name = "Щука",
            averageWeight = 5.5,
            averageLength = 80.0,
            waterType = Fish.WATER_TYPE_RIVER,
            description = "Хищная рыба, обитающая в пресных водоемах",
            baitTypes = listOf("Блесна", "Воблер", "Живец"),
            photoUrl = "https://example.com/pike.jpg",
            seasonality = listOf("Весна", "Осень"),
            bestTimeOfDay = "Утро"
        )

        assertEquals("fish_001", fish.id)
        assertEquals("Щука", fish.name)
        assertEquals("щука", fish.nameLower)
        assertEquals(5.5, fish.averageWeight, 0.01)
        assertEquals(80.0, fish.averageLength, 0.01)
        assertEquals(Fish.WATER_TYPE_RIVER, fish.waterType)
        assertEquals(3, fish.baitTypes.size)
        assertTrue(fish.seasonality.contains("Весна"))
    }

    // Тест 2: Проверка создания объекта VideoGuide
    @Test
    fun videoGuideModel_creation_withValidData_success() {
        val videoGuide = VideoGuide(
            id = "video_001",
            title = "Как ловить щуку осенью",
            description = "Подробный гайд по осенней ловле щуки на спиннинг",
            videoUrl = "https://youtube.com/watch?v=example",
            thumbnailUrl = "https://example.com/thumb.jpg",
            duration = "15:30",
            views = 1500
        )

        assertEquals("video_001", videoGuide.id)
        assertEquals("Как ловить щуку осенью", videoGuide.title)
        assertEquals("15:30", videoGuide.duration)
        assertEquals(1500, videoGuide.views)
        assertNotNull(videoGuide.thumbnailUrl)
    }

    // Тест 3: Проверка создания объекта ForumPost
    @Test
    fun forumPostModel_creation_withValidData_success() {
        val timestamp = Timestamp(Date())
        val forumPost = ForumPost(
            id = "post_001",
            title = "Лучшие места для рыбалки под Москвой",
            content = "Делюсь опытом ловли на местных водоемах...",
            authorEmail = "fisherman@example.com",
            timestamp = timestamp,
            topicId = "topic_moscow"
        )

        assertEquals("post_001", forumPost.id)
        assertEquals("Лучшие места для рыбалки под Москвой", forumPost.title)
        assertEquals("fisherman@example.com", forumPost.authorEmail)
        assertEquals("topic_moscow", forumPost.topicId)
        assertNotNull(forumPost.timestamp)
    }

    // Тест 4: Проверка фильтрации рыбы по типу водоема
    @Test
    fun fishFilter_byWaterType_returnsCorrectResults() {
        val fishList = listOf(
            Fish(id = "1", name = "Щука", waterType = Fish.WATER_TYPE_RIVER),
            Fish(id = "2", name = "Окунь", waterType = Fish.WATER_TYPE_LAKE),
            Fish(id = "3", name = "Судак", waterType = Fish.WATER_TYPE_RIVER),
            Fish(id = "4", name = "Карась", waterType = Fish.WATER_TYPE_LAKE),
            Fish(id = "5", name = "Треска", waterType = Fish.WATER_TYPE_SEA)
        )

        val riverFish = fishList.filter { it.waterType == Fish.WATER_TYPE_RIVER }
        val lakeFish = fishList.filter { it.waterType == Fish.WATER_TYPE_LAKE }
        val seaFish = fishList.filter { it.waterType == Fish.WATER_TYPE_SEA }

        assertEquals(2, riverFish.size)
        assertEquals(2, lakeFish.size)
        assertEquals(1, seaFish.size)
        assertTrue(riverFish.all { it.waterType == Fish.WATER_TYPE_RIVER })
    }

    // Тест 5: Проверка поиска рыбы по названию (регистронезависимый поиск)
    @Test
    fun fishSearch_byName_caseInsensitive_returnsCorrectResults() {
        val fishList = listOf(
            Fish(id = "1", name = "Щука", nameLower = "щука"),
            Fish(id = "2", name = "Окунь", nameLower = "окунь"),
            Fish(id = "3", name = "Судак", nameLower = "судак"),
            Fish(id = "4", name = "Карась", nameLower = "карась")
        )

        val searchQuery = "щук"
        val results = fishList.filter { it.nameLower.contains(searchQuery.lowercase()) }

        assertEquals(1, results.size)
        assertEquals("Щука", results[0].name)
    }

    // Тест 6: Проверка сортировки рыбы по весу
    @Test
    fun fishSort_byWeight_descending_returnsCorrectOrder() {
        val fishList = listOf(
            Fish(id = "1", name = "Карась", averageWeight = 0.5),
            Fish(id = "2", name = "Щука", averageWeight = 5.5),
            Fish(id = "3", name = "Окунь", averageWeight = 1.2),
            Fish(id = "4", name = "Сом", averageWeight = 15.0)
        )

        val sortedByWeightDesc = fishList.sortedByDescending { it.averageWeight }

        assertEquals(4, sortedByWeightDesc.size)
        assertEquals("Сом", sortedByWeightDesc[0].name)
        assertEquals("Щука", sortedByWeightDesc[1].name)
        assertEquals("Окунь", sortedByWeightDesc[2].name)
        assertEquals("Карась", sortedByWeightDesc[3].name)
    }

    // Тест 7: Проверка валидации данных видео-гайда
    @Test
    fun videoGuideValidation_emptyTitle_returnsFalse() {
        val validVideo = VideoGuide(
            id = "video_001",
            title = "Как ловить рыбу",
            description = "Описание",
            videoUrl = "https://example.com/video.mp4",
            thumbnailUrl = "https://example.com/thumb.jpg"
        )

        val invalidVideo = VideoGuide(
            id = "video_002",
            title = "",
            description = "Описание",
            videoUrl = "https://example.com/video.mp4",
            thumbnailUrl = "https://example.com/thumb.jpg"
        )

        assertTrue(validVideo.title.isNotEmpty())
        assertTrue(invalidVideo.title.isEmpty())
    }

    // Тест 8: Проверка подсчета количества приманок у рыбы
    @Test
    fun fishBaitCount_calculation_returnsCorrectCount() {
        val fish = Fish(
            id = "1",
            name = "Щука",
            baitTypes = listOf("Блесна", "Воблер", "Джиг", "Живец")
        )

        assertEquals(4, fish.baitTypes.size)
        assertTrue(fish.baitTypes.contains("Воблер"))
        assertFalse(fish.baitTypes.contains("Мормышка"))
    }

    // Тест 9: Проверка создания ForumPost с пустыми полями (значения по умолчанию)
    @Test
    fun forumPostModel_defaultValues_areCorrect() {
        val forumPost = ForumPost()

        assertEquals("", forumPost.id)
        assertEquals("", forumPost.title)
        assertEquals("", forumPost.content)
        assertEquals("", forumPost.authorEmail)
        assertEquals("", forumPost.topicId)
        assertNull(forumPost.timestamp)
    }

    // Тест 10: Проверка фильтрации по сезону
    @Test
    fun fishFilter_bySeason_returnsCorrectResults() {
        val fishList = listOf(
            Fish(id = "1", name = "Щука", seasonality = listOf("Весна", "Осень")),
            Fish(id = "2", name = "Карась", seasonality = listOf("Лето")),
            Fish(id = "3", name = "Окунь", seasonality = listOf("Весна", "Лето", "Осень")),
            Fish(id = "4", name = "Налим", seasonality = listOf("Зима"))
        )

        val autumnFish = fishList.filter { it.seasonality.contains("Осень") }
        val summerFish = fishList.filter { it.seasonality.contains("Лето") }

        assertEquals(2, autumnFish.size)
        assertEquals(2, summerFish.size)
        assertTrue(autumnFish.any { it.name == "Щука" })
        assertTrue(autumnFish.any { it.name == "Окунь" })
    }
}