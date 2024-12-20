package model.actions.magic

import model.actions.Actions
import model.entity.Global
import model.entity.character.common.Archer
import model.entity.character.magic.WhiteMage
import model.exception.{CannotEquipWeapon, CannotUseMagicSpells, InsufficientMana, InvalidTarget}
import munit.FunSuite
import util.Json.{*, given}


/**
 * Class `HealingTest` is a suite of unit tests for the `Healing` action,
 * verifying the functionality of its methods and properties related to healing magic.
 *
 * The tests include checking attributes, equality comparisons,
 * and the JSON representation of the healing actions.
 */
class HealingTest extends FunSuite {

  /**
   *  Healing action instances
  */
  val healing1: Healing = new Healing("F1")
  val healing2: Healing = new Healing("F2")
  val healing3: Healing = new Healing("F2")

  /**
   * Test case to verify the attributes of the Healing action.
   */
  test("Healing Attributes") {
    assertEquals(healing1.getName, "Healing")
    assertEquals(healing1.getId, "F1")
  }

  /**
   * Test case to verify equality of two Healing instances with the same id.
   */
  test("Equals should return true if the action have the same id") {
    assertEquals(healing2, healing3)
  }

  /**
   * Test case to verify that a Healing instance is equal to itself.
   */
  test("Equals should return true when comparing the same action") {
    assertEquals(healing1, healing1)
  }

  test("Healing can only by perform by white mage") {
    val healing: Actions = new Healing("healing1")
    val whiteMage: Global = new WhiteMage("p5", "Terra", 60, 60, 80, 55)
    val archer: Global = new Archer("archer1", "Legolas", 70, 50, 65)
    /* we should decrease archer hp */
    archer.decreaseHp(30)
    println(s"Archer hp: ${archer.getHp}")
    println(s"White mage mp: ${whiteMage.getMp}")
    whiteMage.doAction(healing, archer)
    println(s"White mage mp: ${whiteMage.getMp}")
    println(s"Archer hp: ${archer.getHp}")

    intercept[CannotUseMagicSpells] {
      archer.doAction(healing, whiteMage)
    }
  }

  test("An entity that have negative/null hp can not perform healing spell") {
    val healing: Actions = new Healing("healing1")
    val whiteMage: Global = new WhiteMage("p5", "Terra", 60, 60, 80, 55)
    val archer: Global = new Archer("archer1", "Legolas", 70, 50, 65)
    whiteMage.decreaseHp(60)
    archer.decreaseHp(30)
    val exception = intercept[InvalidTarget] {
      whiteMage.doAction(healing, archer)
    }
    println(exception)
    assert(exception.getMessage == "Terra has negative HP and cannot perform healing")
  }

  test("An entity that have negative/null hp can not receive healing") {
    val healing: Actions = new Healing("healing1")
    val whiteMage: Global = new WhiteMage("p5", "Terra", 60, 60, 80, 55)
    val archer: Global = new Archer("archer1", "Legolas", 70, 50, 65)
    archer.decreaseHp(70)
    val exception = intercept[InvalidTarget] {
      whiteMage.doAction(healing, archer)
    }
    println(exception)
    assert(exception.getMessage == "Legolas is already defeated and cannot receive healing")
  }

  test("Entity should have sufficient manna to perform a spell") {
    val healing: Actions = new Healing("healing1")
    val whiteMage: Global = new WhiteMage("p5", "Terra", 60, 60, 80, 10)
    val archer: Global = new Archer("archer1", "Legolas", 70, 50, 65)
    /* we should decrease archer hp */
    archer.decreaseHp(30)
    println(s"Archer hp: ${archer.getHp}")
    println(s"White mage mp: ${whiteMage.getMp}")

    intercept[InsufficientMana] {
      whiteMage.doAction(healing, archer)
    }
  }

  /**
   * Test case to verify inequality of two Healing instances with different names.
   */
  test("Equals should return false if the action don't have the same name") {
    assertEquals(healing1 == healing3, false)
  }

  /**
   * Test case to verify inequality when comparing a Healing instance with a non-action.
   */
  test("Equals should return false when comparing with a non-action") {
    val nonHeal: Archer = new Archer("p1", "Didi", 70, 50, 65)
    assertEquals(healing1 == nonHeal, false)
  }

  /**
   * Test case to verify the JSON representation of a Healing action instance.
   */
  test("toJson should return correct JSON for the action") {
    val expectedJson: JsObj = JsObj(
      "id" -> "F1",
      "action" -> s"Magic→Healing"
    )
    assertEquals(healing1.toJson, expectedJson)
  }
}
