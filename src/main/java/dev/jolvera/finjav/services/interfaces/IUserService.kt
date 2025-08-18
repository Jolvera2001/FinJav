package dev.jolvera.finjav.services.interfaces

import dev.jolvera.finjav.models.User
import java.util.UUID

interface IUserService {
    fun FindById(id: UUID) : User?
    fun Login(name: String, password: String) : User?
    fun Register(user: User)
}