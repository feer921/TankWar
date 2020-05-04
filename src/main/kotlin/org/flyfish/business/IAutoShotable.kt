package org.flyfish.business

import org.flyfish.models.IDrawable

/**
 * 定义接口：可自动 射击的功能
 */
interface IAutoShotable : IDrawable {
    /**
     * 接口方法： 自动 发射
     * @return IDrawable 可绘制的 单元(可以为 子弹、炮弹...)
     */
    fun autoShot(): IDrawable?
}