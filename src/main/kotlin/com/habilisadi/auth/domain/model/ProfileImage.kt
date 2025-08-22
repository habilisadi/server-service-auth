package com.habilisadi.auth.domain.model

import com.github.f4b6a3.ulid.UlidCreator
import jakarta.persistence.Embeddable
import kotlin.io.path.Path

@Embeddable
data class ProfileImage(
    val value: String
) {

    init {
        require(value.isNotEmpty()) { "Profile image cannot be empty" }
    }

    companion object {
        fun of(path: String, file: String) =
            ProfileImage(convert(path, file))

        fun of(file: String): ProfileImage {
            val path = "/profile"
            return ProfileImage(convert(path, file))
        }

        private fun convert(path: String, file: String): String {
            val ext = file.substringAfterLast(".")
            val fileName = UlidCreator.getUlid().toString() + ".$ext"
            val filePath = Path(path, fileName).toString()
            return filePath
        }
    }

    fun update(filename: String) =
        of(filename)

    fun update(path: String, filename: String) =
        of(path, filename)

}
