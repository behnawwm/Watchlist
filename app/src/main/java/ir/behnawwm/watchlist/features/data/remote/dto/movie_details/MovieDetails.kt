package ir.behnawwm.watchlist.features.data.remote.dto.movie_details

data class MovieDetails(
    val adult: Boolean,
    val backdrop_path: String,
    val belongs_to_collection: BelongsToCollection,
    val budget: Int,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    val imdb_id: String,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val production_companies: List<ProductionCompany>,
    val production_countries: List<ProductionCountry>,
    val release_date: String,
    val revenue: Int,
    val runtime: Int,
    val spoken_languages: List<SpokenLanguage>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
) {
    companion object {
        val empty = MovieDetails(
            false,
            "",
            BelongsToCollection("", 0, "", ""),
            0,
            emptyList(),
            "",
            0,
            "",
            "",
            "",
            "",
            0.0,
            "",
            emptyList(),
            emptyList(),
            "",
            0,
            0,
            emptyList(),
            "",
            "",
            "",
            false,
            0.0,
            0
        )
    }
}