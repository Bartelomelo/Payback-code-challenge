package pl.bartelomelo.paybackcodingchallenge.data.remote.responses

class SearchResponse(
    val hits: List<Hit>,
    val total: Int,
    val totalHits: Int
)