package com.habilisadi.auth.domain.model

import com.github.f4b6a3.ulid.UlidCreator
import jakarta.persistence.Embeddable
import java.nio.file.Paths

@Embeddable
data class FilePath(
    private var _value: String
) {
    var value: String = _value
        set(value) {
            require(value.trim().isNotEmpty()) { "Filepath cannot be empty" }
            field = of(value).value
        }

    init {
        require(value.trim().isNotEmpty()) { "Filepath cannot be empty" }
    }

    companion object {
        fun of(value: String): FilePath {
            val path = Paths.get(value).toString()
            return FilePath(path)
        }

        fun of(value: String, subPath: String): FilePath {
            val path = Paths.get(value, subPath).toString()
            return FilePath(path)
        }

        fun of(): FilePath {
            val ulid = UlidCreator.getUlid().toString()

            val path = Paths.get(ulid.substring(0, 2), ulid).toString()
            return FilePath(path)
        }
    }

    fun addSubPath(path: String) {
        this.value = of(this.value, path).value
    }

    fun addSubPaths(vararg paths: String) {
        this.value = Paths.get(this.value, *paths).toString()
    }

}
