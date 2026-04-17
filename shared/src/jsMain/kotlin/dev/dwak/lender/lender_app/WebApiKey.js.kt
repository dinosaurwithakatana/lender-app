package dev.dwak.lender.lender_app

actual fun getWebApiKey(): String = js("window.__config?.apiKey ?? ''")
