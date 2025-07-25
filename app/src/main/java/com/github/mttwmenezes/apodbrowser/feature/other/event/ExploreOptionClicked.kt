package com.github.mttwmenezes.apodbrowser.feature.other.event

sealed class ExploreOptionClicked {
    data object RandomPick : ExploreOptionClicked()
    data object Calendar : ExploreOptionClicked()
}
