package pl.bartelomelo.paybackcodingchallenge.screens.imagedetailscreen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import pl.bartelomelo.paybackcodingchallenge.data.remote.repository.ImageRepository
import pl.bartelomelo.paybackcodingchallenge.data.remote.responses.SearchResponse
import pl.bartelomelo.paybackcodingchallenge.util.Resource
import javax.inject.Inject

@HiltViewModel
class ImageDetailViewModel @Inject constructor(
    private val repository: ImageRepository
) : ViewModel() {
    suspend fun getImageInfo(id: String): Resource<SearchResponse> {
        return repository.getImageInfo(id)
    }
}