package org.flyfish.ext

import org.flyfish.models.IDrawable

/**
 * 对可绘制接口进行方法的扩展
 * Kotlin 的特性
 */

fun IDrawable.checkDrawCollision(otherDrawable: IDrawable): Boolean {
    return checkDrawCollision(
        x,
        y,
        width,
        height,
        otherDrawable.x,
        otherDrawable.y,
    otherDrawable.width,
    otherDrawable.height)
}