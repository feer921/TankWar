package org.flyfish

import javafx.application.Application

fun main(args:Array<String>){
    Application.launch(GameWindow::class.java)
}

/**
 * 计算 贷款每月 的 本金 和贷款利率
 * 反推 贷款利率
 * @param terms 贷款总期数
 * @param totalMoney 贷款总额
 * @param repaymentMoneyPerMonth 每月还款总金额
 */
fun calcuRate(terms: Int, totalMoney: Double, repaymentMoneyPerMonth: Double) {

    //每月本金
    val capitalPerMonth = totalMoney / terms

    println("每月 本金：$capitalPerMonth ")

    //每月利息： 每月还款总额 - 每月本金
    val interestPerMonth = repaymentMoneyPerMonth - capitalPerMonth

    //算出每月利率
    val rate = interestPerMonth/totalMoney

    println("每月利率：$rate")

}

/**
 * 计算每月 还款总额
 * @param terms 贷款期数
 * @param totalMoney 贷款总额
 * @param rate 已知贷款 利率 .eg.: 0.0055元 即 5厘5
 */
fun calcuPerMonthMoney(terms: Int, totalMoney: Double, rate: Double) {

    //每月本金
    val capitalPerMonth = totalMoney / terms

    println("每月 本金：$capitalPerMonth ")

    //每月利息： 贷款总金额 * 已知贷款利率
    val interestPerMonth = totalMoney * rate

    //算出每月还款 总额

    println("每月还款总额: ${capitalPerMonth + interestPerMonth}")

}