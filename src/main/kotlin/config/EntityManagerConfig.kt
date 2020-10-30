package config

import javax.persistence.EntityManager
import javax.persistence.Persistence

object EntityManagerConfig {
  private var entityManager: EntityManager

  init {
    val pointUnit = Persistence.createEntityManagerFactory("space-marine-unit")
    entityManager = pointUnit.createEntityManager()
  }

  fun getEntityManager(): EntityManager {
    return entityManager
  }

  fun setEntityManager(entityManager: EntityManager) {
    this.entityManager = entityManager
  }
}