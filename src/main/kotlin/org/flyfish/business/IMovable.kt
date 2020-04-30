package org.flyfish.business

import org.flyfish.Config
import org.flyfish.models.Direction
import org.flyfish.models.IDrawable

/**
 * 定义接口：可移动 能力
 */
interface IMovable : IDrawable{
    /**
     * 所具有的移动速度
     */
    val speed: Int


    /**
     * 当前的移动方向
     */
    val curDirection : Direction


    /**
     * 判断是否将要 移动冲突了
     * 以现在的坐标 预算下一步移动是否会冲突
     */
    fun willMoveConflict(sufferBlockable: IBlockable): Direction?{
        var curX = this.x
        var curY = this.y
        when (curDirection) {
            Direction.UP -> curY -= speed
            Direction.DOWN -> curY += speed
            Direction.LEFT -> curX -= speed
            Direction.RIGHT -> curX += speed
        }
        //游戏边界
        if (curX < 0) {
            return Direction.LEFT
        }
        if (curX + width > Config.gameWindowW) {
            return Direction.RIGHT
        }
        if(curY < 0){
            return Direction.UP
        }
        if (curY + height > Config.gameWindowH) {
            return Direction.DOWN
        }

        val willConflictWithBlockable: Boolean = checkDrawCollision(
                sufferBlockable.x,
                sufferBlockable.y,
                sufferBlockable.width,
                sufferBlockable.height,
                curX,
                curY,
                width,
                height
        )
        return if(willConflictWithBlockable)curDirection else null
    }


    /**
     * 通知 当前移动 (碰到 阻碍物)冲突了
     * @param directionOfConflict 当前移动冲突的方向
     * @param blockable 当前的阻碍物
     */
    fun notifyMoveConflict(directionOfConflict: Direction?, blockable: IBlockable?)


    /**
     * 可移动的 事物当方向确定后，根据方向和指定的移动速度 进行相应的移动
     * 并且对移动的游戏画布边界判断
     */
    fun moveFollowDirection(){
        when (curDirection) {
            Direction.UP -> y -= speed
            Direction.DOWN -> y += speed
            Direction.LEFT -> x -= speed
            Direction.RIGHT -> x += speed
        }
        if (x < 0) {
            x = 0
        }

        if (x + width > Config.gameWindowW) {
            x = Config.gameWindowW - width
        }

        if (y < 0) {
            y = 0
        }
        if (y + height > Config.gameWindowH) {
            y = Config.gameWindowH - height
        }
    }

}