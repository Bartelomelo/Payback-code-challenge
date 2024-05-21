package pl.bartelomelo.paybackcodingchallenge.feature_image.domain.repository

import kotlinx.coroutines.flow.Flow
import pl.bartelomelo.paybackcodingchallenge.feature_image.domain.model.Hit
import pl.bartelomelo.paybackcodingchallenge.feature_image.domain.model.SearchResponse
import pl.bartelomelo.paybackcodingchallenge.util.Resource


interface ImagesRepository {
    fun getImageList(query: String, page: Int): Flow<Resource<SearchResponse>>
    fun getImageDetail(id: Int): Flow<Hit>
}