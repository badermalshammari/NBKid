package com.example.androidtemplate.data

data class VideoOption(
    val id: Long,
    val title: String,
    val description: String,
    val videoFilename: String,
    val coverPicture: String,
    val time: Int
)

val mockVideoList = listOf(
    VideoOption(
        id = 1,
        title = "The Circulatory System",
        description = "This video allows children to learn in a fun way about the circulatory system.",
        videoFilename = "circulatory_system.mp4",
        coverPicture = "default.png",
        time = 150
    ),
    VideoOption(
        id = 2,
        title = "The Reef Cup - Story about Friendship",
        description = "Video teaching kids about friendship, sportsmanship, loyalty, and sea ecosystems.",
        videoFilename = "reef_cup.mp4",
        coverPicture = "default.png",
        time = 589
    ),
    VideoOption(
        id = 3,
        title = "Learn Letter Thaa - Arabic",
        description = "Let's learn the letter Thaa and words with this letter in an entertaining way.",
        videoFilename = "learn_thaa_ar.mp4",
        coverPicture = "default.png",
        time = 188
    ),
    VideoOption(
        id = 4,
        title = "Multiplication & Division - Basic Math",
        description = "Learn more about multiplications and divisions with fun monster characters.",
        videoFilename = "multiplication_division.mp4",
        coverPicture = "default.png",
        time = 240
    ),
    VideoOption(
        id = 5,
        title = "Addition & Subtraction - Basic Math",
        description = "Fun math problems at Monster University with easy examples for kids.",
        videoFilename = "addition_subtraction.mp4",
        coverPicture = "default.png",
        time = 278
    ),
    VideoOption(
        id = 6,
        title = "Musical Notation - Notes",
        description = "Kids learn quarter, eighth, and sixteenth notes with interactive questions.",
        videoFilename = "musical_notation.mp4",
        coverPicture = "default.png",
        time = 225
    ),
    VideoOption(
        id = 7,
        title = "Learn Surah Al-Qadr",
        description = "Learn Surah Al-Qadr repeated 10 times in a fun way with Zakaria characters.",
        videoFilename = "surah_al_qadr.mp4",
        coverPicture = "default.png",
        time = 521
    ),
    VideoOption(
        id = 8,
        title = "Learn Arabic Numbers 1-20",
        description = "Learn numbers from 1 to 20 in Arabic with Zakaria and Zeeko.",
        videoFilename = "arabic_numbers.mp4",
        coverPicture = "default.png",
        time = 244
    ),
    VideoOption(
        id = 9,
        title = "Learn Colors - English & Arabic",
        description = "Learn the names of colors in English and Arabic in a fun way.",
        videoFilename = "colors_en_ar.mp4",
        coverPicture = "default.png",
        time = 318
    ),
    VideoOption(
        id = 10,
        title = "Memory Game - Fruits & Vegetables",
        description = "Play the memory card game with fruits and vegetables cartoon for kids.",
        videoFilename = "memory_game_fruits.mp4",
        coverPicture = "default.png",
        time = 209
    )
)