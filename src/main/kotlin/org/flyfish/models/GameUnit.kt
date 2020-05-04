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

    protected val TAG = javaClass.simpleName

    /**
     * 游戏单元所在的行
     * 注：从 下标 0开始
     */
    var rowOfUnit = 0

    /**
     * 游戏单元所在 列
     * 注：从下标 0 开始
     */
    var columnOfUnit = 0

    /**
     * 游戏单元 的名称
     */
    var gameUnitName: String? = null

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
            drawImage(unitImgPath, x, y)
        }
    }


    open fun setGameUnitType(unitType: Int) {
        this.unitType = unitType;
        this.gameUnitName = when (unitType) {
            UNIT_TYPE_BRICK_WALL -> "砖墙"
            UNIT_TYPE_GRASS ->"草墙"
            UNIT_TYPE_IRON_WALL ->"铁墙"
            UNIT_TYPE_TANK ->"坦克"
            UNIT_TYPE_WATER_WALL ->"水墙"
            else -> null
        }
    }

    override fun toString(): String {
        return "$TAG(rowOfUnit=$rowOfUnit, columnOfUnit=$columnOfUnit, gameUnitName=$gameUnitName, unitType=$unitType)"
    }


    fun drawImage(imgPath: String?, x: Int, y: Int) {
        imgPath?.let {
            Painter.drawImage(imgPath, x, y)
        }
    }

}