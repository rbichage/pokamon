package com.pokamon.features.pokedex.domain.mapper

import androidx.compose.ui.graphics.Color

enum class PokemonColor(
    val colorValue: Color
) {
    Red(Color.Red),
    Blue(Color.Blue),
    Yellow(Color.Yellow),
    Green(Color.Green),
    Black(Color.Black),
    Brown(Color(0xFF964B00)),
    Purple(Color(0xff800080)),
    Gray(Color.Gray),
    White(Color.White),
    Pink(Color(0xFFFFC0CB)),
    Unknown(Color.Transparent),
}

fun mapToColor(value: String): PokemonColor {
    return when {
        value.contentEquals(PokemonColor.Red.name, true) -> PokemonColor.Red
        value.contentEquals(PokemonColor.Blue.name, true) -> PokemonColor.Blue
        value.contentEquals(PokemonColor.Yellow.name, true) -> PokemonColor.Yellow
        value.contentEquals(PokemonColor.Green.name, true) -> PokemonColor.Green
        value.contentEquals(PokemonColor.Black.name, true) -> PokemonColor.Black
        value.contentEquals(PokemonColor.Brown.name, true) -> PokemonColor.Brown
        value.contentEquals(PokemonColor.Purple.name, true) -> PokemonColor.Purple
        value.contentEquals(PokemonColor.Gray.name, true) -> PokemonColor.Gray
        value.contentEquals(PokemonColor.White.name, true) -> PokemonColor.White
        value.contentEquals(PokemonColor.Pink.name, true) -> PokemonColor.Pink
        else -> PokemonColor.Unknown
    }
}