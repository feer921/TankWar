package org.flyfish

import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import org.flyfish.models.*
import org.itheima.kotlin.game.core.Window
import java.io.File
import java.util.concurrent.CopyOnWriteArrayList

/**
 * 游戏窗口类
 */
class GameWindow : Window("Tank-War", "img/s1.png", Config.gameWindowW, Config.gameWindowH) {

    private lateinit var myTank: Tank

    val views = CopyOnWriteArrayList<IDrawable>()
    /**
     * 本次游戏是否结束
     */
    var isGameOver: Boolean = false

    override fun onCreate() {
        println(" --> Game window onCreate() load the game map...")
        val file = File(javaClass.getResource("/map/1.map").path)
        val lines = file.readLines()
        var lineIndex = 0
        lines.forEach { lineText: String ->
            //这里拿到的每一行 文本
            var columnInLine = 0
            //"在AADDDk"
            //转换成字符数组在遍历
            lineText.toCharArray().forEach {
                val unitY = lineIndex * Config.blockPix
                val unitX = columnInLine * Config.blockPix
                when(it){
                    '砖' -> {
                        val brickWall = Wall(unitX, unitY)
                        brickWall.setGameUnitType(brickWall.UNIT_TYPE_BRICK_WALL)
                        views.add(brickWall)
                    }
                    '铁' ->{
                        val steelWall = Wall(unitX,unitY)
                        steelWall.setGameUnitType(steelWall.UNIT_TYPE_IRON_WALL)
                        views.add(steelWall)
                    }
                    '草' ->{
                        val grassWall = Wall(unitX,unitY)
                        grassWall.setGameUnitType(grassWall.UNIT_TYPE_GRASS)
                        views.add(grassWall)
                    }
                    '水' ->{
                        val waterWall = Wall(unitX,unitY)
                        waterWall.setGameUnitType(waterWall.UNIT_TYPE_WATER_WALL)
                        views.add(waterWall)
                    }
                    '敌' ->{
                       //TODO
                    }
                }
                columnInLine ++
            }
            lineIndex ++

        }

        myTank = Tank(Config.blockPix * 10, Config.blockPix * 12)
        views.add(myTank)
        //TODO 添加 大本营
    }

    override fun onDisplay() {
        //绘制会一直在运行
        views.forEach {
            it.draw()
        }
        println(" int the game units count is ${views.size}")
    }

    override fun onKeyPressed(event: KeyEvent) {
        if (isGameOver) {
            return
        }
        var direction: Direction? = null
        when (event.code) {
            KeyCode.UP -> direction = Direction.UP
            KeyCode.W -> direction = Direction.UP
            KeyCode.S -> direction = Direction.DOWN
            KeyCode.DOWN -> direction = Direction.DOWN
            KeyCode.A -> direction = Direction.LEFT
            KeyCode.LEFT -> direction = Direction.LEFT
            KeyCode.RIGHT -> direction = Direction.RIGHT
            KeyCode.D -> direction = Direction.RIGHT
//            KeyCode.ENTER -> //TODO 开火
        }
        if (direction != null) {
            myTank.move(direction)
        }
    }

    override fun onRefresh() {
    }

}