package cn.neday.sheep.network.api

import cn.neday.sheep.model.FlickrImageList
import retrofit2.http.GET
import retrofit2.http.Path

interface FlickrApi {

    @GET("https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=3e7cc266ae2b0e0d78e279ce8e361736&format=json&nojsoncallback=1&safe_search=1&text={keyword}")
    suspend fun getFlickrImageByKeyword(@Path("keyword") keyword: String): FlickrImageList
}