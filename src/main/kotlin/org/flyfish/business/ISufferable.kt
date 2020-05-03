package org.flyfish.business

import org.flyfish.models.IDrawable

/**
 * 定义接口：表示遭受攻击
 */
interface ISufferable : IDrawable {

    /**
     * 表示 遭受 单元的生命值
     * 实现类 各自赋值
     */
    var lifeValue :Int

    /**
     * 接口方法：表示正在遭遇 攻击
     */
    fun onSufferAttack(theIAttackable: IAttackable): Array<IDrawable>?

}