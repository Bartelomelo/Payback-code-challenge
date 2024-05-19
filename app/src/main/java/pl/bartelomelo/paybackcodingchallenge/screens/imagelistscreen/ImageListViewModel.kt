package pl.bartelomelo.paybackcodingchallenge.screens.imagelistscreen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import pl.bartelomelo.paybackcodingchallenge.data.remote.repository.ImageRepository
import pl.bartelomelo.paybackcodingchallenge.data.remote.responses.SearchResponse
import pl.bartelomelo.paybackcodingchallenge.util.Resource
import javax.inject.Inject

@HiltViewModel
class ImageListViewModel @Inject constructor(
    private val repository: ImageRepository
): ViewModel() {
    val imageList = mutableStateOf(SearchResponse(hits = listOf(), total = null, totalHits = 0))
    private var page = 1
    private val _myState = MutableStateFlow(false)
    var searchBarActive = mutableStateOf(false)
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

    private fun resetPage() {
        page = 1
    }
    fun incrementPage() {
        page++
    }
    private fun decrementPage() {
        if (page != 1) page--
    }
    private fun saveSearchQuery(query: String) {
        searchedQuery.value = query
    }
    fun loadPreviousPage(query: String) {
        if (page != 1) {
            decrementPage()
            getImageList(query)
        }
    }
    fun searchQuery(query: String) {
        saveSearchQuery(query)
        resetPage()
        getImageList(query)
    }
    fun updateScrollToTop(value: Boolean) {
        _myState.value = value
    }
}