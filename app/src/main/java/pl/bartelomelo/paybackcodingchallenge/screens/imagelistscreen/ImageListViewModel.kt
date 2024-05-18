package pl.bartelomelo.paybackcodingchallenge.screens.imagelistscreen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pl.bartelomelo.paybackcodingchallenge.data.remote.repository.ImageRepository
import pl.bartelomelo.paybackcodingchallenge.data.remote.responses.SearchResponse
import pl.bartelomelo.paybackcodingchallenge.util.Resource
import javax.inject.Inject

@HiltViewModel
class ImageListViewModel @Inject constructor(
    private val repository: ImageRepository
): ViewModel() {
    val imageList = mutableStateOf(SearchResponse(hits = listOf(), total = 0, totalHits = 0))
    private var page = 1
    var query = mutableStateOf("flowers")
    var searchedQuery = mutableStateOf("flowers")

    init {
        getImageList(query.value)
    }

    fun getImageList(query: String) {
        viewModelScope.launch {
            val result = repository.getImagesList(query, page)
            when (result) {
                is Resource.Success -> {
                    imageList.value = result.data!!
                    Log.d("current page", page.toString())
                }
                is Resource.Error -> {
                    Log.e("error", result.message!!)
                }
                is Resource.Loading -> {

                }
            }
        }
    }

    fun resetPage() {
        page = 1
    }
    fun incrementPage() {
        page++
    }
    fun decrementPage() {
        if (page != 1) page--
    }
    fun saveSearchQuery(query: String) {
        searchedQuery.value = query
    }
}