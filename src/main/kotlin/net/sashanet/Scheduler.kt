package net.sashanet

import java.util.Date

class Scheduler(var currentMonth: Int) {
    init {
        currentMonth = Date().month
    }
}