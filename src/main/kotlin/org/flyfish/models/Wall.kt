package org.flyfish.models

import org.flyfish.business.IBlockable

/**
 * 游戏 单位：墙
 * 有：铁墙、水墙
 */
class Wall(x: Int,y: Int) : GameUnit(x,y),IBlockable{

}