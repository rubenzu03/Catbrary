package cat.rubenzu03.catbrary.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import cat.rubenzu03.catbrary.R
import cat.rubenzu03.catbrary.ui.components.CatBreedListScreen
import cat.rubenzu03.catbrary.ui.components.ErrorState
import cat.rubenzu03.catbrary.ui.components.LoadingState
import cat.rubenzu03.catbrary.ui.theme.Spacing
import cat.rubenzu03.catbrary.ui.viewmodel.CatBreedListViewModel

private sealed interface BreedContent {
    data object Loading : BreedContent
    data object Content : BreedContent
    data class Error(val message: String) : BreedContent
}

@Composable
fun BreedsScreen(modifier: Modifier = Modifier) {
    val breedViewModel: CatBreedListViewModel = viewModel()
    val breeds by breedViewModel.breeds.collectAsState()
    val loading by breedViewModel.loading.collectAsState()
    val error by breedViewModel.error.collectAsState()

    LaunchedEffect(Unit) { breedViewModel.fetchBreeds() }

    val target = when {
        loading && breeds.isEmpty() -> BreedContent.Loading
        error != null && breeds.isEmpty() -> BreedContent.Error(error.orEmpty())
        else -> BreedContent.Content
    }

    val genericErrorBody = stringResource(R.string.breeds_error_body)

    val motionScheme = MaterialTheme.motionScheme
    val transitionSpec = remember(motionScheme) {
        (fadeIn(motionScheme.fastEffectsSpec()) +
            scaleIn(initialScale = 0.92f, animationSpec = motionScheme.fastSpatialSpec()))
            .togetherWith(fadeOut(motionScheme.fastEffectsSpec()))
    }

    AnimatedContent(
        targetState = target,
        modifier = modifier,
        transitionSpec = { transitionSpec },
        label = "breedContent",
    ) { state ->
        when (state) {
            BreedContent.Loading -> LoadingState(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(Spacing.m),
            )

            is BreedContent.Error -> ErrorState(

                icon = Icons.Filled.Info,
                titleRes = R.string.breeds_error_title,
                body = state.message.ifBlank { genericErrorBody },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(Spacing.m),
                actionLabelRes = R.string.retry,
                onAction = { breedViewModel.fetchBreeds() },
            )

            BreedContent.Content -> CatBreedListScreen(breeds = breeds, modifier = Modifier.fillMaxSize())
        }
    }
}
