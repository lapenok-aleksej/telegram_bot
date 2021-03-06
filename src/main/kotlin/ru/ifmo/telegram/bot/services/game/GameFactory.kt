package ru.ifmo.telegram.bot.services.game

import ru.ifmo.telegram.bot.entity.Player

interface GameFactory<in T : Step, out E : Game<T>> {
    fun maxNumberPlayers(): Int
    fun minNumberPlayers(): Int
    fun getGame(vararg player: Player): E
    fun getInfo(): String
    fun fromJson(json: String, vararg player: Player): E
}

interface StepFactory<out T : Step> {
    fun getStep(str: String, p: Player): T
}