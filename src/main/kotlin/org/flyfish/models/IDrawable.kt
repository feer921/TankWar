package org.flyfish.models

/**
 * 定义一个model的接口
 * 表示可绘制的
 */
interface IDrawable {
    /**
     * 当前要绘制的元素在 X轴的坐标
     */
    var x: Int

    /**
     * 当前要绘制的元素在 Y轴的坐标
     */
    var y: Int

    /**
     * 当前要绘制的元素 的宽
     */
    val width: Int

    /**
     * 当前要绘制的元素的高
     */
    val height: Int

    /**
     * 绘制方法，具体怎么绘制 实现类具体实现
     */
    fun draw()

    /**
     * 绘制时，检测 需要绘制的元素之间是否 绘制冲突(绘制的边缘重叠)了
     *
     */
    fun checkDrawCollision(
        unit1X: Int,
        unit1Y: Int,
        unit1W: Int,
        unit1H: Int,
        unit2X: Int,
        unit2Y: Int,
        unit2W: Int,
        unit2H: Int
    ): Boolean {
        return when {
            unit2X + unit2W <= unit1X -> false //第二个在第一个元素的X轴左侧
            unit2Y + unit2H <= unit1Y -> false //第二个在第一个元素的Y轴上方
            unit2Y >= unit1Y + unit1H -> false //第二个在第一个元素的Y+高后Y轴下方
            else -> unit2X > unit1X + unit1W
        }
    }

}