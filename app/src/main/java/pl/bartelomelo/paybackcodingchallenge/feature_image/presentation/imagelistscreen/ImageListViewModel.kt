package pl.bartelomelo.paybackcodingchallenge.feature_image.presentation.imagelistscreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
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
) : ViewModel() {
    val imageList = mutableStateOf(SearchResponse(hits = listOf()))
    private var page = 1
    var searchBarActive = mutableStateOf(false)
    var query = mutableStateOf("flowers")
    var searchedQuery = mutableStateOf("flowers")
    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getImageList(query.value)
    }

    private fun getImageList(query: String) {
        viewModelScope.launch {
            repository.getImageList(query, page)
                .onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                            imageList.value = result.data ?: SearchResponse(emptyList())
                        }

                        is Resource.Error -> {
                            imageList.value = result.data ?: SearchResponse(emptyList())
                            _eventFlow.emit(
                                UIEvent.ShowSnackbar(
                                    result.message ?: "An unknown error."
                                )
                            )
                        }

                        is Resource.Loading -> {
                            imageList.value = result.data ?: SearchResponse(emptyList())
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

    sealed class UIEvent {
        data class ShowSnackbar(val message: String) : UIEvent()
    }
}