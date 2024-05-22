package pl.bartelomelo.paybackcodingchallenge.feature_image.presentation.imagedetailscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pl.bartelomelo.paybackcodingchallenge.feature_image.domain.model.Hit
import pl.bartelomelo.paybackcodingchallenge.feature_image.domain.repository.ImagesRepository
import javax.inject.Inject

@HiltViewModel
class ImageDetailViewModel @Inject constructor(
    private val repository: ImagesRepository
) : ViewModel() {
    private val _imageDetail = MutableStateFlow<Hit?>(null)
    val imageDetail: StateFlow<Hit?> = _imageDetail
    fun getImageDetail(id: Int) {
        viewModelScope.launch {
            _imageDetail.value = repository.getImageDetail(id)
        }
    }
}