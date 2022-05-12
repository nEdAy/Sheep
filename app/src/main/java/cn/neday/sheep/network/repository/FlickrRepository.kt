package cn.neday.sheep.network.repository

import cn.neday.sheep.model.FlickrImageList
import cn.neday.sheep.network.api.FlickrApi

/**
 * Flickr Repository
 *
 * @author nEdAy
 */
class FlickrRepository(private val flickrApi: FlickrApi) {

    suspend fun getFlickrImageByKeyword(keyword: String): FlickrImageList =
        flickrApi.getFlickrImageByKeyword(keyword)
}