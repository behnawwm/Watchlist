package ir.behnawwm.watchlist.features.presentation.main.movie_list

import ir.behnawwm.watchlist.core.exception.Failure

class MovieFailure {
    class ListNotAvailable : Failure.FeatureFailure()
    class NonExistentMovie : Failure.FeatureFailure()
}

