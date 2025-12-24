package dev.dwak.lender.repos.server

import dev.dwak.lender.models.server.ServerUser

interface UserRepo {
  suspend fun getUserById(id: String): ServerUser
  suspend fun getUserByToken(token: String): ServerUser
  suspend fun getUserByEmail(email: String): ServerUser
  suspend fun tokenExists(token: String): Boolean
  suspend fun userExistsByEmail(email: String): Boolean
  suspend fun listAll(): List<ServerUser>
}