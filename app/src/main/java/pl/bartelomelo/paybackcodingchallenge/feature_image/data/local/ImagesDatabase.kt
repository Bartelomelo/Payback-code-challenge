package pl.bartelomelo.paybackcodingchallenge.feature_image.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import pl.bartelomelo.paybackcodingchallenge.feature_image.data.local.entity.ImageEntity

@Database(
    entities = [ImageEntity::class],
    version = 1
)
abstract class ImagesDatabase: RoomDatabase() {
    abstract val dao: ImagesDao
}