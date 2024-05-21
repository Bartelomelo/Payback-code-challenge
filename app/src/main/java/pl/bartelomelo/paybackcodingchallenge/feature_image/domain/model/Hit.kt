package pl.bartelomelo.paybackcodingchallenge.feature_image.domain.model

data class Hit(
    val query: String,
    val comments: Int,
    val downloads: Int,
    val id: Int,
    val imageHeight: Int,
    val imageWidth: Int,
    val largeImageURL: String,
    val likes: Int,
    val webformatURL: String,
    val tags: String,
    val user: String,
    val views: Int,
)
