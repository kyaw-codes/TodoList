package com.kyaw.todolist.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.exponentialDecay
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

enum class Drag {
    Normal,
    Swiped
}

@Stable
@Immutable
enum class SwipeDirection {
    Left,
    Right
}

enum class SwipeType {
    Slide,
    Expand
}

@ExperimentalFoundationApi
@Composable
fun HorizontalSwipeRevealLayout(
    swipeDistance: Dp = 100.dp,
    swipeDirection: SwipeDirection = SwipeDirection.Left,
    swipeType: SwipeType = SwipeType.Slide,
    revealedContent: @Composable BoxScope.(AnchoredDraggableState<Drag>) -> Unit,
    content: @Composable BoxScope.(AnchoredDraggableState<Drag>) -> Unit,
) {

    val density = LocalDensity.current
    val swipedOffset = when (swipeDirection) {
        SwipeDirection.Left -> swipeDistance.unaryMinus()
        SwipeDirection.Right -> swipeDistance
    }
    val anchors = androidx.compose.foundation.gestures.DraggableAnchors {
        Drag.Normal at with(density) { 0.dp.toPx() }
        Drag.Swiped at with(density) { swipedOffset.toPx() }
    }

    val state = remember {
        AnchoredDraggableState(
            initialValue = Drag.Normal,
            anchors = anchors,
            positionalThreshold = { distance: Float -> distance/4 },
            velocityThreshold = {  with(density) { (swipeDistance / 6).toPx() } },
            snapAnimationSpec = spring(Spring.DampingRatioLowBouncy, Spring.StiffnessLow),
            decayAnimationSpec = exponentialDecay()
        )
    }

    SideEffect {
        state.updateAnchors(anchors)
    }

    ConstraintLayout(
        modifier = Modifier.anchoredDraggable(state, Orientation.Horizontal)
    ) {

        // Revealed Layout and Foreground layouts are stacked over each other under constraint layout
        val (revealedLayout, foregroundLayout) = createRefs()

        // Revealed Layout
        Box(
            Modifier
                .constrainAs(revealedLayout) {

                    // Pre-computed width for different swipe types
                    val computedWidth = when (swipeType) {
                        SwipeType.Slide -> Dimension.wrapContent
                        SwipeType.Expand -> {
                            // Expand SwipeType specifically need constraint to parent end
                            end.linkTo(parent.end)
                            Dimension.value(
                                with(density) { state.offset.absoluteValue.toDp() }
                            )
                        }
                    }

                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(foregroundLayout.end, margin = 16.dp)

                    width = computedWidth
                    height = Dimension.fillToConstraints
                }
                .offset {
                    // Different Offsets for different Swipe Type
                    val xOffset = when (swipeType) {
                        SwipeType.Slide -> state.offset.roundToInt()
                        SwipeType.Expand -> state.offset.roundToInt() / 2
                    }
                    // Setting Offset will give the illusion of background layout popping up
                    IntOffset(xOffset, y = 0)
                }
        ) {
            revealedContent(state)
        }

        // Foreground Layout
        Box(
            Modifier
                .constrainAs(foregroundLayout) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.matchParent
                    height = Dimension.wrapContent
                }
                .offset {
                    // Setting Offset will give the illusion of swiping the layout
                    IntOffset(state.offset.roundToInt(), y = 0)
                }
        ) {
            content(state)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun rememberAnchorDragState(
    swipeDistance: Dp = 100.dp,
    swipeDirection: SwipeDirection = SwipeDirection.Left,
): AnchoredDraggableState<Drag> {
    val density = LocalDensity.current
    val swipedOffset = when (swipeDirection) {
        SwipeDirection.Left -> swipeDistance.unaryMinus()
        SwipeDirection.Right -> swipeDistance
    }
    val anchors = androidx.compose.foundation.gestures.DraggableAnchors {
        Drag.Normal at with(density) { 0.dp.toPx() }
        Drag.Swiped at with(density) { swipedOffset.toPx() }
    }

    return remember {
        AnchoredDraggableState(
            initialValue = Drag.Normal,
            anchors = anchors,
            positionalThreshold = { distance: Float -> distance/4 },
            velocityThreshold = {  with(density) { (swipeDistance / 6).toPx() } },
            snapAnimationSpec = spring(Spring.DampingRatioLowBouncy, Spring.StiffnessLow),
            decayAnimationSpec = exponentialDecay()
        )
    }
}