package pan.alexander.dictionary.domain.dto

import pan.alexander.dictionary.domain.entities.MeaningEntity

data class TranslationDto(
    val id: Long,
    val word: String,
    val meanings: List<MeaningEntity>
)
