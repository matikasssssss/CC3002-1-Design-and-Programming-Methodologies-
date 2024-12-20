package model.utilizable.weapons.common

import model.entity.character.common.Archer
import model.exception.{CannotEquipWeapon, CannotHaveDef, CannotHaveMp, CannotHeal, CannotUseMagicSpells}
import model.utilizable.weapons.Weapons
import munit.FunSuite
import util.Json.{*, given}

/**
 * Class `BowTest` is a unit test suite for the `Bow` weapon class,
 * specifically testing the attributes, equality, and JSON representation of the bow.
 */
class BowTest extends FunSuite{

  /**
   * Create instances of characters for testing
   */
  val archer1: Archer = new Archer("p1", "Didi", 70, 50, 65)
  val archer2: Archer = new Archer("p1", "Dodo", 75, 30, 55)

  /**
   * Create instances of Bow weapons for testing
   */
  val bow1: Weapons = new Bow("C3", "Bow V", 65, 30, archer1)
  val bow2: Weapons = new Bow("C3", "Bow V", 65, 30, archer1)
  val bow3: Weapons = new Bow("C2", "Bow V", 65, 30, archer2)
  val bow4: Weapons = new Bow("C2", "Bow II", 65, 30, archer1)
  val bow5: Weapons = new Bow("C3", "Bow V", 55, 30, archer1)
  val bow6: Weapons = new Bow("C3", "Bow V", 65, 20, archer1)

  /**
   * Test to verify the attributes of the Bow weapon.
   */
  test("Bow Attributes") {
    assertEquals(bow1.getName, "Bow V")
    assertEquals(bow1.getAtk, 65)
    assertEquals(bow1.getWeight, 30.0)
    assertEquals(bow1.getOwner, archer1)
    assertEquals(bow1.getId, "C3")
  }

  /**
   * Test to check equality when two bows have the same attributes.
   */
  test("Equals should return true if the weapon have the same attributes") {
    assertEquals(bow1, bow2)
  }

  /**
   * Test to check equality of the same bow instance.
   */
  test("Equals should return true when comparing the same weapon") {
    assertEquals(bow1, bow1)
  }

  /**
   * Test to check inequality when bows have different attributes.
   */
  test("Equals should return false if the weapon don't have the same attributes") {
    assertEquals(bow1 == bow3, false)
    assertEquals(bow1 == bow4, false)
    assertEquals(bow1 == bow5, false)
    assertEquals(bow1 == bow6, false)
  }

  test("Bow has 0 atkm"){
    assertEquals(bow1.getAtkm, 0)
  }

  /**
   * Test to ensure non-weapon objects are not equal to bows.
   */
  test("Equals should return false when comparing with a non-weapon") {
    val nonHeal: Archer = new Archer("p1", "Dudu", 70, 50, 65)
    assertEquals(bow1 == nonHeal, false)
  }

  /**
   * Test case to verify that the Weapon cannot increase HP and throws CannotHeal exception.
   */
  test("Weapon cannot increase hp and throws CannotHeal exception") {
    intercept[CannotHeal] {
      bow1.increaseHp(10)
    }
  }

  /**
   * Test case to verify that the Weapon cannot decrease HP and throws CannotHeal exception.
   */
  test("Weapon cannot decrease hp and throws CannotHeal exception") {
    intercept[CannotHeal] {
      bow1.decreaseHp(10)
    }
  }

  /**
   * Test case to verify that the Weapon cannot get HP and throws CannotHeal exception.
   */
  test("Panel cannot get hp and throws CannotHeal exception") {
    intercept[CannotHeal] {
      bow1.getHp
    }
  }

  /**
   * Test case to verify that the Weapon cannot get max HP and throws CannotHeal exception.
   */
  test("Weapon cannot get max hp and throws CannotHeal exception") {
    intercept[CannotHeal] {
      bow1.getMaxHp
    }
  }

  /**
   * Test case to verify that the Weapon cannot use potions and throws CannotUsePotion exception.
   */
  test("Weapon cannot equip weapon and throws CannotEquipWeapon exception") {
    intercept[CannotEquipWeapon]{
      bow1.canEquipWeapon(bow1)
    }
    intercept[CannotEquipWeapon] {
      bow1.equipWeapon(bow1)
    }
  }

  test("Weapon cannot decrease/increase or get max mp and throws CannotHaveNp exception"){
    intercept[CannotHaveMp]{
      bow1.decreaseMp(500054)
    }
    intercept[CannotHaveMp] {
      bow1.increaseMp(500054)
    }
    intercept[CannotHaveMp] {
      bow1.getMaxMp
    }
  }

  test("Weapon cannot increase or get def and throws CannotHaveDef exception"){
    intercept[CannotHaveDef]{
      bow1.increaseDef(43534534)
    }
    intercept[CannotHaveDef] {
      bow1.getDef
    }
  }

  test("Weapon cannot have magic spells and throws CannotUseMagicSpells exception"){
    intercept[CannotUseMagicSpells]{
      bow1.isMagicBoostActivate
    }
    intercept[CannotUseMagicSpells] {
      bow1.activateMagicBoost()
    }
    intercept[CannotUseMagicSpells] {
      bow1.deactivateMagicBoost()
    }

  }

  /**
   * Test to verify the JSON representation of the Bow weapon.
   */
  test("toJson should return correct JSON for the weapon") {
    val expectedJson: JsObj = JsObj(
      "id" -> "C3",
      "name" -> "Bow V",
      "atk" -> 65,
      "weight" -> 30,
      "owner" -> archer1.toJson
    )
    assertEquals(bow1.toJson, expectedJson)
  }
}
