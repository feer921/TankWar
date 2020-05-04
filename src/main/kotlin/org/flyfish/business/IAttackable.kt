package org.flyfish.business

import org.flyfish.ext.checkDrawCollision
import org.flyfish.models.IDrawable

/**
 * 定义接口，表示 可发动攻击的
 */
interface IAttackable : IDrawable {

    /**
     * 定义可变 属性：表示 本次攻击的发动者
     */
    var curAttackPromoter: IDrawable?

    fun setAttackPromotern(attackPromoter: IDrawable?){
        this.curAttackPromoter = attackPromoter
    }
    fun getAttackPromoter(): IDrawable?{
        return this.curAttackPromoter
    }
    /**
     * 表示 可攻击者的攻击力量值
     */
    var attackPowerValue: Int //接口里不能初始化

    /**
     * 接口方法：表示 可攻击单元 碰到 可遭受攻击 单元时，是否发生了攻击(绘制像素接触/冲突了)
     */
    fun isHappenAttack(sufferableUnit: ISufferable): Boolean {
        return checkDrawCollision(sufferableUnit)
    }
    /**
     * 接口方法 --攻击
     * @return IDrawable 受攻击时 可以返回一个 效果视图
     */
    fun attack(sufferableUnit: ISufferable): Array<IDrawable>? {
        return sufferableUnit.onSufferAttack(this)
    }


}