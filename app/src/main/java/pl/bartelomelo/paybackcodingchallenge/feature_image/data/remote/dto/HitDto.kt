package pl.bartelomelo.paybackcodingchallenge.feature_image.data.remote.dto

import pl.bartelomelo.paybackcodingchallenge.feature_image.data.local.entity.ImageEntity
import pl.bartelomelo.paybackcodingchallenge.feature_image.domain.model.Hit

data class HitDto(
    var query: String,
    val collections: Int,
    val comments: Int,
    val downloads: Int,
    val id: Int,
    val imageHeight: Int,
    val imageSize: Int,
    val imageWidth: Int,
    val largeImageURL: String,
    val likes: Int,
    val pageURL: String,
    val previewHeight: Int,
    val previewURL: String,
    val previewWidth: Int,
    val tags: String,
    val type: String,
    val user: String,
    val userImageURL: String,
    val user_id: Int,
    val views: Int,
    val webformatHeight: Int,
    val webformatURL: String,
    val webformatWidth: Int
) {
    fun toHitEntity(): ImageEntity {
        return ImageEntity(
            query = query,
            comments = comments,
            downloads = downloads,
            id = id,
            imageHeight = imageHeight,
            imageWidth = imageWidth,
            largeImageURL = largeImageURL,
            likes = likes,
            webformatURL = webformatURL,
            tags = tags,
            user = user,
            views = views
        )
    }

    fun toHit(): Hit {
        return Hit(
            query = query,
            comments = comments,
            downloads = downloads,
            id = id,
            imageHeight = imageHeight,
            imageWidth = imageWidth,
            largeImageURL = largeImageURL,
            likes = likes,
            webformatURL = webformatURL,
            tags = tags,
            user = user,
            views = views
        )
    }
}