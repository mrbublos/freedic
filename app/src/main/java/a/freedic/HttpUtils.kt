package a.freedic

import com.github.kittinunf.fuel.android.core.Json
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object HttpUtils {

    suspend fun call4Json(method: HttpMethod, url: String, body: String = ""): Json? {
        try {
            return callHttp(method, url, body)
        } catch (e: Exception) {
            "Error connecting".logE(e)
        }
        return null
    }

    private suspend fun callHttp(method: HttpMethod = HttpMethod.GET, url: String, body: String = ""): Json {
        "Calling: $method $url".log()
        val caller = when (method) {
            HttpMethod.GET -> url.httpGet()
            HttpMethod.POST -> url.httpPost().header(mapOf("Content-Type" to "application/json")).body(body)
        }

        return suspendCoroutine { continuation ->
            caller.responseJson { _, resp, result ->
                try {
                    if (result is Result.Success && resp.headers["Content-Type"]?.firstOrNull()?.contains("application/json") == true) {
                        "Received result ${result.component1()?.content}".log()
                        continuation.resume(result.component1()!!)
                        return@responseJson
                    }
                    continuation.resumeWithException(Exception("Failure response"))
                } catch (e: Exception) {
                    continuation.resumeWithException(e)
                }
            }
        }
    }


}

enum class HttpMethod {
    GET,
    POST
    ;
}