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
    var imageList = mutableStateOf<SearchResponse?>(null)

    init {
        getImageList("flowers")
    }

    fun getImageList(query: String) {
        viewModelScope.launch {
            val result = repository.getImagesList(query)
            when (result) {
                is Resource.Success -> {
                    imageList.value = result.data!!
                }
                is Resource.Error -> {
                    Log.e("error", result.message!!)
                }
                is Resource.Loading -> {

                }
            }
        }
    }
}