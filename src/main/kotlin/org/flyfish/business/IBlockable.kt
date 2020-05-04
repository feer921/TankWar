package org.flyfish.business

import org.flyfish.models.IDrawable

/**
 * 定义接口
 * 表示可阻挡 能力
 */
interface IBlockable : IDrawable {

    /**
     * 定义接口方法：当前可 阻塞物是否 能阻塞
     */
    fun isCanBlock(): Boolean = true

}