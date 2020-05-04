package org.flyfish.models

import org.flyfish.business.IAttackable
import org.flyfish.business.IAutoMovable
import org.flyfish.business.IAutoShotable
import kotlin.random.Random

/**
 * 敌方坦克
 */
class EnemyTank(x: Int, y: Int) : Tank(x, y),IAutoMovable,IAutoShotable{
    /**
     * 当前的方向
     */
    override var curDirection: Direction = Direction.DOWN

    override fun draw() {
       val tankImg = when (curDirection) {
            Direction.UP -> "img/ds.png"
            Direction.DOWN -> "img/dx.png"
            Direction.LEFT -> "img/dz.png"
            Direction.RIGHT -> "img/dy.png"
        }
        drawImage(tankImg,x,y)
    }

    private fun radomDirection(excludeDirection: Direction?) :Direction{
        val random = Random
        val nextInt = random.nextInt(4)
        val aRandomDirection = when (nextInt) {
            0 -> Direction.UP
            1 -> Direction.DOWN
            2 -> Direction.LEFT
            3 -> Direction.RIGHT
            else -> Direction.UP
        }
        if (aRandomDirection == excludeDirection) {
            return radomDirection(excludeDirection)
        }
        return aRandomDirection
    }

    private var lastAutoMoveTime: Long = 0
    private var theAutoMoveTimeGap: Long = 50

    /**
     * 自动移动的能力方法
     * 实现类自己实现，如果自动移动
     */
    override fun autoMove() {
        val curTime = System.currentTimeMillis()
        if (curTime - lastAutoMoveTime < theAutoMoveTimeGap) {
            return
        }
        lastAutoMoveTime = curTime
        if (curDirection == willConflictDirection) {
            curDirection = radomDirection(willConflictDirection)
            return
        }
        super.autoMove()
    }

    private var lastAutoShotTime: Long = 0
    private var theAutoShotGapTime:Long = 1000

    /**
     * 接口方法： 自动 发射
     * @return IDrawable 可绘制的 单元(可以为 子弹、炮弹...)
     */
    override fun autoShot(): IDrawable? {
        val curTime = System.currentTimeMillis()
        if (curTime - lastAutoShotTime < theAutoShotGapTime) {
            return null
        }
        lastAutoShotTime = curTime
        val bullet = Bullet(curDirection)
        bullet.configLocation(this)
        bullet.curAttackPromoter = this
        return bullet
    }

    /**
     * 接口方法：表示正在遭遇 攻击
     */
    override fun onSufferAttack(theIAttackable: IAttackable): Array<IDrawable>? {
        val curAttackPromoter = theIAttackable.getAttackPromoter()
        curAttackPromoter?.let {
            if (it is EnemyTank) {//如果本次 发起攻击的 也是敌方坦克，则不遭受攻击
                return null
            }
        }
        return super.onSufferAttack(theIAttackable)
    }
}