package org.flyfish.models

import org.flyfish.business.IDestroyAble

/**
 * 模型定义：爆炸物
 */
class BlastView(x: Int, y: Int) : GameUnit(x, y), IDestroyAble {
    /**
     * 定义变量：当前是否能被销毁
     */
    override var isCanDestroyed: Boolean = false
    private val blastImgPaths = arrayListOf<String>()
    var blastViewType :Int = 0
    private var curBlastImgIndex = 0;
    init {
        if (blastViewType == 0) {
            (1..2).forEach {
                blastImgPaths.add("img/$it.png")
            }
        }
        else{
            (3..10).forEach{
                blastImgPaths.add("img/$it.png")
            }
        }
    }

    override fun draw() {
        if (curBlastImgIndex < blastImgPaths.size) {
            drawImage(blastImgPaths[curBlastImgIndex], x, y)
        }
        else{
            isCanDestroyed = true
        }
        curBlastImgIndex++;
    }
}