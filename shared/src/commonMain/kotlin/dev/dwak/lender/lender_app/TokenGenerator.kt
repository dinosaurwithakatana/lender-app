package dev.dwak.lender.lender_app

import kotlin.random.Random

object TokenConfig {
    internal const val CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    internal const val KEY_LENGTH = 32
}

fun generateToken() = CharArray(TokenConfig.KEY_LENGTH) {
    TokenConfig.CHARS[Random.nextInt(TokenConfig.CHARS.length)]
}.concatToString()