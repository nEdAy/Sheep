package cn.neday.sheep.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cn.neday.base.network.requestAsync
import cn.neday.base.network.then
import cn.neday.base.viewmodel.BaseViewModel
import cn.neday.sheep.model.Photo
import cn.neday.sheep.network.repository.FlickrRepository

/**
 * RankingListViewModel
 *
 * @author nEdAy
 */
class SearchFlickrListViewModel(private val repository: FlickrRepository) : BaseViewModel() {

    val photoList: MutableLiveData<List<Photo>> = MutableLiveData()

    fun getFlickrImageByKeyword(keyword: String = "") {
        requestAsync {
            repository.getFlickrImageByKeyword(keyword)
        }.then(viewModelScope, {
            photoList.value = it.photos.photo
        }, {
            errMsg.value = it
        }, {
            onComplete.call()
        })
    }
}