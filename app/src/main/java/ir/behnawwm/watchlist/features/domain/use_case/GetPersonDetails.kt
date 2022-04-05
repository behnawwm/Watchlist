package ir.behnawwm.watchlist.features.domain.use_case

import ir.behnawwm.watchlist.core.interactor.UseCase
import ir.behnawwm.watchlist.features.data.remote.dto.movie_details.MovieDetails
import ir.behnawwm.watchlist.features.data.remote.dto.person_details.PersonDetails

import ir.behnawwm.watchlist.features.domain.repository.MoviesRepository
import javax.inject.Inject

class GetPersonDetails
@Inject constructor(private val moviesRepository: MoviesRepository) :
    UseCase<PersonDetails, GetPersonDetails.Params>() {

    override suspend fun run(params: Params) = moviesRepository.personDetails(params.id)

    data class Params(val id: Int)
}
