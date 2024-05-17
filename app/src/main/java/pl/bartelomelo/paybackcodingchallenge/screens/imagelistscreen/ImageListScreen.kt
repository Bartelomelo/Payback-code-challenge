package pl.bartelomelo.paybackcodingchallenge.screens.imagelistscreen

import android.util.Log
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ImageListScreen(
    viewModel: ImageListViewModel = hiltViewModel()
) {
    val imageList by remember {
        viewModel.imageList
    }
    LazyColumn {
        Log.d("imageList size", imageList?.hits?.size.toString())
        imageList?.hits?.let { hits ->
            items(hits.size) {
                Text(text = imageList?.hits!![it].user)
                Spacer(modifier = Modifier.padding(1.dp).fillMaxWidth().height(2.dp))
            }
        }
    }
}