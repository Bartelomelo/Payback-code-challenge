package pl.bartelomelo.paybackcodingchallenge.feature_image.data.remote.dto

import pl.bartelomelo.paybackcodingchallenge.feature_image.domain.model.SearchResponse

class SearchResponseDto(
    val hits: List<HitDto>,
    val total: Int,
    val totalHits: Int
) {
 fun toSearchResponse(): SearchResponse {
     return SearchResponse(
         hits = hits.map { it.toHit()}
     )
 }
}