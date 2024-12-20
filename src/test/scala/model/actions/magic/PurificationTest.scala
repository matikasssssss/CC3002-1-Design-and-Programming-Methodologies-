package model.actions.magic

import model.actions.Actions
import model.entity.Global
import model.entity.character.common.Archer
import model.entity.character.magic.WhiteMage
import model.entity.enemy.{Enemy, IEnemy}
import model.exception.{CannotUseMagicSpells, InsufficientMana, InvalidTarget}
import munit.FunSuite
import util.Json.{*, given}


/**
 * Class `PurificationTest` is a suite of unit tests for the `Purification` action,
 * verifying the functionality of its methods and properties related to purification magic.
 *
 * The tests include checking attributes, equality comparisons,
 * and the JSON representation of the purification action.
 */
class PurificationTest extends FunSuite {
  /**
   *  Purification action instances
   */
  val purification1: Purification = new Purification(id = "F3")
  val purification2: Purification = new Purification(id = "F3")
  val purification3: Purification = new Purification(id = "F3")

  /**
   * Test case to verify the attributes of the Purification action.
   */
  test("Purification Attributes") {
    assertEquals(purification1.getName, "Purification")
    assertEquals(purification1.getId, "F3")
  }

  /**
   * Test case to verify equality of two Purification instances with the same id.
   */
  test("Equals should return true if the action have the same id") {
    assertEquals(purification2, purification3)
  }

  /**
   * Test case to verify that a Purification instance is equal to itself.
   */
  test("Equals should return true when comparing the same action") {
    assertEquals(purification1, purification1)
  }

  /**
   * Test case to verify inequality when comparing a Purification instance with a non-action.
   */
  test("Equals should return false when comparing with a non-action") {
    val nonHeal: Archer = new Archer("p1", "Didi", 70, 50, 65)
    assertEquals(purification1 == nonHeal, false)
  }

  test("Purification can only be used on enemies and be perform by a white mage"){
    val whiteMage: Global = new WhiteMage("p5", "Terra", 60, 60, 80, 80)
    val purification: Actions = new Purification("purification1")
    val enemy: IEnemy = new Enemy("p6", "Scorpion", 70, 60, 80, 55)
    val archer: Global = new Archer("archer1", "Legolas", 70, 50, 65)
    println(s"Enemy hp: ${enemy.getHp}")
    println(s"White mage mp: ${whiteMage.getMp}")
    whiteMage.doAction(purification, enemy)
    println(s"Enemy hp: ${enemy.getHp}")
    println(s"White mage mp: ${whiteMage.getMp}")

    intercept[InvalidTarget] {
      enemy.doAction(purification, whiteMage)
    }

    intercept[InvalidTarget]{
      whiteMage.doAction(purification, archer)
    }
    whiteMage.decreaseMp(45)
    intercept[InsufficientMana]{
      whiteMage.doAction(purification, enemy)
    }
  }

  test("An entity that have negative/null hp can not perform purification spell") {
    val whiteMage: Global = new WhiteMage("p5", "Terra", 60, 60, 80, 80)
    val purification: Actions = new Purification("purification1")
    val enemy: IEnemy = new Enemy("p6", "Scorpion", 70, 60, 80, 55)
    whiteMage.decreaseHp(60)
    val exception = intercept[InvalidTarget] {
      whiteMage.doAction(purification, enemy)
    }
    println(exception)
    assert(exception.getMessage == "Terra has negative HP, and can not perform purification spell")
  }
  test("An entity that have negative/null hp can not receive purification") {
    val whiteMage: Global = new WhiteMage("p5", "Terra", 60, 60, 80, 80)
    val purification: Actions = new Purification("purification1")
    val enemy: IEnemy = new Enemy("p6", "Scorpion", 70, 60, 80, 55)
    enemy.decreaseHp(70)
    val exception = intercept[InvalidTarget] {
      whiteMage.doAction(purification, enemy)
    }
    println(exception)
    assert(exception.getMessage == "Scorpion already defeated and can not receive purification")
  }

  /**
   * Test case to verify the JSON representation of a Purification action instance.
   */
  test("toJson should return correct JSON for the action") {
    val expectedJson: JsObj = JsObj(
      "id" -> "F3",
      "action" -> s"Magic→Purification"
    )
    assertEquals(purification1.toJson, expectedJson)
  }
}
