package pl.bartelomelo.paybackcodingchallenge.screens.imagedetailscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import pl.bartelomelo.paybackcodingchallenge.R
import pl.bartelomelo.paybackcodingchallenge.data.remote.responses.SearchResponse
import pl.bartelomelo.paybackcodingchallenge.util.Resource

@Composable
fun ImageDetailScreen(
    viewModel: ImageDetailViewModel = hiltViewModel(),
    imageId: String,
    navController: NavController
) {
    val imageInfo = produceState<Resource<SearchResponse>>(initialValue = Resource.Loading()) {
        value = viewModel.getImageInfo(imageId)
    }.value
    Column {
        ImageDetailTopSection(navController = navController)
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            ImageDetailStateWrapper(
                imageInfo = imageInfo,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.75f)
                    .padding(
                        top = 25.dp,
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp
                    )
                    .shadow(10.dp, RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp)
                    .align(Alignment.TopCenter),
                loadingModifier = Modifier
                    .size(100.dp)
                    .align(Alignment.Center)
                    .padding(
                        top = 25.dp,
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp
                    )
            )
        }
    }
}

@Composable
fun ImageDetailTopSection(
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.primary,
                        Color.Transparent
                    )
                )
            )
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .size(36.dp)
                .offset(16.dp, 16.dp)
                .clickable {
                    navController.popBackStack()
                }
        )
    }
}

@Composable
fun ImageDetailStateWrapper(
    imageInfo: Resource<SearchResponse>,
    modifier: Modifier = Modifier,
    loadingModifier: Modifier = Modifier
) {
    when (imageInfo) {
        is Resource.Success -> {
            ImageDetailSection(
                imageInfo = imageInfo,
                modifier = modifier
            )
        }

        is Resource.Error -> {
            Text(
                text = imageInfo.message!!,
                color = Color.Red,
                modifier = modifier
            )
        }

        is Resource.Loading -> {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                modifier = loadingModifier
            )
        }
    }
}

@Composable
fun ImageDetailSection(
    imageInfo: Resource<SearchResponse>,
    modifier: Modifier = Modifier
) {
    val hit = imageInfo.data!!.hits[0]
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
    ) {
        val imageWeight = if (hit.imageHeight > hit.imageWidth) 1.8f else 1.2f
        Box(
            modifier = Modifier
                .weight(imageWeight)
        ) {
            SubcomposeAsyncImage(
                model = hit.largeImageURL,
                contentDescription = "image",
                loading = { CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(
                            top = 100.dp,
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 16.dp
                        ),
                    color = MaterialTheme.colorScheme.primary
                ) }
            )
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(top = 16.dp)
        ) {
            Column {
                ImageDetailsRow(
                    info = hit.user,
                    iconVector = Icons.Default.AccountCircle,
                    iconDrawable = null
                )
                ImageDetailsRow(
                    info = hit.tags,
                    iconVector = null,
                    iconDrawable = R.drawable.baseline_tag_24
                )
                ImageDetailsRow(
                    info = hit.likes.toString(),
                    iconVector = Icons.Default.Favorite,
                    iconDrawable = null
                )
                ImageDetailsRow(
                    info = hit.comments.toString(),
                    iconVector = null,
                    iconDrawable = R.drawable.baseline_comment_24
                )
                ImageDetailsRow(
                    info = hit.downloads.toString(),
                    iconVector = null,
                    iconDrawable = R.drawable.baseline_file_download_done_24
                )
            }

        }
    }
}

@Composable
fun ImageDetailsRow(
    info: String,
    iconVector: ImageVector?,
    iconDrawable: Int?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 5.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        if (iconVector == null && iconDrawable != null) {
            Icon(
                painter = painterResource(id = iconDrawable),
                contentDescription = "Painter resource",
                tint = Color.DarkGray
            )
        } else {
            Icon(
                imageVector = iconVector!!,
                contentDescription = "ImageVector",
                tint = Color.DarkGray
            )
        }
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = info,
            color = Color.DarkGray,
            textAlign = TextAlign.End
        )
    }
}

