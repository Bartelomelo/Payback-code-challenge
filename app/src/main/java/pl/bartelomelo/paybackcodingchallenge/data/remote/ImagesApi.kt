package pl.bartelomelo.paybackcodingchallenge.data.remote

import pl.bartelomelo.paybackcodingchallenge.data.remote.responses.SearchResponse
import pl.bartelomelo.paybackcodingchallenge.util.Constants.KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface ImagesApi {
    @GET("?key=$KEY&")
    suspend fun getImagesList (
        @Query ("q") query: String
    ): SearchResponse
}