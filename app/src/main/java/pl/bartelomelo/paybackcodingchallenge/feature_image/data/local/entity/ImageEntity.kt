package pl.bartelomelo.paybackcodingchallenge.feature_image.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import pl.bartelomelo.paybackcodingchallenge.feature_image.domain.model.Hit

@Entity
data class ImageEntity(
    val query: String,
    val comments: Int,
    val downloads: Int,
    val imageHeight: Int,
    val imageWidth: Int,
    val largeImageURL: String,
    val likes: Int,
    val webformatURL: String,
    val tags: String,
    val user: String,
    val views: Int,
    @PrimaryKey val id: Int
) {
    fun toHit(): Hit {
        return Hit(
            query = query,
            comments = comments,
            downloads = downloads,
            imageHeight = imageHeight,
            imageWidth = imageWidth,
            largeImageURL = largeImageURL,
            likes = likes,
            webformatURL = webformatURL,
            tags = tags,
            user = user,
            views = views,
            id = id
        )
    }
}