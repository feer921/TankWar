package org.flyfish.business

import org.flyfish.Config
import org.flyfish.models.IDrawable

/**
 * 定义接口：可销毁的
 */
interface IDestroyAble : IDrawable {

    /**
     * 定义变量：当前是否能被销毁
     */
    var isCanDestroyed: Boolean

    /**
     * 是否将要被销毁
     * @return true:将要被销毁；false:不能被销毁
     */
    fun isWillDestroyed() : Boolean{
        if (isCanDestroyed) {
            return true
        }
        //通用的 接口实现
        if (x < -width) {
            //左 超出 游戏画面边界
            return true
        }
        if (x > Config.gameWindowW) {
            //右 超出 游戏画面边界
            return true
        }
        if (y < -height) {
            return true
        }
        if (y > Config.gameWindowH) {
            return true
        }
        return false
    }

}