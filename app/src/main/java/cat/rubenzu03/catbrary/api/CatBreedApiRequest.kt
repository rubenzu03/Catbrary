package cat.rubenzu03.catbrary.api

import android.content.Context
import cat.rubenzu03.catbrary.BuildConfig
import cat.rubenzu03.catbrary.domain.CatBreedInfo
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley

private fun org.json.JSONObject.optRating(key: String): Int = runCatching {
    this.optString(key).trim().toIntOrNull() ?: this.optInt(key)
}.getOrDefault(0)

class CatBreedApiRequest(context: Context) {

    private val requestQueue: RequestQueue = Volley.newRequestQueue(context)

    private fun headers(): MutableMap<String, String> =
        if (BuildConfig.API_KEY.isNotBlank()) {
            mutableMapOf("x-api-key" to BuildConfig.API_KEY)
        } else {
            mutableMapOf()
        }

    fun fetchAllCatBreedsInfo(
        url: String,
        onSuccess: (List<CatBreedInfo>) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        val request = object : JsonArrayRequest(
            Method.GET,
            url,
            null,
            { response ->
                val catBreeds = mutableListOf<CatBreedInfo>()
                for (i in 0 until response.length()) {
                    val jsonObj = response.getJSONObject(i)
                    val breedId = jsonObj.optString("id")
                    val historicalRatings = HISTORICAL_BREED_RATINGS[breedId]
                    val catBreed = CatBreedInfo(
                        id = breedId,
                        name = jsonObj.optString("name"),
                        temperament = jsonObj.optString("temperament"),
                        origin = jsonObj.optString("origin"),
                        description = jsonObj.optString("description"),
                        indoor = jsonObj.optInt("indoor"),
                        adaptability = historicalRatings?.adaptability ?: jsonObj.optRating("adaptability"),
                        affectionLevel = historicalRatings?.affectionLevel ?: jsonObj.optRating("affection_level"),
                        childFriendly = historicalRatings?.childFriendly ?: jsonObj.optRating("child_friendly"),
                        dogFriendly = historicalRatings?.dogFriendly ?: jsonObj.optRating("dog_friendly"),
                        energyLevel = historicalRatings?.energyLevel ?: jsonObj.optRating("energy_level"),
                        grooming = historicalRatings?.grooming ?: jsonObj.optRating("grooming"),
                        healthIssues = historicalRatings?.healthIssues ?: jsonObj.optRating("health_issues"),
                        intelligence = historicalRatings?.intelligence ?: jsonObj.optRating("intelligence"),
                        sheddingLevel = historicalRatings?.sheddingLevel ?: jsonObj.optRating("shedding_level"),
                        socialNeeds = historicalRatings?.socialNeeds ?: jsonObj.optRating("social_needs"),
                        strangerFriendly = historicalRatings?.strangerFriendly ?: jsonObj.optRating("stranger_friendly"),
                        wikipediaUrl = jsonObj.optString("wikipedia_url"),
                        refImageid = jsonObj.optString("reference_image_id"),
                        imageUrl = ""
                    )
                    catBreeds.add(catBreed)
                }
                onSuccess(catBreeds)
            },
            { error -> onError(error) }
        ) {
            override fun getHeaders(): MutableMap<String, String> = headers()
        }
        requestQueue.add(request)
    }

    fun fetchImageUrl(refImageId: String, onSuccess: (String) -> Unit, onError: (VolleyError) -> Unit) {
        if (refImageId.isBlank()) {
            onSuccess("")
            return
        }
        val url = "https://api.thecatapi.com/v1/images/$refImageId"
        val request = object : JsonObjectRequest(
            Method.GET,
            url,
            null,
            { response ->
                val imageUrl = response.optString("url", "")
                onSuccess(imageUrl)
            },
            { error -> onError(error) }
        ) {
            override fun getHeaders(): MutableMap<String, String> = headers()
        }
        requestQueue.add(request)
    }
}