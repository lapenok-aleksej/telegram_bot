package ru.ifmo.telegram.bot.services.game

import ru.ifmo.telegram.bot.entity.Player
import ru.ifmo.telegram.bot.services.main.Games
import ru.ifmo.telegram.bot.services.telegramApi.classes.Keyboard

/**
 *
 */
interface Game<in T : Step> {
    /**
     * accept step and change state of game
     */
    fun step(step: T): String

    /**
     * return state of game
     */
    fun drawPicture(player: Player): Array<Byte>

    /**
     * возвращает то, что надо делать игроку
     */
    fun getMessage(player: Player): String

    /**
     * surrender game
     */
    fun surrender(player: Player)

    /**
     * return json of game
     */
    fun toJson(): String

    /**
     * return playes in game
     */
    fun getPlayes(): List<Player>

    fun getGameId(): Games

    fun isFinished(): Boolean

    fun isCurrent(player: Player): Boolean

    fun getKeyboard(): Keyboard
}


interface Step {
    /**
     * who made this step
     */
    val player: Player
}
