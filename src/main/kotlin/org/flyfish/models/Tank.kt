package org.flyfish.models

import org.flyfish.business.*
import org.itheima.kotlin.game.core.Composer
import sun.rmi.runtime.Log

open class Tank(x: Int, y: Int) : GameUnit(x,y),IMovable,IBlockable,IDestroyAble,ISufferable{
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
    override var willConflictDirection: Direction? = null


    /**
     * 定义变量：当前是否能被销毁
     */
    override var isCanDestroyed: Boolean = false
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

    private var lastShotTime : Long = 0
    private var theShotTimeGap : Long = 600

    /**
     * 发射 子弹
     */
    fun shot(): Bullet? {
        val curTime = System.currentTimeMillis()
        if (curTime - lastShotTime < theShotTimeGap) {
            return null
        }
        lastShotTime = curTime
        val bullet = Bullet(curDirection)
        bullet.configLocation(this)
        bullet.setAttackPromotern(this)
        return bullet
    }


    /**
     * 表示 遭受 单元的生命值
     * 实现类 各自赋值
     */
    override var lifeValue: Int = 5

    /**
     * 接口方法：表示正在遭遇 攻击
     */
    override fun onSufferAttack(theIAttackable: IAttackable): Array<IDrawable>? {
        if (theIAttackable.getAttackPromoter() == this) {
            //如果子弹 是我自己发出去的，则不 遭受攻击
            return null
        }
        val attackPowerValue = theIAttackable.attackPowerValue
        lifeValue -= attackPowerValue
        Composer.play("snd/hit.wav")
        isCanDestroyed = lifeValue <= 0
        val blastView = BlastView(x,y)
        blastView.blastViewType = 1
        return arrayOf(blastView)
    }
}