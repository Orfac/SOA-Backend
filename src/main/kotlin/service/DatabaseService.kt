package service

import config.EntityManagerConfig
import config.Utils
import exceptions.NotFoundException
import model.AstartesCategory
import model.SpaceMarine
import java.lang.IllegalStateException
import javax.persistence.EntityManager
import kotlin.random.Random
import kotlin.random.nextLong

object DatabaseService {
  var entityManager: EntityManager = EntityManagerConfig.getEntityManager()

  fun save(spaceMarine: SpaceMarine) {
    saveMarine(spaceMarine)
  }

  fun save(spaceMarines: List<SpaceMarine>) {

    for (spaceMarine in spaceMarines) {
      saveMarine(spaceMarine)
    }

  }

  fun get(): List<SpaceMarine> {
    val table = Utils.SpaceMarineTableName
    val list = entityManager.createQuery("SELECT e FROM $table e")
        .resultList as List<SpaceMarine>
    return list.sortedBy { it.id }
  }

  fun get(sortingFields: List<String>): List<SpaceMarine> {
    val table = Utils.SpaceMarineTableName
    val connectedFields = sortingFields.joinToString()
    return entityManager
        .createNativeQuery("SELECT * FROM $table  ORDER BY $connectedFields", SpaceMarine::class.java)
        .resultList as List<SpaceMarine>
  }

  fun getById(id: Long): SpaceMarine {
    return try {
      entityManager.find(SpaceMarine::class.java, id)
    } catch (ex : IllegalStateException){
      throw NotFoundException("space marine with id = ${id} was not found ")
    }
  }

  fun updateById(id: Long, marine: SpaceMarine) {
    val existedMarine = getById(id)
    existedMarine.category = marine.category
    existedMarine.chapter = marine.chapter
    existedMarine.coordinates = marine.coordinates
    existedMarine.health = marine.health
    existedMarine.heartCount = marine.heartCount
    existedMarine.meleeWeapon = marine.meleeWeapon
    existedMarine.name = marine.name
    saveMarine(existedMarine)
  }

  fun saveMarine(marine: SpaceMarine) {
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

  fun deleteRandomByCategory(category: AstartesCategory){
    val marines = get().filter { it.category != null && it.category == category }
    if (marines.isEmpty()) return

    val randomIndex = Random.nextInt(0, marines.size)

    entityManager.transaction.begin()
    entityManager.remove(marines[randomIndex])
    entityManager.transaction.commit()

  }

}