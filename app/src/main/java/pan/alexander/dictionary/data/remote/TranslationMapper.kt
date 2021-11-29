package pan.alexander.dictionary.data.remote

import pan.alexander.dictionary.data.remote.pojo.SearchResponsePojo
import pan.alexander.dictionary.domain.entities.Meanings
import pan.alexander.dictionary.domain.entities.Translation

object TranslationMapper {
    fun map(searchResponsePojo: SearchResponsePojo): Translation =
        Translation(
            text = searchResponsePojo.text ?: "",
            meanings = searchResponsePojo.meanings
                ?.filter {
                    it.id != null
                }?.map {
                    Meanings(
                        id = it.id ?: 0,
                        translation = it.translation?.translation ?: "",
                        imgUrl = it.imageUrl ?: ""
                    )
                } ?: emptyList()
        )
}
