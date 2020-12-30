package jp.annict.auth

import jp.annict.exception.AnnictError
import jp.annict.models.Application
import jp.annict.services.CastsGetResponseData
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.*

data class TokenGetRequestData (
    val client_id: String? =null,
    val client_secret: String? =null,
    val grant_type: String? ="authorization_code",
    val redirect_uri: String? ="urn:ietf:wg:oauth:2.0:oob",
    val code: String? =null,
) {

    internal fun toRequestBody() : RequestBody {

        return MultipartBody.Builder().apply {
            if (client_id != null && client_id.isNotEmpty()) { addFormDataPart("client_id", client_id) }
            if (client_secret != null && client_secret.isNotEmpty()) { addFormDataPart("client_secret", client_secret) }
            if (grant_type != null && grant_type.isNotEmpty()) { addFormDataPart("grant_type", grant_type) }
            if (redirect_uri != null && redirect_uri.isNotEmpty()) { addFormDataPart("redirect_uri", redirect_uri) }
            if (code != null && code.isNotEmpty()) { addFormDataPart("code", code) }

        }.build()

    }
}

@Serializable
data class TokenGetResponseData (
    val access_token: String?,
    val token_Type: String?,
    val scope: String?,
    val created_at: Long?
) {

    constructor() : this(null, null, null, null)

    internal fun parse(response: Response): TokenGetResponseData? {
        response.apply {
            if (response.code != 200) {
                return throw AnnictError(response.message)
            }
            return body?.string()?.let { Json { isLenient = true }.decodeFromString<TokenGetResponseData>(it) }
        }
    }
}

@Serializable
data class TokenInfoGetResponseData(
    val resource_owner_id: Long?,
    val scopes: Array<String>?,
    val expires_in_seconds: String?,
    val application: Application?,
    val created_at: Long?
) {

    constructor() : this(null, null, null, null, null)

    internal fun parse(response: Response): TokenInfoGetResponseData? {
        response.apply {
            if (response.code != 200) {
                return throw AnnictError(response.message)
            }
            return body?.string()?.let { Json { isLenient = true }.decodeFromString<TokenInfoGetResponseData>(it) }
        }
    }
}

data class RevokeTokenPostRequestData(
    val client_id: String? = null,
    val client_secret: String? = null,
    val token: String? = null
) {

    internal fun toRequestBody(): RequestBody {

        return MultipartBody.Builder().apply {
            if (client_id != null && client_id.isNotEmpty()) {
                addFormDataPart("client_id", client_id)
            }
            if (client_secret != null && client_secret.isNotEmpty()) {
                addFormDataPart("client_secret", client_secret)
            }
            if (token != null && token.isNotEmpty()) {
                addFormDataPart("token", token)
            }

        }.build()
    }
}

class AnnictAuth {

    val client = OkHttpClient()

    internal fun getUrlBuilder(): HttpUrl.Builder = HttpUrl.Builder().scheme("https").host("api.annict.com")

    /**
     * アクセストークンを取得する
     */
    fun token(
        client_id: String? = null,
        client_secret: String? = null,
        grant_type: String? = "authorization_code",
        redirect_uri: String? = "urn:ietf:wg:oauth:2.0:oob",
        code: String? = null
    ): TokenGetResponseData? = TokenGetResponseData()
        .parse(
            this.client.newCall(
                Request.Builder().url(getUrlBuilder().addPathSegments("oauth/token").build()).post(
                    TokenGetRequestData(
                        client_id,
                        client_secret,
                        grant_type,
                        redirect_uri,
                        code
                    ).toRequestBody()
                ).build()
            ).execute()
        )


    /**
     * 認証ユーザの情報を取得する
     */
    fun info(token: String): TokenInfoGetResponseData? {

        this.client.newCall(
            Request.Builder().header("Authorization", "Bearer $token").url(getUrlBuilder().addPathSegments("oauth/token/info").build()).build()
        ).execute().apply {
            if (code != 200) {
                return throw AnnictError(message)
            }
            return TokenInfoGetResponseData().parse(this)
        }
    }

    /**
     * トークンを失効させる
     */
    fun revoke(client_id: String? = null, client_secret: String? = null, token: String? = null): Boolean? =
        this.client.newCall(
            Request.Builder().url(getUrlBuilder().addPathSegments("oauth/revoke").build()).post(
                RevokeTokenPostRequestData(client_id, client_secret, token).toRequestBody()
            ).build()
        ).execute().code == 200
}


