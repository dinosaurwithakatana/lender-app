package dev.dwak.lender.lender_app

import dev.zacsweers.metro.Inject
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.createRouteScopedPlugin
import io.ktor.server.response.respond

class PluginConfiguration {
    var headerName: String = "X-API-Key"
}

@Inject
class ApiKeyPlugin(apiKeyRepo: ApiKeyRepo) {
    val plugin = createRouteScopedPlugin(
        name = "ApiKeyPlugin",
        createConfiguration = ::PluginConfiguration
    ) {
        pluginConfig.apply {
            onCall { call ->
                val apiKey = call.request.headers[headerName]

                if (apiKey == null) {
                    call.respond(HttpStatusCode.Unauthorized, "API key required")
                    return@onCall
                }

                val validKey = apiKeyRepo.hasKey(apiKey)
                if (!validKey) {
                    call.respond(HttpStatusCode.Unauthorized, "Invalid API key")
                    return@onCall
                }

//                 Store in call attributes for later use
//                call.attributes.put(ApiKeyAttribute, validKey)
            }
        }
    }
}