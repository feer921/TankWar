package org.flyfish.models

import org.flyfish.business.IBlockable
import org.flyfish.business.IMovable

open class Tank(x: Int, y: Int) : GameUnit(x,y),IMovable,IBlockable{
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
     * 主动的移动
     */
    fun move(theDirection: Direction){
//        println("$TAG --> move() $theDirection")
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
//        println("$TAG -> draw() curDirection = $curDirection")
        val tankImgPath: String = when (curDirection) {
            Direction.UP -> "img/s1.png"
            Direction.DOWN -> "img/s9.png"
            Direction.LEFT -> "img/s7.png"
            Direction.RIGHT -> "img/s8.png"
        }
        drawImage(tankImgPath, x, y)
    }

    /**
     * 通知 当前移动 (碰到 阻碍物)冲突了
     * @param directionOfConflict 当前移动冲突的方向
     * @param blockable 当前的阻碍物
     */
    override fun notifyMoveConflict(directionOfConflict: Direction?, blockable: IBlockable?) {
        this.willConflictDirection = directionOfConflict
    }

    fun shot(): Bullet {
//        return Bullet(this.curDirection) { bulletWidth, bulletHeight ->
//            var theBulletX = this.x
//            var theBulletY = this.y
//            //要根据当前坦克的方向来确定 子弹的方向
//            val halfWidthOfTank = this.width / 2
//            val halfHeightOfTank = this.height / 2
//
//            when (this.curDirection) {
//                Direction.UP -> {
//                    //如果坦克的方向为 上，则子弹的 X应该是 坦克的X加上一半的坦克宽 - 子弹的半宽
//                    theBulletX += halfWidthOfTank - bulletWidth / 2
//                    theBulletY -= bulletHeight / 2
//                }
//                Direction.LEFT ->{
//                    theBulletX -= bulletWidth / 2
//                    theBulletY += halfHeightOfTank - bulletHeight / 2
//                }
//                Direction.RIGHT ->{
//                    theBulletX += this.width + bulletWidth / 2
//                    theBulletY += halfHeightOfTank - bulletHeight / 2
//                }
//                Direction.DOWN ->{
//                    theBulletX += halfWidthOfTank - bulletWidth / 2
//                    theBulletY += this.height - bulletHeight / 2
//                }
//            }
//            Pair(theBulletX,theBulletY)
//        }
        val bullet = Bullet(curDirection)
        bullet.configLocation(this)
        return bullet
    }
}