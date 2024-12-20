package model.entity.character.common

import model.actions.attack.Attack
import model.actions.consume.CPotion
import model.actions.equip.AWeapon
import model.actions.magic.Healing
import model.actions.movement.Move
import model.entity.character.magic.{BlackMage, WhiteMage}
import model.exception.{ActionNotFound, CannotHaveMp}
import model.utilizable.potions.Potions
import model.utilizable.potions.common.{Heal, Strength}
import model.utilizable.potions.magic.MagicalForce
import model.utilizable.weapons.Weapons
import model.utilizable.weapons.common.Bow
import munit.FunSuite
import util.Json.{*, given}

import scala.collection.mutable.ListBuffer

/**
 * Class `ArcherTest` is a suite of unit tests for the `Archer` character,
 * verifying the functionality of its methods and properties related to archer actions.
 *
 * The tests include checking attributes, weapon assignments, equality comparisons,
 * and the JSON representation of the archer character.
 */
class ArcherTest extends FunSuite{

  /**
   * Weapons
   */
  val bow1: Weapons = new Bow("C3", "Bow V", 65, 30, archer1)
  val bow2: Weapons = new Bow("C3", "Bow V", 65, 30, archer2)

  /**
   * Characters
   */
  val archer1: Archer = new Archer("p1", "Relm", 70, 50, 65){
    weaponsSlot = Some(bow1)
  }
  val archer2: Archer = new Archer("p2", "Lulu", 70, 50, 65) {
    weaponsSlot = Some(bow2)
  }
  val archer3: Archer = new Archer("p1", "Relm", 60, 50, 65)
  val archer4: Archer = new Archer("p1", "Relm", 70, 40, 65)
  val archer5: Archer = new Archer("p1", "Relm", 70, 40, 60)
  val archer6: Archer = new Archer("p1", "Relm", 70, 50, 65)
  val archer7: Archer = new Archer("p1", "Relm", 70, 50, 65)
  val whiteMage4: WhiteMage = new WhiteMage("p5", "Terra", 60, 60, 80, 55)
  //Equip Weapon
  //archer1.equipWeapon(bow)
  /**
   *  Potion
   */
  val heal: Heal = new Heal("D6", "Heal Potion")
  //Add potion and heal to inventory
  //archer1.addToInventory(bow)
  //archer1.addToInventory(heal)

  /**
   * Test case to verify the attributes of the Archer character.
   */
  test("Archer attributes"){
    assertEquals(archer1.getId, "p1")
    assertEquals(archer1.getName, "Relm")
    assertEquals(archer1.getHp, 70)
    assertEquals(archer1.getDef, 50)
    assertEquals(archer1.getWeight, 65.0)
  }
  
  test("An entity has a list of available actions"){
    val weapons: ListBuffer[Weapons] = ListBuffer(bow1)
    val potions: ListBuffer[Potions] = ListBuffer(heal)
    assertEquals(archer1.availableActions, List(new Attack("Archer Attack", "ArcherAttack1"),
      new Move("Archer Movement", "2"),
      new AWeapon("Archer Equip Weapon", "ArcherAWeapon1", ListBuffer.from(weapons)),
      new CPotion("Archer consume potion", "ArcherConsumePotion1", ListBuffer.from(potions))))
  }

  /**
   * Test case to verify that the Archer character has a weapon assigned.
   */
  test("Archer has weapon"){
    val archerWeapon: Option[Weapons] = archer1.getWeapon
    assertEquals(archerWeapon, Some(bow1))
  }

  /**
   * Test case to verify that the HP of the Archer cannot go below zero.
   */
  test("Archer HP should not be negative") {
    archer1.decreaseHp(100) // Exceeds current HP
    assertEquals(archer1.getHp, 0) // HP should be 0, not negative
  }

  test("Archer has maximum attributes"){
    assertEquals(archer1.getMaxHp, 70)
    assertEquals(archer1.getMaxDef, 50)
  }

  test("Archer does not have maximum mp and atkm"){
    intercept[CannotHaveMp]{
      archer1.getMaxMp
    }
    assertEquals(archer1.getAtkm, 0)
  }

  test("Archer can find actions in his action list"){
    val heal: Potions = new Heal("heal1", "Heal potion")
    val strength: Potions = new Strength("strength1", "Strength potion")
    val archer: Archer = new Archer("p1", "Relm", 70, 50, 65)
    val bow: Weapons = new Bow("C3", "Bow V", 65, 30, archer)
    archer.potions ++= List(heal, strength)
    archer.weapons ++= List(bow)
    archer.findActionById("2")

    intercept[ActionNotFound]{
      archer.findActionById("Healing")
    }
  }
  /**
   * Test case to verify equality of two Archer instances with the same attributes.
   */
  test("Equals should return true if the units have the same attributes") {
    assertEquals(archer6, archer7)
  }

  /**
   * Test case to verify that an Archer instance is equal to itself.
   */
  test("Equals should return true when comparing the same units") {
    assertEquals(archer1, archer1)
  }

  /**
   * Test case to verify inequality of Archer instances with different attributes.
   */
  test("Equals should return false if the unit don't have the same attributes") {
    assertEquals(archer1 == archer2, false)
    assertEquals(archer1 == archer3, false)
    assertEquals(archer1 == archer4, false)
    assertEquals(archer1 == archer5, false)
  }

  /**
   * Test case to verify inequality when comparing an Archer instance with a non-unit.
   */
  test("Equals should return false when comparing with a non-unit") {
    val nonUnit: Heal = new Heal("D6", "Heal Potion")
    assertEquals(archer1 == nonUnit, false)
  }

  /**
   * Test case to verify the JSON representation of an Archer instance.
   */
  test("toJson should return correct JSON for the unit") {
    val expectedJson: JsObj = JsObj(
      "id" -> "p1",
      "attributes" -> JsArr(
        JsObj("name" -> "name", "value" -> "Relm"),
        JsObj("name" -> "hp", "value" -> 0),
        JsObj("name" -> "def", "value" -> 50),
        JsObj("name" -> "weight", "value" -> 65),
      ),
      "img" -> "Relm.gif"
    )
    assertEquals(archer1.toJson, expectedJson)
  }
}
