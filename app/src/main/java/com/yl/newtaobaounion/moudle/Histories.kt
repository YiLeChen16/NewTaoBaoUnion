package com.yl.newtaobaounion.moudle


class Histories {
    override fun toString(): String {
        return "Histories{" +
                "histories=" + histories +
                '}'
    }

    private var histories: List<String> = ArrayList()

    fun getHistories(): List<String> {
        return histories
    }

    fun setHistories(histories: List<String>) {
        this.histories = histories
    }
}