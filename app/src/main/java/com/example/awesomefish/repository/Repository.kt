package com.example.awesomefish.repository

interface Repository<T> {

    suspend fun saveData(data: T)

    suspend fun saveListOfData(dataList: List<T>)

    suspend fun getAll(): List<T>

    suspend fun <R> getOne(filter: R): T?

    suspend fun deleteAll()
}
