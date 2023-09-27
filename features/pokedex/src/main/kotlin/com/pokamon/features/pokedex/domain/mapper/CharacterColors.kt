package com.pokamon.features.pokedex.domain.mapper

import androidx.compose.ui.graphics.Color

enum class CharacterColor(
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

fun mapToColor(value: String): CharacterColor {
    return when {
        value.contentEquals(CharacterColor.Red.name, true) ->CharacterColor.Red
        value.contentEquals(CharacterColor.Blue.name, true) ->CharacterColor.Blue
        value.contentEquals(CharacterColor.Yellow.name, true) ->CharacterColor.Yellow
        value.contentEquals(CharacterColor.Green.name, true) ->CharacterColor.Green
        value.contentEquals(CharacterColor.Black.name, true) ->CharacterColor.Black
        value.contentEquals(CharacterColor.Brown.name, true) ->CharacterColor.Brown
        value.contentEquals(CharacterColor.Purple.name, true) ->CharacterColor.Purple
        value.contentEquals(CharacterColor.Gray.name, true) ->CharacterColor.Gray
        value.contentEquals(CharacterColor.White.name, true) ->CharacterColor.White
        value.contentEquals(CharacterColor.Pink.name, true) ->CharacterColor.Pink
        else ->CharacterColor.Unknown
    }
}