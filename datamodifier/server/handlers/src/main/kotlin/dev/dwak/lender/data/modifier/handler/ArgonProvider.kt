package dev.dwak.lender.data.modifier.handler

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder

@ContributesTo(AppScope::class)
interface ArgonProvider {

    @Provides
    @SingleIn(AppScope::class)
    fun argonFactory(): Argon2PasswordEncoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8()

    @Provides
    fun hasher(argon2: Argon2PasswordEncoder): PasswordHasher = {
        argon2.encode(it)!!
    }

    @Provides
    fun verifier(argon2: Argon2PasswordEncoder): PasswordVerifier = { hashed, password ->
        argon2.matches(password, hashed)
    }
}

typealias PasswordHasher = (String) -> String
typealias PasswordVerifier = (String, CharSequence) -> Boolean