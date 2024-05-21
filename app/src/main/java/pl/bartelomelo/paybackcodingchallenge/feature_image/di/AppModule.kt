package pl.bartelomelo.paybackcodingchallenge.feature_image.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.bartelomelo.paybackcodingchallenge.feature_image.data.local.ImagesDatabase
import pl.bartelomelo.paybackcodingchallenge.feature_image.data.remote.ImagesApi
import pl.bartelomelo.paybackcodingchallenge.feature_image.data.remote.repository.ImageRepositoryImpl
import pl.bartelomelo.paybackcodingchallenge.feature_image.domain.repository.ImagesRepository
import pl.bartelomelo.paybackcodingchallenge.util.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideImageApi(): ImagesApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ImagesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideImagesRepository(
        db: ImagesDatabase,
        api: ImagesApi
    ): ImagesRepository {
        return ImageRepositoryImpl(api, db.dao)
    }

    @Provides
    @Singleton
    fun provideImagesDatabase(app: Application): ImagesDatabase {
        return Room.databaseBuilder(
            app, ImagesDatabase::class.java, "images_db"
        ).build()
    }
}