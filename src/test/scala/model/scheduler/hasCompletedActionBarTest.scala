package model.scheduler

import model.entity.character.common.{Archer, Thief}
import model.entity.character.magic.{BlackMage, WhiteMage}
import model.utilizable.weapons.Weapons
import model.utilizable.weapons.common.{Bow, Dagger}
import model.utilizable.weapons.magic.{Cane, Wand}
import munit.FunSuite

/**
 * Class `hasCompletedActionBarTest` is a unit test suite for the `TurnScheduler` class,
 * specifically testing the functionality of checking whether a unit has completed its action bar.
 */
class hasCompletedActionBarTest extends FunSuite{

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

  //Equip Weapon
  //archer1.equipWeapon(bow)
  //thief1.equipWeapon(dagger)
  //whiteMage.equipWeapon(cane)
  //blackMage.equipWeapon(wand)

  /**
   * Add the characters to the TurnScheduler.
   */
  scheduler.addUnit(whiteMage)
  scheduler.addUnit(blackMage)
  scheduler.addUnit(archer1)
  scheduler.addUnit(thief1)

  /**
   * Calculate the maximum action bar for all units in the scheduler.
   */
  scheduler.calculateMaxActionBar()

  /**
   * Print the maximum action bar for each character
   */
  val actionBarArcher: Option[Double] = scheduler.getMaxActionBar(archer1)
  println(s"Archer max action bar is: $actionBarArcher")

  val actionBarBlackMage: Option[Double] = scheduler.getMaxActionBar(blackMage)
  println(s"Black Mage max action bar is: $actionBarBlackMage")

  val actionBarWhiteMage: Option[Double] = scheduler.getMaxActionBar(whiteMage)
  println(s"White Mage max action bar is: $actionBarWhiteMage")

  val actionBarThief: Option[Double] = scheduler.getMaxActionBar(thief1)
  println(s"Thief max action bar is: $actionBarThief")

  /**
   * Increase the action bars of all units by a specified amount.
   */
  scheduler.increaseAllActionBar(80)

  /**
   * Print the current action bar after the increase
   */
  val actionBarArcherPlus: Double = scheduler.getCurrentActionBar(archer1)
  println(s"Archer Plus current action bar is: $actionBarArcherPlus")

  val actionBarBlackMagePlus: Double = scheduler.getCurrentActionBar(blackMage)
  println(s"Black Mage Plus current action bar is: $actionBarBlackMagePlus")

  val actionBarWhiteMagePlus: Double = scheduler.getCurrentActionBar(whiteMage)
  println(s"White Mage Plus current action bar is: $actionBarWhiteMagePlus")

  val actionBarThiefPlus: Double = scheduler.getCurrentActionBar(thief1)
  println(s"Thief Plus current action bar is: $actionBarThiefPlus")

  /**
   * Test case to verify whether the action bar for each character has been completed.
   */
  test("Has completed the bar action"){
    val AbFull1= scheduler.hasCompletedActionBar(archer1)
    println(s"Has completed: $AbFull1")
    assertEquals(AbFull1, true)
    val AbFull2 = scheduler.hasCompletedActionBar(blackMage)
    println(s"Has completed: $AbFull2")
    assertEquals(AbFull2, false)
    val AbFull3 = scheduler.hasCompletedActionBar(whiteMage)
    println(s"Has completed: $AbFull3")
    assertEquals(AbFull3, false)
    val AbFull4 = scheduler.hasCompletedActionBar(thief1)
    println(s"Has completed: $AbFull4")
    assertEquals(AbFull4, true)
  }

}
