package pl.bartelomelo.paybackcodingchallenge.feature_image.presentation.imagedetailscreen

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import pl.bartelomelo.paybackcodingchallenge.feature_image.domain.model.Hit

@Composable
fun ImageDetailScreen(
    viewModel: ImageDetailViewModel = hiltViewModel(),
    imageId: Int,
    navController: NavController
) {
    LaunchedEffect(key1 = null) {
        viewModel.getImageDetail(id = imageId)
    }
    val imageInfo by viewModel.imageDetail.collectAsState()
    Column {
        ImageDetailTopSection(navController = navController)
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            imageInfo?.let {
                ImageDetailStateWrapper(
                    imageInfo = imageInfo!!,
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
                        .align(Alignment.TopCenter)
                )
            }
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
    imageInfo: Hit,
    modifier: Modifier = Modifier,
) {
    ImageDetailSection(
        imageInfo = imageInfo,
        modifier = modifier
    )
}


@Composable
fun ImageDetailSection(
    imageInfo: Hit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
    ) {
        val imageWeight = if (imageInfo.imageHeight > imageInfo.imageWidth) 1.8f else 1.2f
        Box(
            modifier = Modifier
                .weight(imageWeight)
        ) {
            SubcomposeAsyncImage(
                model = imageInfo.largeImageURL,
                contentDescription = "image",
                loading = {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(
                                top = 100.dp,
                                start = 16.dp,
                                end = 16.dp,
                                bottom = 16.dp
                            ),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            )
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(top = 16.dp)
        ) {
            Column {
                ImageDetailsRow(
                    info = imageInfo.user,
                    iconVector = Icons.Default.AccountCircle,
                    iconDrawable = null
                )
                ImageDetailsRow(
                    info = imageInfo.tags,
                    iconVector = null,
                    iconDrawable = R.drawable.baseline_tag_24
                )
                ImageDetailsRow(
                    info = imageInfo.likes.toString(),
                    iconVector = Icons.Default.Favorite,
                    iconDrawable = null
                )
                ImageDetailsRow(
                    info = imageInfo.comments.toString(),
                    iconVector = null,
                    iconDrawable = R.drawable.baseline_comment_24
                )
                ImageDetailsRow(
                    info = imageInfo.downloads.toString(),
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

