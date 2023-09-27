package com.pokamon.features.pokedex.data.model


import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class PokemonsResponse(
    @Json(name = "count")
    val count: Int,
    @Json(name = "results")
    val results: List<PokemonDTO>
)

@Keep
@JsonClass(generateAdapter = true)
data class PokemonDTO(
    @Json(name = "name")
    val name: String,
    @Json(name = "url")
    val url: String
)

@Keep
@JsonClass(generateAdapter = true)
data class PokemonDetailsResponse(
    @Json(name = "abilities")
    val abilities: List<Ability>,
    @Json(name = "base_experience")
    val baseExperience: Int,
    @Json(name = "forms")
    val forms: List<Form>,
    @Json(name = "height")
    val height: Int,
    @Json(name = "held_items")
    val heldItems: List<Any>,
    @Json(name = "id")
    val id: Int,
    @Json(name = "is_default")
    val isDefault: Boolean,
    @Json(name = "location_area_encounters")
    val locationAreaEncounters: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "order")
    val order: Int,
    @Json(name = "species")
    val species: SpeciesDTO,
    @Json(name = "sprites")
    val sprites: Sprites,
    @Json(name = "stats")
    val stats: List<StatDTO>,
    @Json(name = "weight")
    val weight: Int,
    @Json(name = "types")
    val types: List<TypeHolder>
)

@Keep
@JsonClass(generateAdapter = true)
data class SpeciesResponse(
    @Json(name = "base_happiness")
    val baseHappiness: Int,
    @Json(name = "capture_rate")
    val captureRate: Int,
    @Json(name = "color")
    val color: SpeciesColor,
    @Json(name = "evolution_chain")
    val evolutionChain: EvolutionChain,
    @Json(name = "flavor_text_entries")
    val flavorTextEntries: List<FlavorTextEntry>,
    @Json(name = "forms_switchable")
    val formsSwitchable: Boolean,
    @Json(name = "gender_rate")
    val genderRate: Int,
    @Json(name = "generation")
    val generation: Generation,
    @Json(name = "growth_rate")
    val growthRate: GrowthRate,
    @Json(name = "habitat")
    val habitat: Habitat,
    @Json(name = "has_gender_differences")
    val hasGenderDifferences: Boolean,
    @Json(name = "hatch_counter")
    val hatchCounter: Int,
    @Json(name = "id")
    val id: Int,
    @Json(name = "is_baby")
    val isBaby: Boolean,
    @Json(name = "is_legendary")
    val isLegendary: Boolean,
    @Json(name = "is_mythical")
    val isMythical: Boolean,
    @Json(name = "name")
    val name: String,
    @Json(name = "names")
    val names: List<Name>,
    @Json(name = "order")
    val order: Int,
    @Json(name = "pal_park_encounters")
    val palParkEncounters: List<PalParkEncounter>,
    @Json(name = "pokedex_numbers")
    val pokedexNumbers: List<PokedexNumber>,
    @Json(name = "shape")
    val shape: Shape,
    @Json(name = "varieties")
    val varieties: List<Variety>
)

@Keep
@JsonClass(generateAdapter = true)
data class SpeciesColor(
    @Json(name = "name")
    val name: String,
    @Json(name = "url")
    val url: String
)

@Keep
@JsonClass(generateAdapter = true)
data class EggGroup(
    @Json(name = "name")
    val name: String,
    @Json(name = "url")
    val url: String
)

@Keep
@JsonClass(generateAdapter = true)
data class EvolutionChain(
    @Json(name = "url")
    val url: String
)

@Keep
@JsonClass(generateAdapter = true)
data class FlavorTextEntry(
    @Json(name = "flavor_text")
    val flavorText: String,
    @Json(name = "language")
    val language: Language
)

@Keep
@JsonClass(generateAdapter = true)
data class Generation(
    @Json(name = "name")
    val name: String,
    @Json(name = "url")
    val url: String
)

@Keep
@JsonClass(generateAdapter = true)
data class GrowthRate(
    @Json(name = "name")
    val name: String,
    @Json(name = "url")
    val url: String
)

@Keep
@JsonClass(generateAdapter = true)
data class Habitat(
    @Json(name = "name")
    val name: String,
    @Json(name = "url")
    val url: String
)

@Keep
@JsonClass(generateAdapter = true)
data class Name(
    @Json(name = "language")
    val language: Language,
    @Json(name = "name")
    val name: String
)

@Keep
@JsonClass(generateAdapter = true)
data class PalParkEncounter(
    @Json(name = "area")
    val area: Area,
    @Json(name = "base_score")
    val baseScore: Int,
    @Json(name = "rate")
    val rate: Int
)

@Keep
@JsonClass(generateAdapter = true)
data class PokedexNumber(
    @Json(name = "entry_number")
    val entryNumber: Int,
    @Json(name = "pokedex")
    val pokedex: Pokedex
)

@Keep
@JsonClass(generateAdapter = true)
data class Shape(
    @Json(name = "name")
    val name: String,
    @Json(name = "url")
    val url: String
)

@Keep
@JsonClass(generateAdapter = true)
data class Variety(
    @Json(name = "is_default")
    val isDefault: Boolean,
    @Json(name = "pokemon")
    val pokemon: Pokemon
)

@Keep
@JsonClass(generateAdapter = true)
data class Language(
    @Json(name = "name")
    val name: String,
    @Json(name = "url")
    val url: String
)

@Keep
@JsonClass(generateAdapter = true)
data class Version(
    @Json(name = "name")
    val name: String,
    @Json(name = "url")
    val url: String
)

@Keep
@JsonClass(generateAdapter = true)
data class Area(
    @Json(name = "name")
    val name: String,
    @Json(name = "url")
    val url: String
)

@Keep
@JsonClass(generateAdapter = true)
data class Pokedex(
    @Json(name = "name")
    val name: String,
    @Json(name = "url")
    val url: String
)

@Keep
@JsonClass(generateAdapter = true)
data class Pokemon(
    @Json(name = "name")
    val name: String,
    @Json(name = "url")
    val url: String
)

@Keep
@JsonClass(generateAdapter = true)
data class Ability(
    @Json(name = "ability")
    val ability: AbilityDetails,
    @Json(name = "is_hidden")
    val isHidden: Boolean,
    @Json(name = "slot")
    val slot: Int
)

@Keep
@JsonClass(generateAdapter = true)
data class Form(
    @Json(name = "name")
    val name: String,
    @Json(name = "url")
    val url: String
)

@Keep
@JsonClass(generateAdapter = true)
data class SpeciesDTO(
    @Json(name = "name")
    val name: String,
    @Json(name = "url")
    val url: String
)

@Keep
@JsonClass(generateAdapter = true)
data class Sprites(
    @Json(name = "back_default")
    val backDefault: String? = null,
    @Json(name = "back_female")
    val backFemale: String? = null,
    @Json(name = "back_shiny")
    val backShiny: String? = null,
    @Json(name = "back_shiny_female")
    val backShinyFemale: String? = null,
    @Json(name = "front_default")
    val frontDefault: String? = null,
    @Json(name = "front_female")
    val frontFemale: String? = null,
    @Json(name = "front_shiny")
    val frontShiny: String? = null,
    @Json(name = "front_shiny_female")
    val frontShinyFemale: String? = null,
)

@Keep
@JsonClass(generateAdapter = true)
data class StatDTO(
    @Json(name = "base_stat")
    val baseStat: Int,
    @Json(name = "effort")
    val effort: Int,
    @Json(name = "stat")
    val stat: StatDetails
)

@Keep
@JsonClass(generateAdapter = true)
data class AbilityDetails(
    @Json(name = "name")
    val name: String,
    @Json(name = "url")
    val url: String
)

@Keep
@JsonClass(generateAdapter = true)
data class MoveX(
    @Json(name = "name")
    val name: String,
    @Json(name = "url")
    val url: String
)

@Keep
@JsonClass(generateAdapter = true)
data class VersionGroupDetail(
    @Json(name = "level_learned_at")
    val levelLearnedAt: Int,
    @Json(name = "move_learn_method")
    val moveLearnMethod: MoveLearnMethod,
    @Json(name = "version_group")
    val versionGroup: VersionGroup
)

@Keep
@JsonClass(generateAdapter = true)
data class MoveLearnMethod(
    @Json(name = "name")
    val name: String,
    @Json(name = "url")
    val url: String
)

@Keep
@JsonClass(generateAdapter = true)
data class VersionGroup(
    @Json(name = "name")
    val name: String,
    @Json(name = "url")
    val url: String
)

@Keep
@JsonClass(generateAdapter = true)
data class Other(
    @Json(name = "dream_world")
    val dreamWorld: DreamWorld,
    @Json(name = "home")
    val home: Home,
    @Json(name = "official-artwork")
    val officialArtwork: OfficialArtwork
)


@Keep
@JsonClass(generateAdapter = true)
data class DreamWorld(
    @Json(name = "front_default")
    val frontDefault: String,
    @Json(name = "front_female")
    val frontFemale: Any
)

@Keep
@JsonClass(generateAdapter = true)
data class Home(
    @Json(name = "front_default")
    val frontDefault: String,
    @Json(name = "front_female")
    val frontFemale: Any,
    @Json(name = "front_shiny")
    val frontShiny: String,
    @Json(name = "front_shiny_female")
    val frontShinyFemale: Any
)

@Keep
@JsonClass(generateAdapter = true)
data class OfficialArtwork(
    @Json(name = "front_default")
    val frontDefault: String,
    @Json(name = "front_shiny")
    val frontShiny: String
)

@Keep
@JsonClass(generateAdapter = true)
data class RedBlue(
    @Json(name = "back_default")
    val backDefault: String,
    @Json(name = "back_gray")
    val backGray: String,
    @Json(name = "back_transparent")
    val backTransparent: String,
    @Json(name = "front_default")
    val frontDefault: String,
    @Json(name = "front_gray")
    val frontGray: String,
    @Json(name = "front_transparent")
    val frontTransparent: String
)

@Keep
@JsonClass(generateAdapter = true)
data class Yellow(
    @Json(name = "back_default")
    val backDefault: String,
    @Json(name = "back_gray")
    val backGray: String,
    @Json(name = "back_transparent")
    val backTransparent: String,
    @Json(name = "front_default")
    val frontDefault: String,
    @Json(name = "front_gray")
    val frontGray: String,
    @Json(name = "front_transparent")
    val frontTransparent: String
)

@Keep
@JsonClass(generateAdapter = true)
data class Crystal(
    @Json(name = "back_default")
    val backDefault: String,
    @Json(name = "back_shiny")
    val backShiny: String,
    @Json(name = "back_shiny_transparent")
    val backShinyTransparent: String,
    @Json(name = "back_transparent")
    val backTransparent: String,
    @Json(name = "front_default")
    val frontDefault: String,
    @Json(name = "front_shiny")
    val frontShiny: String,
    @Json(name = "front_shiny_transparent")
    val frontShinyTransparent: String,
    @Json(name = "front_transparent")
    val frontTransparent: String
)

@Keep
@JsonClass(generateAdapter = true)
data class Gold(
    @Json(name = "back_default")
    val backDefault: String,
    @Json(name = "back_shiny")
    val backShiny: String,
    @Json(name = "front_default")
    val frontDefault: String,
    @Json(name = "front_shiny")
    val frontShiny: String,
    @Json(name = "front_transparent")
    val frontTransparent: String
)

@Keep
@JsonClass(generateAdapter = true)
data class Silver(
    @Json(name = "back_default")
    val backDefault: String,
    @Json(name = "back_shiny")
    val backShiny: String,
    @Json(name = "front_default")
    val frontDefault: String,
    @Json(name = "front_shiny")
    val frontShiny: String,
    @Json(name = "front_transparent")
    val frontTransparent: String
)

@Keep
@JsonClass(generateAdapter = true)
data class Emerald(
    @Json(name = "front_default")
    val frontDefault: String,
    @Json(name = "front_shiny")
    val frontShiny: String
)

@Keep
@JsonClass(generateAdapter = true)
data class FireredLeafgreen(
    @Json(name = "back_default")
    val backDefault: String,
    @Json(name = "back_shiny")
    val backShiny: String,
    @Json(name = "front_default")
    val frontDefault: String,
    @Json(name = "front_shiny")
    val frontShiny: String
)

@Keep
@JsonClass(generateAdapter = true)
data class RubySapphire(
    @Json(name = "back_default")
    val backDefault: String,
    @Json(name = "back_shiny")
    val backShiny: String,
    @Json(name = "front_default")
    val frontDefault: String,
    @Json(name = "front_shiny")
    val frontShiny: String
)

@Keep
@JsonClass(generateAdapter = true)
data class DiamondPearl(
    @Json(name = "back_default")
    val backDefault: String,
    @Json(name = "back_female")
    val backFemale: Any,
    @Json(name = "back_shiny")
    val backShiny: String,
    @Json(name = "back_shiny_female")
    val backShinyFemale: Any,
    @Json(name = "front_default")
    val frontDefault: String,
    @Json(name = "front_female")
    val frontFemale: Any,
    @Json(name = "front_shiny")
    val frontShiny: String,
    @Json(name = "front_shiny_female")
    val frontShinyFemale: Any
)

@Keep
@JsonClass(generateAdapter = true)
data class HeartgoldSoulsilver(
    @Json(name = "back_default")
    val backDefault: String,
    @Json(name = "back_female")
    val backFemale: Any,
    @Json(name = "back_shiny")
    val backShiny: String,
    @Json(name = "back_shiny_female")
    val backShinyFemale: Any,
    @Json(name = "front_default")
    val frontDefault: String,
    @Json(name = "front_female")
    val frontFemale: Any,
    @Json(name = "front_shiny")
    val frontShiny: String,
    @Json(name = "front_shiny_female")
    val frontShinyFemale: Any
)

@Keep
@JsonClass(generateAdapter = true)
data class Platinum(
    @Json(name = "back_default")
    val backDefault: String,
    @Json(name = "back_female")
    val backFemale: Any,
    @Json(name = "back_shiny")
    val backShiny: String,
    @Json(name = "back_shiny_female")
    val backShinyFemale: Any,
    @Json(name = "front_default")
    val frontDefault: String,
    @Json(name = "front_female")
    val frontFemale: Any,
    @Json(name = "front_shiny")
    val frontShiny: String,
    @Json(name = "front_shiny_female")
    val frontShinyFemale: Any
)

@Keep
@JsonClass(generateAdapter = true)
data class BlackWhite(
    @Json(name = "animated")
    val animated: Animated,
    @Json(name = "back_default")
    val backDefault: String,
    @Json(name = "back_female")
    val backFemale: Any,
    @Json(name = "back_shiny")
    val backShiny: String,
    @Json(name = "back_shiny_female")
    val backShinyFemale: Any,
    @Json(name = "front_default")
    val frontDefault: String,
    @Json(name = "front_female")
    val frontFemale: Any,
    @Json(name = "front_shiny")
    val frontShiny: String,
    @Json(name = "front_shiny_female")
    val frontShinyFemale: Any
)

@Keep
@JsonClass(generateAdapter = true)
data class Animated(
    @Json(name = "back_default")
    val backDefault: String,
    @Json(name = "back_female")
    val backFemale: Any,
    @Json(name = "back_shiny")
    val backShiny: String,
    @Json(name = "back_shiny_female")
    val backShinyFemale: Any,
    @Json(name = "front_default")
    val frontDefault: String,
    @Json(name = "front_female")
    val frontFemale: Any,
    @Json(name = "front_shiny")
    val frontShiny: String,
    @Json(name = "front_shiny_female")
    val frontShinyFemale: Any
)

@Keep
@JsonClass(generateAdapter = true)
data class OmegarubyAlphasapphire(
    @Json(name = "front_default")
    val frontDefault: String,
    @Json(name = "front_female")
    val frontFemale: Any,
    @Json(name = "front_shiny")
    val frontShiny: String,
    @Json(name = "front_shiny_female")
    val frontShinyFemale: Any
)

@Keep
@JsonClass(generateAdapter = true)
data class Icons(
    @Json(name = "front_default")
    val frontDefault: String,
    @Json(name = "front_female")
    val frontFemale: Any
)

@Keep
@JsonClass(generateAdapter = true)
data class UltraSunUltraMoon(
    @Json(name = "front_default")
    val frontDefault: String,
    @Json(name = "front_female")
    val frontFemale: Any,
    @Json(name = "front_shiny")
    val frontShiny: String,
    @Json(name = "front_shiny_female")
    val frontShinyFemale: Any
)

@Keep
@JsonClass(generateAdapter = true)
data class StatDetails(
    @Json(name = "name")
    val name: String,
    @Json(name = "url")
    val url: String
)

@Keep
@JsonClass(generateAdapter = true)
data class TypeHolder(
    @Json(name = "type")
    val type: Type
)

@Keep
@JsonClass(generateAdapter = true)
data class Type(
    @Json(name = "name")
    val name: String,
    @Json(name = "url")
    val url: String
)