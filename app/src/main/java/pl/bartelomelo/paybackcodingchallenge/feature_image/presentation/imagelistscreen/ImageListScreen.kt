package pl.bartelomelo.paybackcodingchallenge.feature_image.presentation.imagelistscreen

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import pl.bartelomelo.paybackcodingchallenge.R
import pl.bartelomelo.paybackcodingchallenge.feature_image.domain.model.Hit

@Composable
fun ImageListScreen(
    navController: NavController,
    viewModel: ImageListViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    val lazyState = rememberLazyListState()
    val snackBarHostState = remember { SnackbarHostState() }
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is ImageListViewModel.UIEvent.ShowSnackbar -> {
                    snackBarHostState.showSnackbar(message = event.message)
                }
            }
        }
    }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
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
                    .verticalScroll(scrollState)
            ) {
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(800.dp)
                    ) {
                        ImageListSection(
                            listState = lazyState,
                            navController = navController
                        )
                    }
                    Box(
                        modifier = Modifier
                            .height(50.dp)
                    ) {
                        ImageListButtonsSection(
                            scrollState = scrollState,
                            listState = lazyState
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageListTopSection(
    viewModel: ImageListViewModel = hiltViewModel(),

    ) {
    val query = viewModel.query
    val active = viewModel.searchBarActive
    Row {
        SearchBar(
            modifier = Modifier.fillMaxWidth(),
            colors = SearchBarDefaults.colors(
                containerColor = MaterialTheme.colorScheme.tertiary,
            ),
            query = query.value,
            onQueryChange = { query.value = it },
            onSearch = {
                viewModel.searchQuery(query.value)
                active.value = false
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_search_24),
                    contentDescription = "search icon"
                )
            },
            trailingIcon = {
                if (query.value.isNotEmpty()) {
                    IconButton(onClick = { query.value = "" }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            tint = MaterialTheme.colorScheme.onSurface,
                            contentDescription = "Clear search"
                        )
                    }
                }
            },
            active = active.value,
            onActiveChange = {
                active.value = it
            }
        ) {}
    }
}

@Composable
fun ImageListSection(
    viewModel: ImageListViewModel = hiltViewModel(),
    listState: LazyListState,
    navController: NavController
) {
    val imageList = viewModel.imageList
    LazyColumn(
        state = listState,
        modifier = Modifier
            .padding(top = 16.dp, start = 3.dp, end = 3.dp)
    ) {
        val imageListCount = if (imageList.value.hits.size % 2 == 0) {
            imageList.value.hits.size / 2
        } else {
            imageList.value.hits.size / 2 + 1
        }
        items(imageListCount) {
            ImageListRow(
                rowIndex = it,
                entries = imageList.value.hits,
                navController = navController
            )
        }
    }

}

@Composable
fun ImageListEntry(
    hit: Hit,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    var showDialog by remember { mutableStateOf(false) }
    Box(
        contentAlignment = TopCenter,
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .aspectRatio(1f)
            .background(MaterialTheme.colorScheme.secondary)
            .clickable {
                showDialog = showDialog.not()
            }
    ) {
        Column {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(hit.webformatURL)
                    .error(R.drawable.noimage)
                    .build(),
                contentDescription = hit.tags,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.7f),
                loading = {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            )
            Text(
                text = hit.user,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.tertiary
            )
            Text(
                text = hit.tags,
                fontSize = 10.sp,
                textAlign = TextAlign.Center,
                modifier = modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.tertiary
            )
        }
    }
    if (showDialog) {
        ImageAlertDialog(
            onDismissRequest = { showDialog = false },
            onConfirmation = { navController.navigate("image_detail_screen/${hit.id}") },
            dialogTitle = "Do you want to see more details",
            dialogText = "This action will take you to detail view.",
            Icons.Default.Info
        )
    }
}

@Composable
fun ImageListRow(
    rowIndex: Int,
    entries: List<Hit>,
    navController: NavController
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            ImageListEntry(
                hit = entries[rowIndex * 2],
                modifier = Modifier.weight(1f),
                navController = navController
            )
            Spacer(modifier = Modifier.width(16.dp))
            if (entries.size >= rowIndex * 2 + 2) {
                ImageListEntry(
                    hit = entries[rowIndex * 2 + 1],
                    modifier = Modifier.weight(1f),
                    navController = navController
                )
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun ImageListButtonsSection(
    viewModel: ImageListViewModel = hiltViewModel(),
    scrollState: ScrollState,
    listState: LazyListState
) {
    val searchedQuery = viewModel.searchedQuery
    val scope = rememberCoroutineScope()
    Row {
        Box(
            modifier = Modifier
                .weight(1f),
            contentAlignment = Alignment.CenterStart
        ) {
            Button(
                onClick = {
                    scope.launch {
                        scrollState.animateScrollTo(0)
                        listState.animateScrollToItem(0)
                    }
                    viewModel.loadPreviousPage(searchedQuery.value)
                },
                colors = ButtonDefaults.buttonColors()
            ) {
                Text(
                    text = "<<",
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        }
        Box(
            modifier = Modifier
                .weight(1f),
            contentAlignment = Alignment.CenterEnd
        ) {
            Button(onClick = {
                scope.launch {
                    scrollState.animateScrollTo(0)
                    listState.animateScrollToItem(0)
                }
                viewModel.loadNextPage(searchedQuery.value)
            }) {
                Text(
                    text = ">>",
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        }
    }
}

@Composable
fun ImageAlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Alert icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text(
                    "Confirm",
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(
                    "Dismiss",
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.primary,
        iconContentColor = MaterialTheme.colorScheme.tertiary,
        titleContentColor = MaterialTheme.colorScheme.tertiary,
        textContentColor = MaterialTheme.colorScheme.tertiary,
    )
}