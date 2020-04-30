package org.flyfish.models

import org.flyfish.business.IBlockable
import org.flyfish.business.IMovable
import org.itheima.kotlin.game.core.Painter

class Tank(x: Int, y: Int) : GameUnit(x,y),IMovable {
    /**
     * 所有的移动速度
     */
    override var speed: Int = 8

    /**
     * 当前的方向
     */
    override var curDirection: Direction = Direction.UP

    /**
     * 将要移动 冲突的方向
     */
    var willConflictDirection: Direction? = null


    /**
     *
     */
    fun move(theDirection: Direction){
        if (theDirection == willConflictDirection) {//当移动的方向 正好是移动冲突的方向时，则不移动
            return
        }
        if (theDirection != curDirection) {//如果和上一次的方向不同，则只是转向而已
            this.curDirection = theDirection
            return
        }
        moveFollowDirection()
    }

    override fun draw() {
        //TODO 还要判断是否为敌人的坦克
        val tankImgPath: String = when (curDirection) {
            Direction.UP -> "img/s1.png"
            Direction.DOWN -> "img/s9.png"
            Direction.LEFT -> "img/s7.png"
            Direction.RIGHT -> "img/s8.png"
        }
        Painter.drawImage(tankImgPath, x, y)
    }

    /**
     * 通知 当前移动 (碰到 阻碍物)冲突了
     * @param directionOfConflict 当前移动冲突的方向
     * @param blockable 当前的阻碍物
     */
    override fun notifyMoveConflict(directionOfConflict: Direction?, blockable: IBlockable?) {
        this.willConflictDirection = directionOfConflict
    }

}