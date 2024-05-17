package pl.bartelomelo.paybackcodingchallenge.data.remote.responses

data class SearchResponse(
    val hits: List<Hit>,
    val total: Int,
    val totalHits: Int
)