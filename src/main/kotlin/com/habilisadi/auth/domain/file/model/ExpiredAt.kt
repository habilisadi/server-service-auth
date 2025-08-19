package com.habilisadi.auth.domain.file.model

import jakarta.persistence.Embeddable
import java.time.Instant
import java.time.temporal.ChronoUnit

@Embeddable
data class ExpiredAt(
    var value: Instant = Instant.now().plus(5, ChronoUnit.MINUTES)
) {
    companion object {
        fun of(value: Instant): ExpiredAt {
            return ExpiredAt(value)
        }

        fun of(amountToAdd: Long): ExpiredAt {
            return ExpiredAt(Instant.now().plus(amountToAdd, ChronoUnit.MINUTES))
        }

        fun of(amountToAdd: Long, unit: ChronoUnit): ExpiredAt {
            return ExpiredAt(Instant.now().plus(amountToAdd, unit))
        }

        fun of(value: Instant, amountToAdd: Long, unit: ChronoUnit): ExpiredAt {
            return ExpiredAt(value.plus(amountToAdd, unit))
        }
    }
}
