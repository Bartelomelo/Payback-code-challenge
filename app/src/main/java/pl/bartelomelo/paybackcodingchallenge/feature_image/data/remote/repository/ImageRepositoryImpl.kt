package pl.bartelomelo.paybackcodingchallenge.feature_image.data.remote.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pl.bartelomelo.paybackcodingchallenge.feature_image.data.local.ImagesDao
import pl.bartelomelo.paybackcodingchallenge.feature_image.data.remote.ImagesApi
import pl.bartelomelo.paybackcodingchallenge.feature_image.domain.model.Hit
import pl.bartelomelo.paybackcodingchallenge.feature_image.domain.model.SearchResponse
import pl.bartelomelo.paybackcodingchallenge.feature_image.domain.repository.ImagesRepository
import pl.bartelomelo.paybackcodingchallenge.util.Constants
import pl.bartelomelo.paybackcodingchallenge.util.Resource
import retrofit2.HttpException
import java.io.IOException

class ImageRepositoryImpl(
    private val api: ImagesApi,
    private val dao: ImagesDao
): ImagesRepository {
    override fun getImageList(query: String, page: Int): Flow<Resource<SearchResponse>> = flow {
        emit(Resource.Loading())
        val cachedImageList = dao.getImagesList(query)
        emit(Resource.Loading(data = SearchResponse(cachedImageList)))

        try {
            val remoteImageList = api.getImagesList(key = Constants.KEY, query = query, page = page)
            remoteImageList.hits.map { it.query = query}
            dao.deleteImages(remoteImageList.hits.map { it.query })
            dao.insertImages(remoteImageList.hits.map { it.toHitEntity() })
        } catch (e: HttpException) {
            emit(Resource.Error(
                message = "something went wrong!",
                data = SearchResponse(cachedImageList)
            ))
        } catch (e: IOException) {
            emit(Resource.Error(
                message = "Check your internet connection!",
                data = SearchResponse(cachedImageList)
            ))
        }
        val newImageList = dao.getImagesList(query)
        emit(Resource.Success(SearchResponse(newImageList)))
    }

    override suspend fun getImageDetail(id: Int): Hit? {
       return dao.getImageDetail(id)
    }

}