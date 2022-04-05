package ir.behnawwm.watchlist.features.data.remote.dto.person_details


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import androidx.annotation.Keep
import ir.behnawwm.watchlist.features.presentation.main.person_details.PersonDetailsView

@Keep
@JsonClass(generateAdapter = true)
data class PersonDetails(
    @Json(name = "adult")
    val adult: Boolean?,
    @Json(name = "also_known_as")
    val alsoKnownAs: List<String>?,
    @Json(name = "biography")
    val biography: String?,
    @Json(name = "birthday")
    val birthday: String?,
    @Json(name = "deathday")
    val deathday: String?,
    @Json(name = "gender")
    val gender: Int?,
    @Json(name = "homepage")
    val homepage: String?,
    @Json(name = "id")
    val id: Int?,
    @Json(name = "imdb_id")
    val imdbId: String?,
    @Json(name = "known_for_department")
    val knownForDepartment: String?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "place_of_birth")
    val placeOfBirth: String?,
    @Json(name = "popularity")
    val popularity: Double?,
    @Json(name = "profile_path")
    val profilePath: String?
) {
    fun toPersonDetailsView() = PersonDetailsView(
        id!!,
        name ?: "No Name!",
        alsoKnownAs.orEmpty(),
        biography.orEmpty(),
        birthday.orEmpty(),
        deathday,
        gender ?: -1,
        popularity ?: 0.0,
        profilePath.orEmpty()
    )

    companion object {
        val empty = PersonDetails(false, emptyList(), "", "", "", 0, "", 0, "", "", "", "", 0.0, "")
    }
}