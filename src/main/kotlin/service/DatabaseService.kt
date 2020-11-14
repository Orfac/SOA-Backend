package service

import config.EntityManagerConfig
import config.Utils
import model.SpaceMarine
import javax.persistence.EntityManager

object DatabaseService {
  var entityManager: EntityManager = EntityManagerConfig.getEntityManager()

  fun save(spaceMarine: SpaceMarine) {
    persistMarine(spaceMarine)
  }

  fun save(spaceMarines: List<SpaceMarine>) {

    for (spaceMarine in spaceMarines) {
      persistMarine(spaceMarine)
    }

  }

  fun get(): List<SpaceMarine> {
    val table = Utils.SpaceMarineTableName
    return entityManager.createQuery("SELECT e FROM $table e").resultList as List<SpaceMarine>
  }

  fun getById(id: Long): SpaceMarine {
    return entityManager.find(SpaceMarine::class.java, id)
  }

  fun updateById(id:Long, marine: SpaceMarine){
    val existedMarine = getById(id)
    existedMarine.category = marine.category
    existedMarine.chapter = marine.chapter
    existedMarine.coordinates = marine.coordinates
    existedMarine.health = marine.health
    existedMarine.heartCount = marine.heartCount
    existedMarine.meleeWeapon = marine.meleeWeapon
    existedMarine.name = marine.name
    persistMarine(existedMarine)
  }

  fun saveMarine(marine: SpaceMarine){
    persistMarine(marine)
  }

  private fun persistMarine(marine: SpaceMarine) {
    entityManager.transaction.begin()
    entityManager.persist(marine)
    entityManager.transaction.commit()
  }

  fun deleteById(id: Long) {
    val marine = getById(id)
    entityManager.transaction.begin()
    entityManager.remove(marine)
    entityManager.transaction.commit()

  }

}