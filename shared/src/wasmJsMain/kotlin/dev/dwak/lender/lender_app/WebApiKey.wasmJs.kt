package dev.dwak.lender.lender_app

@JsFun("() => window.__config?.apiKey ?? ''")
private external fun _getWebApiKey(): String

actual fun getWebApiKey(): String = _getWebApiKey()
