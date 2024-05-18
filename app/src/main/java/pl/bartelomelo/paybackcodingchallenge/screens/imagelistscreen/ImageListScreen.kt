package pl.bartelomelo.paybackcodingchallenge.screens.imagelistscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import pl.bartelomelo.paybackcodingchallenge.R
import pl.bartelomelo.paybackcodingchallenge.data.remote.responses.Hit

@Composable
fun ImageListScreen(
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
        ) {
            ImageListTopSection()
        }
        Box(
            modifier = Modifier
                .weight(10f)
        ) {
            ImageListSection()
        }
        Box(
            modifier = Modifier
                .weight(1f)
        ) {
            ImageListButtonsSection()
        }
    }
}

@Composable
fun ImageListTopSection(
    viewModel: ImageListViewModel = hiltViewModel()
) {
    val query = viewModel.query
    Row {
        OutlinedTextField(
            value = query.value,
            onValueChange = { query.value = it },
            shape = RoundedCornerShape(20.dp),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_search_24),
                    contentDescription = "search icon"
                )
            }
        )
        Button(onClick = {
            viewModel.saveSearchQuery(query.value)
            viewModel.resetPage()
            viewModel.getImageList(query.value)
        }) {
            Text(text = "search")
        }
    }
}

@Composable
fun ImageListSection(
    viewModel: ImageListViewModel = hiltViewModel()
) {
    val imageList = viewModel.imageList
    LazyColumn {
        items(imageList.value.hits.size) {
            ImageListEntry(hit = imageList.value.hits[it])
        }
    }
}

@Composable
fun ImageListEntry(hit: Hit) {
    Box(
        contentAlignment = Center,
        modifier = Modifier
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(1f)
    ) {
        Column {
            Text(
                text = hit.user,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = hit.tags,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun ImageListButtonsSection(
    viewModel: ImageListViewModel = hiltViewModel()
) {
    val searchedQuery = viewModel.searchedQuery
    Row {
        Box(
            modifier = Modifier
                .weight(1f),
            contentAlignment = Alignment.CenterStart
        ) {
            Button(
                onClick = {
                    viewModel.decrementPage()
                    viewModel.getImageList(searchedQuery.value)
                },
                colors = ButtonDefaults.buttonColors()
            ) {
                Text(text = "<<")
            }
        }
        Box(
            modifier = Modifier
                .weight(1f),
            contentAlignment = Alignment.CenterEnd
        ) {
            Button(onClick = {
                viewModel.incrementPage()
                viewModel.getImageList(searchedQuery.value)
            }) {
                Text(text = ">>")
            }
        }
    }
}