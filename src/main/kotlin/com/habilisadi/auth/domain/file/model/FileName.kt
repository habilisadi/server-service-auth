package com.habilisadi.auth.domain.file.model

import com.github.f4b6a3.ulid.UlidCreator

data class FileName(
    var value: String
) {
    init {
        require(value.isNotEmpty()) { "FileName cannot be empty" }
    }

    companion object {
        fun of(): FileName {
            return FileName(UlidCreator.getUlid().toString())
        }

        fun of(ext: String): FileName {
            return FileName(UlidCreator.getUlid().toString() + "." + ext)
        }

        fun of(ext: String, filename: String): FileName {
            return FileName("$filename.$ext")
        }
    }
}
