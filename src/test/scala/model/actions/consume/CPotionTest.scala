package model.actions.consume

import model.actions.Actions
import model.actions.equip.AWeapon
import model.actions.magic.Thunder
import model.entity.Global
import model.entity.character.common.Archer
import model.entity.character.magic.BlackMage
import model.entity.enemy.Enemy
import model.exception.{CannotUsePotion, InvalidTarget}
import model.utilizable.potions.Potions
import model.utilizable.potions.common.{Heal, Strength}
import model.utilizable.potions.magic.{MagicalForce, Manna}
import model.utilizable.weapons.magic.Cane
import munit.FunSuite
import util.Json.{*, given}

import scala.collection.mutable.ListBuffer

/**
 * Class `CPotionTest` is a suite of unit tests for the `CPotion` class,
 * verifying the functionality of its methods and properties.
 *
 * The tests include:
 * - Validating attributes of `CPotion` instances.
 * - Equality comparisons between instances.
 * - Interactions with different entity types (common, magical, and enemy).
 * - Correct JSON representation.
 */
class CPotionTest extends FunSuite {

  // Declare potions available for the archer
  private val heal: Heal = new Heal("Heal1", "Basic heal potion")
  private val strength: Strength = new Strength("Strength1", "Basic strength potion")

  /**
   * Instances of `CPotion` to be tested.
   */
  val consumePotion1: CPotion = new CPotion("Consume Potion 1", "ConsumePotion1", ListBuffer(heal, strength))
  val consumePotion2: CPotion = new CPotion("Consume Potion 1", "ConsumePotion2", ListBuffer(heal))
  val consumePotion3: CPotion = new CPotion("Consume Potion 3", "ConsumePotion3", ListBuffer())

  /**
   * Test case to verify the attributes of the Consume potion class.
   */
  test("Consume potion Attributes") {
    assertEquals(consumePotion1.getName, "Consume Potion 1")
    assertEquals(consumePotion1.getId, "ConsumePotion1")
  }

  /**
   * Test case to verify equality of two Consume potion instances with the same attributes.
   */
  test("Equals should return true if the action has the same name") {
    assertEquals(consumePotion1, consumePotion2)
  }

  /**
   * Test case to verify that a Consume potion instance is equal to itself.
   */
  test("Equals should return true when comparing the same action") {
    assertEquals(consumePotion1, consumePotion1)
  }

  /**
   * Test case to verify inequality of two Consume potion instances with different attributes.
   */
  test("Equals should return false if the consume potion actions don't have the same attributes") {
    assertEquals(consumePotion1 == consumePotion3, false)
  }

  /**
   * Test case to verify inequality when comparing a Consume potion instance with a non-action.
   */
  test("Equals should return false when comparing with a non-action") {
    val nonHeal: Archer = new Archer("p1", "Didi", 70, 50, 65)
    assertEquals(consumePotion1 == nonHeal, false)
  }

  test("When a entity consume a magical force potion, magic boost activate and increase atkm"){
    val blackMage: BlackMage = new BlackMage("p4", "Shadow", 60, 60, 85, 60)
    val magicalForce: Potions = new MagicalForce("magicalForce1", "Magical Force potion")
    val inventory: ListBuffer[Potions] = ListBuffer(magicalForce)
    val consume: Actions = new CPotion("Consume Potions", "consumePotion1", inventory)
    val cane: Cane = Cane("C4", "Cane I", 40, 10, blackMage, 40)
    val equipCane: Actions = new AWeapon("Equip cane", "equipCane1", ListBuffer(cane))
    blackMage.doAction(equipCane, blackMage)
    println(s"${blackMage.getName} have weapon: ${blackMage.getWeapon}")
    blackMage.doAction(consume, blackMage)
    println(s"magical boost: ${blackMage.isMagicBoostActivate}")
    val thunder: Actions = new Thunder("thunder1")
    val archer: Global = new Archer("archer1", "Legolas", 70, 50, 65)
    println(s"Archer hp: ${archer.getHp}")
    println(s"Black mage mp: ${blackMage.getMp}")
    blackMage.doAction(thunder, archer)
    println(s"Archer hp: ${archer.getHp}")
    println(s"Black mage mp: ${blackMage.getMp}")
  }

  test("An entity that have negative/null hp can not consume/receive an potion") {
    val blackMage: BlackMage = new BlackMage("p4", "Shadow", 60, 60, 85, 60)
    val cane: Cane = Cane("C4", "Cane I", 40, 10, blackMage, 40)
    val equipCane: Actions = new AWeapon("Equip cane", "equipCane1", ListBuffer(cane))
    blackMage.doAction(equipCane, blackMage)
    val magicalForce: Potions = new MagicalForce("magicalForce1", "Magical Force potion")
    val inventory: ListBuffer[Potions] = ListBuffer(magicalForce)
    val consume: Actions = new CPotion("Consume Potions", "consumePotion1", inventory)
    blackMage.decreaseHp(60)
    val exception = intercept[InvalidTarget] {
      blackMage.doAction(consume, blackMage)
    }
    println(exception)
    assert(exception.getMessage == "Shadow has negative HP and cannot consume any potion")
  }

  /**
   * Test case to verify a magical entity consuming potions.
   */
  test("An entity can consume potions") {
    val blackMage: Global = new BlackMage("blackMage1", "Shadow", 50, 60, 85, 60)
    blackMage.decreaseHp(30)
    blackMage.decreaseMp(30)
    blackMage.increaseDef(-30)

    val heal: Potions = new Heal("heal1", "Heal potion")
    val strength: Potions = new Strength("strength1", "Strength potion")
    val magicalForce: Potions = new MagicalForce("magicalForce1", "Magical Force potion")
    val manna: Potions = new Manna("manna1", "Manna potion")
    val inventory: ListBuffer[Potions] = ListBuffer(heal, strength, magicalForce, manna)
    val consume: Actions = new CPotion("Consume Potions", "consumePotion1", inventory)

    blackMage.doAction(consume, blackMage)
    assertEquals(blackMage.getHp, 30)
    assertEquals(blackMage.getMp, 48)
    assertEquals(blackMage.isMagicBoostActivate, true)
    assertEquals(blackMage.getDef, 34)
  }

  /**
   * Test case to verify that a common entity cannot consume magic potions.
   */
  test("A common entity can not consume magic potions") {
    val archer: Global = new Archer("archer1", "Legolas", 70, 50, 65)
    archer.decreaseHp(30)
    archer.increaseDef(-30)

    val heal: Potions = new Heal("heal1", "Heal potion")
    val strength: Potions = new Strength("strength1", "Strength potion")
    val magicalForce: Potions = new MagicalForce("magicalForce1", "Magical Force potion")
    val manna: Potions = new Manna("manna1", "Manna potion")
    val inventory1: ListBuffer[Potions] = ListBuffer(magicalForce, manna)
    val inventory2: ListBuffer[Potions] = ListBuffer(heal, strength)
    val consume1: Actions = new CPotion("Consume Magic Potions", "consumePotion1", inventory1)
    val consume2: Actions = new CPotion("Consume Common Potions", "consumePotion2", inventory2)

    intercept[CannotUsePotion] {
      archer.doAction(consume1, archer)
    }
    archer.doAction(consume2, archer)
    assertEquals(archer.getHp, 54)
  }

  /**
   * Test case to verify that an enemy cannot consume any potions.
   */
  test("An Enemy can not consume potions") {
    val enemy: Global = new Enemy("enemy1", "Thanos", 100, 60, 50, 75)

    val heal: Potions = new Heal("heal1", "Heal potion")
    val strength: Potions = new Strength("strength1", "Strength potion")
    val magicalForce: Potions = new MagicalForce("magicalForce1", "Magical Force potion")
    val manna: Potions = new Manna("manna1", "Manna potion")
    val inventory: ListBuffer[Potions] = ListBuffer(heal, strength, magicalForce, manna)
    val consume1: Actions = new CPotion("Consume Magic Potions", "consumePotion1", inventory)

    intercept[CannotUsePotion] {
      enemy.doAction(consume1, enemy)
    }
  }

  /**
   * Test case to verify the JSON representation of a Consume potion instance.
   */
  test("toJson should return correct JSON for the action") {
    val expectedJson: JsObj = JsObj(
      "id" -> "ConsumePotion1",
      "action" -> s"Consume a potion→Consume Potion 1"
    )
    assertEquals(consumePotion1.toJson, expectedJson)
  }
}
