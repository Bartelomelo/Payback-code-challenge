package pl.bartelomelo.paybackcodingchallenge.data.remote.repository

import dagger.hilt.android.scopes.ActivityScoped
import pl.bartelomelo.paybackcodingchallenge.data.remote.ImagesApi
import javax.inject.Inject

@ActivityScoped
class ImageRepository @Inject constructor(
    private val api: ImagesApi
) {

}