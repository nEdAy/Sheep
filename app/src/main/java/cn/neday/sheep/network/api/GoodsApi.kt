package cn.neday.sheep.network.api

import cn.neday.base.model.Pages
import cn.neday.base.model.Response
import cn.neday.sheep.model.CommonGoods
import cn.neday.sheep.model.RankingGoods
import retrofit2.http.GET
import retrofit2.http.Query

interface GoodsApi {

    /**
     * 各大榜单
     * 实时销量榜等特色榜单。该接口包含大淘客平台的实时销量榜全天销量榜及热推榜，接口实时更新，推荐最新的榜单商品
     *
     * @param rankType 榜单类型 是 Number 1.实时销量榜 2.全天销量榜 3.热推榜
     * @param cid 大淘客一级类目id 否 Number 实时销量榜和全天销量榜支持传入分类返回分类榜数据
     * @return 返回参数
     */
    @GET("goods/get-ranking-list")
    suspend fun rankingList(@Query("rankType") rankType: Int, @Query("cid") cid: String): Response<List<RankingGoods>>

    /**
     * 9.9精选
     * 大淘客专业选品团队提供的9.9精选商品，提供最优质的白菜商品列表，可组建9.9商品专区，提供丰富的选品体验
     *
     * @param pageSize 每页条数	是	Number	默认100 ，可选范围：10,50,100,200，如果小于10按10处理，大于200按照200处理，其它非范围内数字按100处理
     * @param pageId 分页id	是	String	默认为1，支持传统的页码分页方式和scroll_id分页方式，根据用户自身需求传入值。示例1：商品入库，则首次传入1，后续传入接口返回的pageId，接口将持续返回符合条件的完整商品列表，该方式可以避免入口商品重复；示例2：根据pageSize和totalNum计算出总页数，按照需求返回指定页的商品（该方式可能在临近页取到重复商品）
     * @param cid 一级类目Id	是	String	大淘客的一级分类id，如果需要传多个，以英文逗号相隔，如：”1,2,3”。一级分类id请求详情：-1-精选，1 -居家百货，2 -美食，3 -服饰，4 -配饰，5 -美妆，6 -内衣，7 -母婴，8 -箱包，9 -数码配件，10 -文娱车品
     * @return 返回参数
     */
    @GET("goods/nine/op-goods-list")
    suspend fun nineOpGoodsList(@Query("pageSize") pageSize: Int, @Query("pageId") pageId: String, @Query("cid") cid: String): Response<Pages<CommonGoods>>

    /**
     * 猜你喜欢
     * 根据当前商品推荐大淘客商品库的相关优质商品，可用在CMS/APP的相关商品推荐页面，向用户提供更丰富的商品选择
     *
     * @param id    大淘客的商品id	是	Number
     * @param size    每页条数	否	Number	默认10 ， 最大值100
     * @return 返回参数
     */
    @GET("goods/list-similer-goods-by-open")
    suspend fun listSimilerGoodsByOpen(@Query("id") id: Int, @Query("size") size: Int): Response<List<CommonGoods>>

    /**
     * 超级搜索
     * 通过关键字进行搜索，提供大淘客商品库的精准搜索结果及联盟的相关商品结果，为您带来优质的搜索体验，可用在CMS/APP的搜索引擎
     *
     * @param type    搜索类型	否	Number	0-综合结果，1-大淘客商品，2-联盟商品
     * @param keyWords    关键词搜索	是	String
     * @param tmall    是否天猫商品	否	Number	1-天猫商品，0-所有商品，不填默认为0
     * @param haitao    是否海淘商品	否	Number	1-海淘商品，0-所有商品，不填默认为0
     * @param sort    排序字段	否	String	排序字段信息 销量（total_sales） 价格（price），排序_des（降序），排序_asc（升序）
     *
     * @return 返回参数
     */
    @GET("goods/list-super-goods")
    suspend fun listSuperGoods(
        @Query("type") type: Int, @Query("keyWords") keyWords: String,
        @Query("tmall") tmall: Int, @Query("haitao") haitao: Int, @Query("sort") sort: String
    ): Response<List<CommonGoods>>


    /**
     * 大淘客搜索
     * 根据关键字、筛选条件，返回符合条件的大淘客平台的商品信息。
     *
     * @param pageSize 每页条数	是	Number	默认100 ，可选范围：10,50,100,200，如果小于10按10处理，大于200按照200处理，其它非范围内数字按100处理
     * @param pageId 分页id	是	String	默认为1，支持传统的页码分页方式和scroll_id分页方式，根据用户自身需求传入值。示例1：商品入库，则首次传入1，后续传入接口返回的pageId，接口将持续返回符合条件的完整商品列表，该方式可以避免入口商品重复；示例2：根据pageSize和totalNum计算出总页数，按照需求返回指定页的商品（该方式可能在临近页取到重复商品）
     * @param keyWords    关键词搜索	是	String
     *
     * @return 返回参数
     */
    @GET("goods/get-dtk-search-goods")
    suspend fun getDtkSearchGoods(
        @Query("pageSize") pageSize: Int, @Query("pageId") pageId: String, @Query("keyWords") keyWords: String
    ): Response<Pages<CommonGoods>>

}
