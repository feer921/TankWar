package org.flyfish.models

import org.flyfish.business.IAttackable
import org.flyfish.business.IAutoMovable
import org.flyfish.business.IDestroyAble
import org.flyfish.business.ISufferable
import org.itheima.kotlin.game.core.Painter

/**
 * 视图模型：子弹
 * 注：可移动，可攻击
 */
////方案一
//class Bullet(
//    override var curDirection: Direction, calcLocation: (bulletWidth: Int, bulletHeight: Int) -> Pair<Int, Int>
//) : GameUnit(0,0),
//
//    IMovable {

//方案二
class Bullet(override var curDirection: Direction) : GameUnit(0,0),IAutoMovable,IDestroyAble,IAttackable {

    override var speed: Int = 8
    override val width: Int
    override val height: Int

    /**
     * 定义 可移动 的单元 移动时将要碰到与障碍物冲突的 方向
     */
    override var willConflictDirection: Direction? = null

    /**
     * 定义变量：当前是否能被销毁
     */
    override var isCanDestroyed: Boolean = false

    /**
     * 定义可变 属性：表示 本次攻击的发动者
     */
    override var curAttackPromoter: IDrawable? = null

    /**
     * 表示 可攻击者的攻击力量值
     */
    override var attackPowerValue: Int = 1
    private var imgPath: String = when (curDirection) {
        Direction.UP -> "img/zidan.png"
        Direction.DOWN -> "img/zidanx.png"
        Direction.LEFT -> "img/zidanz.png"
        Direction.RIGHT -> "img/zidany.png"
    }

    init {
        //这是Kotlin 对象的初始化方法
        //根据 图片来求 元素大小
        val sizes: Array<Int> = Painter.size(imgPath)
        this.width = sizes[0]
        this.height = sizes[1]
        //方案一
//        val calcedXy: Pair<Int,Int> =  calcLocation.invoke(width,height)
//        this.x = calcedXy.first
//        this.y = calcedXy.second
    }

    override fun draw() {
        drawImage(imgPath, x, y)
    }


    /**
     * 方案二：
     */
    fun configLocation(owner: IDrawable) {
        val ownerWidth = owner.width
        val ownerHeight = owner.height
        val halfWidthOfOwner = ownerWidth / 2
        val halfHeightOfOwner = ownerHeight / 2

        //子弹的初始位置先赋值 子弹所有者当前 x、y
        var theBulletX = owner.x
        var theBulletY = owner.y
        val theBulletWidth = this.width
        val theBulletHeight = this.height
        when (this.curDirection) {
            Direction.UP -> {
                //如果坦克的方向为 上，则子弹的 X应该是 坦克的X加上一半的坦克宽 - 子弹的半宽
                theBulletX += halfWidthOfOwner - theBulletWidth / 2
                theBulletY -= theBulletHeight / 2
            }
            Direction.LEFT ->{
                theBulletX -= theBulletWidth / 2
                theBulletY += halfHeightOfOwner - theBulletHeight / 2
            }
            Direction.RIGHT ->{
                theBulletX += ownerWidth - theBulletWidth / 2
                theBulletY += halfHeightOfOwner - theBulletHeight / 2
            }
            Direction.DOWN ->{
                theBulletX += halfWidthOfOwner - theBulletWidth / 2
                theBulletY += ownerHeight - theBulletHeight / 2
            }
        }
        this.x = theBulletX
        this.y = theBulletY
    }

    override fun moveFollowDirection() {
        when (curDirection) {
            Direction.UP -> y -= speed
            Direction.DOWN -> y += speed
            Direction.LEFT -> x -= speed
            Direction.RIGHT -> x += speed
        }
    }


    /**
     * 接口方法 --攻击
     * @return IDrawable 受攻击时 可以返回一个 效果视图
     */
    override fun attack(sufferableUnit: ISufferable): Array<IDrawable>? {
        this.isCanDestroyed = true
        return super.attack(sufferableUnit)
    }
}