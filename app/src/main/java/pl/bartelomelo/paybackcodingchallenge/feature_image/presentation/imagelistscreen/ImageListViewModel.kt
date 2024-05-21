package pl.bartelomelo.paybackcodingchallenge.feature_image.presentation.imagelistscreen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import pl.bartelomelo.paybackcodingchallenge.feature_image.domain.model.SearchResponse
import pl.bartelomelo.paybackcodingchallenge.feature_image.domain.repository.ImagesRepository
import pl.bartelomelo.paybackcodingchallenge.util.Resource
import javax.inject.Inject

@HiltViewModel
class ImageListViewModel @Inject constructor(
    private val repository: ImagesRepository
): ViewModel() {
    val imageList = mutableStateOf(SearchResponse(hits = listOf()))
    private var page = 1
    private var maxPage = 10
    private val _myState = MutableStateFlow(false)
    var searchBarActive = mutableStateOf(false)
    var query = mutableStateOf("flowers")
    var searchedQuery = mutableStateOf("flowers")

    init {
        getImageList(query.value)
    }

    fun getImageList(query: String) {
        viewModelScope.launch {
            repository.getImageList(query, page)
                .onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                            imageList.value = result.data ?: SearchResponse(emptyList())
                            Log.d("current page", page.toString())
                            Log.d("list", imageList.value.hits.toString())
                        }
                        is Resource.Error -> {
                            imageList.value = result.data ?: SearchResponse(emptyList())
                            Log.e("error VM", result.message.toString())
                        }
                        is Resource.Loading -> {
                            imageList.value = result.data ?: SearchResponse(emptyList())
                            Log.d("current page", page.toString())
                        }
                    }
                }.launchIn(this)
        }
    }

    private fun resetPage() {
        page = 1
    }
    private fun incrementPage() {
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
    fun loadNextPage(query: String) {
        incrementPage()
        getImageList(query)
    }
    fun searchQuery(query: String) {
        saveSearchQuery(query)
        resetPage()
        getImageList(query)
    }
}