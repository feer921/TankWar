package org.flyfish.business

/**
 * 定义接口：可自动移动的能力
 */
interface IAutoMovable : IMovable {

    /**
     * 自动移动的能力方法
     * 实现类自己实现，如果自动移动
     */
    fun autoMove(){
        moveFollowDirection()
    }

}