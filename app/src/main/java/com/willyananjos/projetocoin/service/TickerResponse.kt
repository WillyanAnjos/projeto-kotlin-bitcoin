package com.willyananjos.projetocoin.service

class TickerResponse (
    val ticker: Ticker
)

class Ticker(
    val high: String,
    val low: String,
    val vol: String,
    val last: String,
    val buy: String,
    val sell: String,
    val open: String,
    val date: Long
)