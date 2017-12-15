package ru.ifmo.telegram.bot.services.game.blingame

data class MyShip(private val tiles: List<Tile.MyShipTile>, private val playerId: Int) {
    private var wrecked = false
    init {
        for (t in tiles) {
            t.ship = this
        }
    }

    fun isWrecked(): Boolean = if (wrecked) true else {
        wrecked = tiles.all { it.isWrecked }
        wrecked
    }
}

val nullShip = MyShip(listOf(), 228)