package pl.bartelomelo.paybackcodingchallenge.data.remote.repository

import dagger.hilt.android.scopes.ActivityScoped
import pl.bartelomelo.paybackcodingchallenge.data.remote.ImagesApi
import pl.bartelomelo.paybackcodingchallenge.data.remote.responses.SearchResponse
import pl.bartelomelo.paybackcodingchallenge.util.Resource
import javax.inject.Inject

@ActivityScoped
class ImageRepository @Inject constructor(
    private val api: ImagesApi
) {
    suspend fun getImagesList(query: String): Resource<SearchResponse> {
        val response = try {
            api.getImagesList(query)
        } catch (e: Exception) {
            return Resource.Error(e.message.toString())
        }
        return Resource.Success(response)
    }
}