package pan.alexander.dictionary.domain.entities

data class Translation(
    val text: String,
    val meanings: List<Meanings>
)
