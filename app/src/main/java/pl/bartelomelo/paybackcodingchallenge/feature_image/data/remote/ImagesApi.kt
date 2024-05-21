package pl.bartelomelo.paybackcodingchallenge.feature_image.data.remote

import pl.bartelomelo.paybackcodingchallenge.feature_image.data.remote.dto.SearchResponseDto
import pl.bartelomelo.paybackcodingchallenge.util.Constants.KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface ImagesApi {
    @GET("?key=$KEY")
    suspend fun getImagesList (
        @Query ("q") query: String,
        @Query ("page") page: Int
    ): SearchResponseDto

    @GET("?key=$KEY")
    suspend fun getImageInfo(
        @Query ("id") imageId: String
    ): SearchResponseDto
}