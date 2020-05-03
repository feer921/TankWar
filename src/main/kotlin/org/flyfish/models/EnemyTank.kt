package org.flyfish.models

import org.flyfish.business.IAutoMovable

/**
 * 敌方坦克
 */
class EnemyTank(x: Int, y: Int) : Tank(x, y),IAutoMovable{

    override fun draw() {

    }
}