package org.flyfish

import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import org.flyfish.business.*
import org.flyfish.models.*
import org.itheima.kotlin.game.core.Window
import java.io.File
import java.util.concurrent.CopyOnWriteArrayList

/**
 * 游戏窗口类
 */
class GameWindow : Window("Tank-War", "img/s1.png", Config.gameWindowW, Config.gameWindowH) {

    private lateinit var myTank: Tank

    private val views = CopyOnWriteArrayList<IDrawable>()
    /**
     * 本次游戏是否结束
     */
    var isGameOver: Boolean = false

    override fun onCreate() {
        println(" --> Game window onCreate() load the game map...")
        val file = File(javaClass.getResource("/map/2.map").path)
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
                        brickWall.rowOfUnit = lineIndex
                        brickWall.columnOfUnit = columnInLine
                        views.add(brickWall)
                    }
                    '铁' ->{
                        val steelWall = Wall(unitX,unitY)
                        steelWall.setGameUnitType(steelWall.UNIT_TYPE_IRON_WALL)
                        steelWall.rowOfUnit = lineIndex
                        steelWall.columnOfUnit = columnInLine
                        views.add(steelWall)
                    }
                    '草' ->{
                        val grassWall = Wall(unitX,unitY)
                        grassWall.setGameUnitType(grassWall.UNIT_TYPE_GRASS)
                        grassWall.rowOfUnit = lineIndex
                        grassWall.columnOfUnit = columnInLine
                        views.add(grassWall)
                    }
                    '水' ->{
                        val waterWall = Wall(unitX,unitY)
                        waterWall.setGameUnitType(waterWall.UNIT_TYPE_WATER_WALL)
                        waterWall.rowOfUnit = lineIndex
                        waterWall.columnOfUnit = columnInLine
                        views.add(waterWall)
                    }
                    '敌' ->{
                        val enemyTank = EnemyTank(unitX, unitY)
                        enemyTank.rowOfUnit = lineIndex
                        enemyTank.columnOfUnit = columnInLine
                        views.add(enemyTank)
                    }
                }
                columnInLine ++
            }
            lineIndex ++
        }

        myTank = Tank(Config.blockPix * 12, Config.blockPix * 10)
        views.add(myTank)
        //TODO 添加 大本营
    }

    override fun onDisplay() {
        //绘制会一直在运行
        views.forEach {
            it.draw()
        }
//        println(" in the game total game unit count is ${views.size}")
    }

    override fun onKeyPressed(event: KeyEvent) {
        if (isGameOver) {
            return
        }
        var direction: Direction? = null
        println("onKeyPressed() ${event.code} ")
        when (event.code) {
            KeyCode.UP -> direction = Direction.UP
            KeyCode.W -> direction = Direction.UP
            KeyCode.S -> direction = Direction.DOWN
            KeyCode.DOWN -> direction = Direction.DOWN
            KeyCode.A -> direction = Direction.LEFT
            KeyCode.LEFT -> direction = Direction.LEFT
            KeyCode.RIGHT -> direction = Direction.RIGHT
            KeyCode.D -> direction = Direction.RIGHT
            KeyCode.SPACE ->{
                val sendABullet = myTank.shot()
                views.add(sendABullet)
            }
        }
        if (direction != null) {
            myTank.move(direction)
        }
    }

    /**
     * 界面 时时刷新
     *
     */
    override fun onRefresh() {
        //检测 可销毁的元素
        views.filterIsInstance<IDestroyAble>().forEach {
            val needDestroyedThing = it as IDestroyAble
            if (needDestroyedThing.isWillDestroyed()) {
                views.remove(it)
            }
        }

        if (isGameOver) {
            return
        }

        //可移动单元 检测 碰到阻挡单元
        views.filterIsInstance<IMovable>().forEach {moveUnit->
            moveUnit as IMovable
            var conflictDirection: Direction? = null
            var theConflictBockableUnit : IBlockable ? = null
            views.filter { (it is IBlockable) and (moveUnit != it)  }.forEach Tag@{blockUnit ->
                blockUnit as IBlockable
                conflictDirection = moveUnit.willMoveConflict(blockUnit)
                conflictDirection?.let {
                    theConflictBockableUnit = blockUnit
                     return@Tag
                }
            }
            moveUnit.notifyMoveConflict(conflictDirection, theConflictBockableUnit)
        }

        //界面刷新时，检测可自动移动的 元素
        views.filterIsInstance<IAutoMovable>().forEach {
            val autoMovableThing = it as IAutoMovable
            autoMovableThing.autoMove()
        }


        //检测 可以攻击的游戏元素
        views.filterIsInstance<IAttackable>().forEach { attackableUnit ->
            attackableUnit as IAttackable
            views.filterIsInstance<ISufferable>().forEach sufferTag@ /** 放置一个标记 **/{sufferableUnit ->
                sufferableUnit as ISufferable
                if (attackableUnit.isHappenAttack(sufferableUnit)) {
                    val attachDrawable = attackableUnit.attack(sufferableUnit)
                    attachDrawable?.let {
                        views.addAll(attachDrawable)
                    }
                    return@sufferTag
                }
            }
        }
    }

}