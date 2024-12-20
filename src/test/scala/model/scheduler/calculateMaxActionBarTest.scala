package model.scheduler

import model.entity.character.common.{Archer, Thief}
import model.entity.character.magic.{BlackMage, WhiteMage}
import model.utilizable.weapons.Weapons
import model.utilizable.weapons.common.{Bow, Dagger}
import model.utilizable.weapons.magic.{Cane, Wand}
import munit.FunSuite

import scala.collection.mutable.ListBuffer

/**
 * Class `calculateMaxActionBarTest` is a unit test suite for the `TurnScheduler` class,
 * specifically testing the calculation of the maximum action bar for various character types.
 */
class calculateMaxActionBarTest extends FunSuite{

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
  val whiteMage: WhiteMage = new WhiteMage("p1", "White Mage", 70, 60, 80, 55){
    weaponsSlot = Option(cane)
  }
  val blackMage: BlackMage = new BlackMage("p2", "Black Mage", 60, 60, 85, 60){
    weaponsSlot = Option(wand)
  }
  val archer1: Archer = new Archer("p3", "Didi", 70, 50, 65){
    weaponsSlot = Option(bow)
  }
  val thief1: Thief = new Thief("p4", "Jadue", 70, 65, 75){
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
   * Test case to verify that the maximum action bars are calculated correctly for each unit.
   */
  test("We can calculate Max action bar of all units"){
    val actionBarArcher: Option[Double] = scheduler.getMaxActionBar(archer1)
    println(s"Archer max action bar is: $actionBarArcher")

    val actionBarBlackMage: Option[Double] = scheduler.getMaxActionBar(blackMage)
    println(s"Black Mage max action bar is: $actionBarBlackMage")

    val actionBarWhiteMage: Option[Double] = scheduler.getMaxActionBar(whiteMage)
    println(s"White Mage max action bar is: $actionBarWhiteMage")

    val actionBarThief: Option[Double] = scheduler.getMaxActionBar(thief1)
    println(s"Thief max action bar is: $actionBarThief")
    
    assertEquals(actionBarArcher, Some(80.0))
    assertEquals(actionBarBlackMage, Some(92.5))
    assertEquals(actionBarWhiteMage, Some(85.0))
    assertEquals(actionBarThief, Some(80.0))
  }

}
