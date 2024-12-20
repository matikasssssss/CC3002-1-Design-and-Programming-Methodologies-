package model.scheduler

import model.entity.Global
import model.entity.character.Entity
import model.entity.character.common.{Archer, Thief}
import model.entity.character.magic.{BlackMage, WhiteMage}
import model.utilizable.weapons.Weapons
import model.utilizable.weapons.common.{Bow, Dagger}
import model.utilizable.weapons.magic.{Cane, Wand}
import munit.FunSuite

import scala.collection.mutable.ListBuffer

/**
 * Class `addUnitTest` is a unit test suite for the `TurnScheduler` class,
 * specifically testing the functionality of adding units to the scheduler.
 *
 * The tests ensure that the correct units are added and retrieved from the scheduler.
 */
class addUnitTest extends FunSuite{

  /**
   * Create an instance of TurnScheduler to manage character turns.
   */
  val scheduler = new TurnScheduler()
  val cane: Weapons = Cane("C4", "Cane I", 40, 10, whiteMage, 80)
  val wand: Weapons = Wand("C5", "Wand I", 30, 15, blackMage, 60)
  val bow: Weapons = new Bow("C3", "Bow V", 65, 30, archer1)
  
  /**
   * Create instances of characters with their respective attributes and assigned weapons.
   */
  val whiteMage: WhiteMage = new WhiteMage("p1", "White Mage", 70, 60, 80, 55) {
    weaponsSlot = Option(cane)
  }
  val blackMage: BlackMage = new BlackMage("p2", "Black Mage", 60, 60, 85, 60) {
    weaponsSlot = Option(wand)
  }
  val archer1: Archer = new Archer("p3", "Didi", 70, 50, 65) {
    weaponsSlot = Option(bow)
  }
  val thief1: Thief = new Thief("p4", "Jadue", 70, 65, 75) {
    weaponsSlot = Option(dagger)
  }
  
  /**
   * Add units
   */
  val units_add1: ListBuffer[Global] = scheduler.getUnit
  println(s"The units are: $units_add1")
  scheduler.addUnit(whiteMage)
  scheduler.addUnit(blackMage)
  scheduler.addUnit(archer1)
  scheduler.addUnit(thief1)
  val units_add2: ListBuffer[Global] = scheduler.getUnit
  println(s"The units are: $units_add2")

  /**
   * Test case to verify that the correct units are added to the scheduler.
   */
  test("Units in Scheduler") {
    assertEquals(scheduler.getUnit, ListBuffer(whiteMage, blackMage, archer1, thief1))
  }
}