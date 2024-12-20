package model.scheduler

import model.entity.Global
import munit.FunSuite
import model.entity.character.Entity
import model.entity.character.common.{Archer, Thief}
import model.entity.character.magic.{BlackMage, WhiteMage}
import model.utilizable.weapons.Weapons
import model.utilizable.weapons.common.{Bow, Dagger}
import model.utilizable.weapons.magic.{Cane, Wand}

import scala.collection.mutable.ListBuffer

/**
 * Class `resetActionBarTest` is a unit test suite for the `TurnScheduler` class,
 * specifically testing the functionality of resetting action bars for characters.
 */
class resetActionBarTest extends FunSuite {

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
   * Add characters to the TurnScheduler.
   */
  scheduler.addUnit(whiteMage)
  scheduler.addUnit(blackMage)
  scheduler.addUnit(archer1)
  scheduler.addUnit(thief1)
  val units_add1: ListBuffer[Global] = scheduler.getUnit
  println(s"The units are: $units_add1")

  /**
   * Calculate max action bar for all units.
   */
  scheduler.calculateMaxActionBar()

  /**
   * Increase action bars by 60.
   */
  scheduler.increaseAllActionBar(60.0)

  /**
   * Test case to reset the action bar for the White Mage.
   */
  test("Reset action bar of White Mage") {
    // Retrieve and print the current action bar before resetting
    val ABWhiteMage1: Double = scheduler.getCurrentActionBar(whiteMage)
    println(s"White Mage action bar is: $ABWhiteMage1")
    // Reset the action bar for White Mage
    scheduler.resetActionBar(whiteMage)
    // Retrieve the action bar after resetting
    val ABWhiteMage2: Double = scheduler.getCurrentActionBar(whiteMage)
    println(s"White Mage action bar is: $ABWhiteMage2")
    // Assert that the action bar is reset to 0
    assertEquals(scheduler.getCurrentActionBar(whiteMage), 0.0)
  }

  /**
   * Test case to verify that resetActionBar resets the action bar to 0.0.
   */
  test("resetActionBar should reset the action bar to 0.0") {
    scheduler.addUnit(thief1)

    scheduler.increaseAllActionBar(10.0)

    scheduler.resetActionBar(thief1)

    assert(scheduler.getCurrentActionBar(thief1) == 0.0, true)
  }

  /**
   * Test case to verify that the initial action bar is 0.0 when adding a character.
   */
  test("The initial action bar should be 0.0 when adding a character") {
    val character: Archer = new Archer("p8", "dodo", 70, 50, 65) {
      weaponsSlot = Option(bow)
    }
    assert(scheduler.getCurrentActionBar(character) == 0.0, true)
  }

  /**
   * Test case to verify that the action bar updates correctly when incremented.
   */
  test("The action bar should update correctly when incremented") {
    scheduler.addUnit(archer1)

    scheduler.increaseAllActionBar(15.0)

    assert(scheduler.getCurrentActionBar(archer1) == 15.0, true)
  }
}
