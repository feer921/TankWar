package org.flyfish.models

import org.flyfish.Config
import org.itheima.kotlin.game.core.Painter

//虽然 接口里定义的是 val类型的 变量，但实现类仍然可以把 val更改成 var
//class GameUnit(override val x: Int, override val y: Int) : ICanDraw {
//
//
//}

/**
 * 定义的游戏单位(即游戏里的各种元素)
 */
open class GameUnit(override var x: Int, override var y: Int) : IDrawable {

    val UNIT_TYPE_TANK = 10
    val UNIT_TYPE_GRASS = UNIT_TYPE_TANK + 1
    val UNIT_TYPE_WATER_WALL = UNIT_TYPE_GRASS + 1
    val UNIT_TYPE_IRON_WALL = UNIT_TYPE_WATER_WALL + 1
    val UNIT_TYPE_BRICK_WALL = UNIT_TYPE_IRON_WALL + 1
    var unitType: Int = -1

    override val width: Int = Config.blockPix;
    override val height: Int = Config.blockPix


    override fun draw() {
        val unitImgPath: String? = when (unitType) {
            UNIT_TYPE_GRASS -> "img/s3.png"
            UNIT_TYPE_TANK -> "img/s10.png"
            UNIT_TYPE_IRON_WALL -> "img/s6.png"
            UNIT_TYPE_WATER_WALL -> "img/s4.png"
            UNIT_TYPE_BRICK_WALL -> "img/s5.png"
            else -> null
        }
        if (unitImgPath != null) {
            Painter.drawImage(unitImgPath, x, y)
        }
    }


    fun setGameUnitType(unitType: Int) {
        this.unitType = unitType;
    }

}