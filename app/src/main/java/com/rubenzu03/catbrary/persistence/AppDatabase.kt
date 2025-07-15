package com.rubenzu03.catbrary.persistence

import androidx.room.Database
import com.rubenzu03.catbrary.domain.Cat

@Database(entities = [Cat::class], version = 1)
class AppDatabase {
}