package model.actions.attack

import api.{Source, Target}
import model.actions.Actions
import model.actions.equip.AWeapon
import model.entity.Global
import model.entity.character.Entity
import model.entity.character.common.Archer
import model.entity.enemy.{Enemy, IEnemy}
import model.exception.{InvalidTarget, ShouldHaveWeapon}
import model.panels.{IPanel, Panel}
import model.utilizable.weapons.Weapons
import model.utilizable.weapons.common.Bow
import munit.FunSuite
import spray.json.JsObject
import util.Json.{*, given}

import scala.collection.mutable.ListBuffer

/**
 * Class `AttackTest` is a suite of unit tests for the `Attack` class,
 * verifying the functionality of its methods and properties.
 *
 * The tests include checking attribute values, equality comparisons,
 * and the JSON representation of the attack actions.
 */
class AttackTest extends FunSuite {

  /**
   *  Test instances of Attack
   */
  val attack1: Actions = Attack("Push", "F1")
  val attack2: Actions = Attack("Push", "F1")
  val attack3: Actions = Attack("Push I", "F2")

  /**
   * Test case to verify the attributes of the Attack class.
   */
  test("Attack Attributes") {
    assertEquals(attack1.getName, "Push")
    assertEquals(attack1.getId, "F1")
  }

  /**
   * Test case to verify equality of two Attack instances with the same attributes.
   */
  test("Equals should return true if the action have the same name") {
    assertEquals(attack1, attack2)
  }

  /**
   * Test case to verify that an Attack instance is equal to itself.
   */
  test("Equals should return true when comparing the same action") {
    assertEquals(attack1, attack1)
  }

  /**
   * Test case to verify inequality of two Attack instances with different attributes.
   */
  test("Equals should return false if the heal don't have the same action") {
    assertEquals(attack1 == attack3, false)
  }

  /**
   * Test case to verify inequality when comparing an Attack instance with a non-action.
   */
  test("Equals should return false when comparing with a non-action") {
    val nonHeal: Archer = new Archer("p1", "Didi", 70, 50, 65)
    assertEquals(attack1 == nonHeal, false)
  }

  test("An entity that have negative/null hp can not perform an action"){
    val enemy: Global = new Enemy("enemy1", "Thanos", 100, 60, 50, 75)
    val archer: Global = new Archer("archer1", "Legolas", 70, 50, 65)
    val attack: Actions = new Attack("Push", "attack1")
    enemy.decreaseHp(100)
    val exception = intercept[InvalidTarget]{
      enemy.doAction(attack, archer)
    }
    println(exception)
    assert(exception.getMessage == "Thanos has negative HP, and cannot perform an attack")
  }

  test("An entity that have negative/null hp can not receive an action"){
    val enemy: Global = new Enemy("enemy1", "Thanos", 100, 60, 50, 75)
    val archer: Global = new Archer("archer1", "Legolas", 70, 50, 65)
    val bow: Weapons = new Bow("C3", "Bow V", 65, 30, archer)
    val equipBow: Actions = new AWeapon("Equip bow", "equipBow1", ListBuffer(bow))
    archer.doAction(equipBow, archer)
    val attack: Actions = new Attack("Push", "attack1")
    archer.decreaseHp(70)
    val exception = intercept[InvalidTarget] {
      enemy.doAction(attack, archer)
    }
    println(exception)
    assert(exception.getMessage == "Legolas is already defeated and cannot receive an attack")
  }

  test("An attack action should be perform by an entity or enemy") {
    val enemy: Global = new Enemy("enemy1", "Thanos", 100, 60, 50, 75)
    val archer: Global = new Archer("archer1", "Legolas", 70, 50, 65)
    val attack: Actions = new Attack("Push", "attack1")
    val panel12: IPanel = new Panel("p1", 1, 2)
    enemy.doAction(attack, archer)
    println(s"Archer hp is: ${archer.getHp}")
    assertEquals(archer.getHp, 10)

    val invalidSource: Source = new Source{
      def deactivateMagicBoost(): Unit = {}
      val name: String = "Invalid Source"
      val actions: List[Actions] = List()
      def findActionById(id: String): Option[Actions] = None
      def doAction(action: Actions, target: Target): Unit = {}
      def getAtk: Int = 0
      def getAtkm: Int = 0
      def getMp: Int = 0
      def getHp: Int = 0
      def decreaseMp(amount: Int): Unit = {}
      def getName: String = name
      def id: String = "invalid"
      def toJson: JsObject = JsObj(
        "id" -> "Invalid",
        "name" -> "Invalid Source"
      )
    }
    intercept[InvalidTarget]{
      attack.receiveTarget(invalidSource,archer)
    }
  }

  test("An entity should have weapon to perform an attack"){
    val archer: Global = new Archer("archer1", "Legolas", 70, 50, 65)
    val archer2: Global = new Archer("archer2", "Legolas", 70, 50, 65)
    val bow: Weapons = new Bow("C3", "Bow V", 65, 30, archer)
    val equipBow: Actions = new AWeapon("Equip bow", "equipBow1", ListBuffer(bow))
    archer.doAction(equipBow, archer)
    println(s"${archer.getName} weapons: ${archer.getWeapon}")
    val enemy: Global = new Enemy("enemy1", "Thanos", 100, 60, 50, 75)
    println(s"Enemy ${enemy.getName} hp is: ${enemy.getHp}")
    val attack: Actions = new Attack("Push", "attack1")
    archer.doAction(attack, enemy)
    println(s"Enemy ${enemy.getName} hp is: ${enemy.getHp}")
    assertEquals(enemy.getHp, 35)
    intercept[ShouldHaveWeapon]{
      archer2.doAction(attack, enemy)
    }
  }

  /**
   * Test case to verify the JSON representation of an Attack instance.
   */
  test("toJson should return correct JSON for the action") {
    val expectedJson: JsObj = JsObj(
      "id" -> "F1",
      "action" -> s"Attack→Push"
    )
    assertEquals(attack1.toJson, expectedJson)
  }
}
