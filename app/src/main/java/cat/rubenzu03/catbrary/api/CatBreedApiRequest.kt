package cat.rubenzu03.catbrary.api

import android.content.Context
import cat.rubenzu03.catbrary.domain.CatBreedInfo
import coil.compose.AsyncImagePainter
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

class CatBreedApiRequest(context: Context) {

    private val requestQueue: RequestQueue = Volley.newRequestQueue(context)

    fun fetchAllCatBreedsInfo(
        url: String,
        onSuccess: (List<CatBreedInfo>) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        val request = JsonArrayRequest(
            com.android.volley.Request.Method.GET,
            url,
            null,
            { response ->
                val catBreeds = mutableListOf<CatBreedInfo>()
                for (i in 0 until response.length()) {
                    val jsonObj = response.getJSONObject(i)
                    val catBreed = CatBreedInfo().apply {
                        id = jsonObj.optString("id")
                        name = jsonObj.optString("name")
                        temperament = jsonObj.optString("temperament")
                        origin = jsonObj.optString("origin")
                        description = jsonObj.optString("description")
                        indoor = jsonObj.optInt("indoor")
                        adaptability = jsonObj.optInt("adaptability")
                        affectionLevel = jsonObj.optInt("affection_level")
                        childFriendly = jsonObj.optInt("child_friendly")
                        dogFriendly = jsonObj.optInt("dog_friendly")
                        energyLevel = jsonObj.optInt("energy_level")
                        grooming = jsonObj.optInt("grooming")
                        healthIssues = jsonObj.optInt("health_issues")
                        intelligence = jsonObj.optInt("intelligence")
                        sheddingLevel = jsonObj.optInt("shedding_level")
                        socialNeeds = jsonObj.optInt("social_needs")
                        strangerFriendly = jsonObj.optInt("stranger_friendly")
                        wikipediaUrl = jsonObj.optString("wikipedia_url")
                        ref_imageId = jsonObj.optString("reference_image_id")
                    }
                    catBreeds.add(catBreed)
                }
                onSuccess(catBreeds)
            },
            { error -> onError(error) }
        )
        requestQueue.add(request)
    }

    fun fetchImageUrl(refImageId: String, onSuccess: (String) -> Unit, onError: (VolleyError) -> Unit) {
        if (refImageId.isBlank()) {
            onSuccess("")
            return
        }
        val url = "https://api.thecatapi.com/v1/images/$refImageId"
        val request = JsonObjectRequest(
            com.android.volley.Request.Method.GET,
            url,
            null,
            { response ->
                val imageUrl = response.optString("url", "")
                onSuccess(imageUrl)
            },
            { error -> onError(error) }
        )
        requestQueue.add(request)
    }
}