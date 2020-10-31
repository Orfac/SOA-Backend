package service

import config.EntityManagerConfig
import config.Utils
import model.SpaceMarine
import javax.persistence.EntityManager

object DatabaseService {
  var entityManager: EntityManager = EntityManagerConfig.getEntityManager()

  fun save(spaceMarine: SpaceMarine) {
    entityManager.transaction.begin()
    entityManager.persist(spaceMarine)
    entityManager.transaction.commit()
  }

  fun save(spaceMarines: List<SpaceMarine>) {
    entityManager.transaction.begin()
    for (spaceMarine in spaceMarines) {
      entityManager.persist(spaceMarine)
    }
    entityManager.transaction.commit()
  }

  fun get(): List<SpaceMarine> {
    val table = Utils.SpaceMarineTableName
    return entityManager.createQuery("SELECT e FROM $table e").resultList as List<SpaceMarine>
  }

  fun getById(id: Int): SpaceMarine {
    return entityManager.find(SpaceMarine::class.java, id)
  }


}