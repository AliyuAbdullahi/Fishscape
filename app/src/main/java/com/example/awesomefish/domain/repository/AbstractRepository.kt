package com.example.awesomefish.domain.repository

abstract class AbstractRepository<T> : Repository<T> {
    override suspend fun saveData(data: T) {}

    override suspend fun saveListOfData(dataList: List<T>) {}

    override suspend fun getAll(): List<T> {
        return listOf()
    }

    override suspend fun <R> getOne(filter: R): T? {
        return null
    }

    override suspend fun deleteAll() {}
}
