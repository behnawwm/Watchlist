package ir.behnawwm.watchlist.features.presentation.main.person_details

data class PersonDetailsView(
    val id: Int,
    val name: String,
    val alsoKnownAs: List<String>,
    val biography: String,
    val birthday: String,
    val deathday: String?,
    val gender: Int,
    val popularity: Double,
    val profilePath: String

)