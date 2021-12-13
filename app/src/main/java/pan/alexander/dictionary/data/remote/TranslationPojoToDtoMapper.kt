package pan.alexander.dictionary.data.remote

import pan.alexander.dictionary.data.remote.pojo.SearchResponsePojo
import pan.alexander.dictionary.domain.entities.MeaningEntity
import pan.alexander.dictionary.domain.dto.TranslationDto

object TranslationPojoToDtoMapper {
    fun map(searchResponsePojo: SearchResponsePojo): TranslationDto =
        TranslationDto(
            id = searchResponsePojo.id ?: 0,
            word = searchResponsePojo.text ?: "",
            meanings = searchResponsePojo.meanings
                ?.filter {
                    it.id != null
                }?.map {
                    MeaningEntity(
                        id = it.id ?: 0,
                        translation = it.translation?.translation ?: "",
                        previewUrl = it.previewUrl ?: "",
                        imgUrl = it.imageUrl ?: "",
                        transcription = it.transcription ?: "",
                        soundUrl = it.soundUrl ?: ""
                    )
                } ?: emptyList()
        )
}
