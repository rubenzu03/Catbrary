package cat.rubenzu03.catbrary.api

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class CatApiRequest(context: Context) {

    private val requestQueue: RequestQueue = Volley.newRequestQueue(context)


}