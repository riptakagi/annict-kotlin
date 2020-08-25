package jp.annict.lib.impl.v1.services

import com.google.gson.reflect.TypeToken
import jp.annict.lib.interfaces.IAnnictClient
import jp.annict.lib.interfaces.services.PeopleGetRequestQuery
import jp.annict.lib.interfaces.services.PeopleGetResponseData
import jp.annict.lib.interfaces.services.PeopleService
import jp.annict.lib.utils.JsonUtil
import jp.annict.lib.impl.v1.enums.Order
import jp.annict.lib.impl.v1.models.Person
import okhttp3.HttpUrl
import okhttp3.Request
import okhttp3.Response

data class PeopleGetRequestQueryImpl (
    val fields           : Array<String>?=null,
    val filter_ids       : Array<Long>?=null,
    val filter_work_id   : Long?=null,
    val page             : Long?=null,
    val per_page         : Long?=null,
    val sort_id          : Order?=null) : PeopleGetRequestQuery {

    override fun url(builder: HttpUrl.Builder): HttpUrl {
        return builder.apply {
            addPathSegment("people")

            if(fields != null && fields.isNotEmpty()) { addQueryParameter("fields", fields.joinToString(separator = ",")) }
            if(filter_ids != null && filter_ids.isNotEmpty()) { addQueryParameter("filter_ids", filter_ids.joinToString(separator = ",")) }
            if(filter_work_id != null) { addQueryParameter("filter_work_id", filter_work_id.toString()) }
            if(page != null) { addQueryParameter("page", page.toString()) }
            if(per_page != null) { addQueryParameter("per_page", per_page.toString()) }
            if(sort_id != null) { addQueryParameter("sort_id", sort_id.toString()) }

        }.build()
    }
}

data class PeopleGetResponseDataImpl (
    val people: Array<Person>?,
    val total_count: Long?,
    val next_page: Long?,
    val prev_page: Long?
) : PeopleGetResponseData {

    constructor() : this(null, null, null, null)

    override fun toDataClass(response: Response): PeopleGetResponseData {
        response.apply { JsonUtil.JSON_PARSER.parse(body?.string()).asJsonObject.apply { return PeopleGetResponseDataImpl (
            JsonUtil.GSON.fromJson(getAsJsonArray("people"), object : TypeToken<Array<Person>>() {}.type),
            if (get("total_count").isJsonNull) null else get("total_count").asLong,
            if (get("next_page").isJsonNull) null else get("next_page").asLong,
            if (get("prev_page").isJsonNull) null else get("prev_page").asLong
        ) }
        }
    }
}

class PeopleServiceImpl(val client: IAnnictClient) : PeopleService {

    override fun get(query: PeopleGetRequestQuery) : PeopleGetResponseData {
        this.client.apply {
            println(request(Request.Builder().url(query.url(getUrlBuilder()))).body?.string())
            return PeopleGetResponseDataImpl().toDataClass(request(Request.Builder().url(query.url(getUrlBuilder()))))
        }
    }

}