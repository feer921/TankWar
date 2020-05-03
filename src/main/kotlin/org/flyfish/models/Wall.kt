package org.flyfish.models

import org.flyfish.business.IAttackable
import org.flyfish.business.IBlockable
import org.flyfish.business.IDestroyAble
import org.flyfish.business.ISufferable
import org.itheima.kotlin.game.core.Composer

/**
 * 游戏 单位：墙
 * 有：铁墙、水墙
 */
class Wall(x: Int,y: Int) : GameUnit(x,y),IBlockable,ISufferable,IDestroyAble{
    /**
     * 表示 遭受 单元的生命值
     * 实现类 各自赋值
     */
    override var lifeValue: Int = 3

    /**
     * 定义变量：当前是否能被销毁
     */
    override var isCanDestroyed: Boolean = false
    /**
     * 接口方法：表示正在遭遇 攻击
     */
    override fun onSufferAttack(theIAttackable: IAttackable): Array<IDrawable>? {
        val attackPowerValue = theIAttackable.attackPowerValue
        lifeValue -= attackPowerValue
        Composer.play("snd/hit.wav")
        isCanDestroyed = lifeValue <= 0
        val blastView = BlastView(x,y)
        blastView.blastViewType = 1
        return arrayOf(blastView)
    }





}