package br.com.douglasmotta.mysubscribers.repository

import androidx.lifecycle.LiveData
import br.com.douglasmotta.mysubscribers.data.db.SubscriberDao
import br.com.douglasmotta.mysubscribers.data.db.entity.SubscriberEntity

class DatabaseDataSource(
    private val subscriberDao: SubscriberDao
) : SubscriberRepository {
    override suspend fun insertSubscriber(name: String, email: String): Long {
        return subscriberDao.insert(SubscriberEntity(
            name = name,
            email = email
        ))
    }

    override suspend fun updateSubscriber(id: Long, name: String, email: String) {
        return subscriberDao.update(SubscriberEntity(
            id = id,
            name = name,
            email = email
        ))
    }

    override suspend fun deleteSubscriber(id: Long) {
        subscriberDao.delete(id)
    }

    override suspend fun deleteAllSubscribers() {
        subscriberDao.deleteAll()
    }

    override suspend fun getAllSubscribers(): List<SubscriberEntity> {
        return subscriberDao.getAll()
    }
}