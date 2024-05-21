package pl.bartelomelo.paybackcodingchallenge.feature_image.presentation.imagedetailscreen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import pl.bartelomelo.paybackcodingchallenge.feature_image.domain.model.Hit
import pl.bartelomelo.paybackcodingchallenge.feature_image.domain.repository.ImagesRepository
import javax.inject.Inject

@HiltViewModel
class ImageDetailViewModel @Inject constructor(
    private val repository: ImagesRepository
) : ViewModel() {
    fun getImageDetail(id: Int): Flow<Hit>  {
            return repository.getImageDetail(id)
    }
}