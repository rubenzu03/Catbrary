package com.rubenzu03.catbrary.persistence

import com.rubenzu03.catbrary.dao.CatDao

class CatRepository(private val catDao: CatDao) {
    suspend fun getAllCats() = catDao.getAllCats()

    suspend fun getCatBreedsCount() = catDao.getCatBreedsCount()

    suspend fun insertCat(cat: cat.rubenzu03.catbrary.domain.Cat) = catDao.insertCat(cat)
}