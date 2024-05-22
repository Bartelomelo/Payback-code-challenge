package pl.bartelomelo.paybackcodingchallenge.feature_image.data.remote

import pl.bartelomelo.paybackcodingchallenge.feature_image.data.remote.dto.SearchResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ImagesApi {
    @GET("?")
    suspend fun getImagesList (
        @Query ("key") key: String,
        @Query ("q") query: String,
        @Query ("page") page: Int
    ): SearchResponseDto
}