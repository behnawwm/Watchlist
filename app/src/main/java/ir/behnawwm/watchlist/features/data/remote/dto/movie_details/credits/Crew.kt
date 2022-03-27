package ir.behnawwm.watchlist.features.data.remote.dto.movie_details.credits

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Crew(
    val adult: Boolean?,

    @Json(name = "credit_id")
    val creditId: String = "",

    val department: String = "",

    val gender: Int?,

    val id: Int?,

    val job: String = "",

    @Json(name = "known_for_department")
    val knownForDepartment: String?,

    val name: String = "",

    @Json(name = "original_name")
    val originalName: String = "",

    val popularity: Double?,

    @Json(name = "profile_path")
    val profilePath: String?
)