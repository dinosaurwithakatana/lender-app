package dev.dwak.lender.lender_app

@JsFun("() => window.location.origin")
private external fun _getWebServerOrigin(): String

actual fun getWebServerOrigin(): String = _getWebServerOrigin()
