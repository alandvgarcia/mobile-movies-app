package com.alandvg.movies_app_test_involves.model

data class ResultPaging<out T>(
    val page: Int,
    val results: T,
    val total_pages: Int,
    val total_results: Int
)