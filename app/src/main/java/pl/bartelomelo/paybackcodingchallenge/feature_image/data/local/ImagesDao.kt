package pl.bartelomelo.paybackcodingchallenge.feature_image.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pl.bartelomelo.paybackcodingchallenge.feature_image.data.local.entity.ImageEntity
import pl.bartelomelo.paybackcodingchallenge.feature_image.domain.model.Hit

@Dao
interface ImagesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImages(hits: List<ImageEntity>)

    @Query("DELETE FROM imageentity WHERE `query` IN(:hits)")
    suspend fun deleteImages(hits: List<String>)

    @Query("SELECT * FROM imageentity WHERE `query` = :query")
    suspend fun getImagesList(query: String): List<Hit>

    @Query ("SELECT * FROM imageentity WHERE id = :id")
    fun getImageDetail(id: Int): Flow<Hit>
}