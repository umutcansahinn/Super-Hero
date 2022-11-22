@file:OptIn(ExperimentalAnimationApi::class)

package com.umutcansahin.superhero

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring.DampingRatioLowBouncy
import androidx.compose.animation.core.Spring.StiffnessLow
import androidx.compose.animation.core.Spring.StiffnessVeryLow
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.umutcansahin.superhero.model.Hero
import com.umutcansahin.superhero.model.HeroesRepository
import com.umutcansahin.superhero.ui.theme.SuperHeroTheme


@Composable
fun HeroApp() {

    val visibleState = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }

    AnimatedVisibility(
        visibleState = visibleState,
        enter = fadeIn(
            animationSpec = spring(dampingRatio = DampingRatioLowBouncy)
        ),
        exit = fadeOut()
    ){
        LazyColumn(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .animateEnterExit(
                    enter = slideInVertically(
                        animationSpec = spring(
                            stiffness = StiffnessVeryLow,
                            dampingRatio = DampingRatioLowBouncy
                        )
                    )
                )
        ) {
            items(HeroesRepository.heroes) {
                HeroListItem(hero = it)
            }
        }
    }
}

@Composable
fun HeroListItem(hero: Hero) {
    Card(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .sizeIn(minHeight = 72.dp)
        ) {
            Box(modifier = Modifier.weight(3f)) {
                HeroInformation(heroName = hero.nameRes, heroDescription = hero.descriptionRes)
            }
            Spacer(
                modifier = Modifier
                    .width(16.dp)
            )
            Box(modifier = Modifier.weight(1f)) {
                HeroIcon(heroIcon = hero.imageRes)
            }
        }
    }
}

@Composable
fun HeroInformation(
    @StringRes heroName: Int,
    @StringRes heroDescription: Int
) {

    Column(
        modifier = Modifier
    ) {
        Text(
            text = stringResource(id = heroName),
            style = MaterialTheme.typography.h3,
        )
        Text(
            text = stringResource(id = heroDescription),
            style = MaterialTheme.typography.body1,
        )
    }
}

@Composable
fun HeroIcon(
    @DrawableRes heroIcon: Int,
) {
        Image(
            painter = painterResource(id = heroIcon),
            contentDescription = null,
            alignment = Alignment.TopCenter,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .size(72.dp)
        )
    }



@Preview("Heroes List")
@Composable
fun HeroesPreview() {
    SuperHeroTheme(darkTheme = false) {
        Surface(
            color = MaterialTheme.colors.background
        ) {
            HeroApp()
        }
    }
}
@Preview
@Composable
fun DarkThemePreview() {
    SuperHeroTheme(darkTheme = true) {
        HeroApp()
    }
}
@Preview("Only One Hero")
@Composable
fun HeroPreview() {
    val hero = Hero(R.string.hero1,R.string.description1,R.drawable.android_superhero1)
    SuperHeroTheme {
        HeroListItem(hero = hero)
    }
}
