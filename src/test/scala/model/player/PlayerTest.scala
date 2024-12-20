package model.player

import model.entity.Global
import model.entity.character.common.{Archer, Knight, Thief}
import model.entity.character.magic.BlackMage
import model.utilizable.weapons.Weapons
import model.utilizable.weapons.common.{Bow, Sword}
import model.utilizable.weapons.magic.Cane
import model.utilizable.weapons.common.Dagger
import munit.FunSuite
import util.SprayJson.{JsArr, JsObj}

import scala.collection.mutable.ListBuffer

/**
 * Class `PlayerTest` is a suite of unit tests for the `Player` class,
 * which represents a player in the game and their associated units.
 *
 * The tests cover various aspects of the player, including their units,
 * defeat status, and JSON representation.
 */
class PlayerTest extends FunSuite{

  /**
   * Characters
   */
  val knight: Knight = new Knight("p1", "Principito", 60, 45, 70)
  val archer: Archer = new Archer("p2", "Arquera", 50, 30, 50)
  val thief: Thief = new Thief("p3", "Jadue", 60, 70, 75)
  val blackMage: BlackMage = new BlackMage("p4", "Mago Oscuro", 60, 70, 65, 70)

  /**
   * Weapons
   */
  val bow: Weapons = Bow("C3", "Bow V", 65, 30, archer)
  val dagger: Weapons = Dagger("C2", "Dagger II", 35, 10, thief)
  val sword: Weapons = Sword("C1", "Sword V", 85, 40, knight)
  val cane: Weapons = Cane("C4", "Cane I", 40, 10, blackMage, 80)

  /**
   * Player
   */
  val units1: ListBuffer[Global] = ListBuffer(knight, archer, thief)
  val player1: Player = new Player("pp1", units1)
  val units2: ListBuffer[Global] = ListBuffer(blackMage, archer, thief)
  val player2: Player = new Player("pp2", units2)
  val units3: ListBuffer[Global] = ListBuffer.empty[Global]
  val player3: Player = new Player("pp3", units3)

  /**
   * Test case to verify the units of a player.
   */
  test("Player Units"){
    val Unit: ListBuffer[Global] = player1.getUnits
    println(s"The units of the player are $Unit")
    assertEquals(player1.getUnits, ListBuffer(knight, archer, thief))
    assertNotEquals(player1.getUnits, player2.getUnits)
  }

  test("Player has id"){
    assertEquals(player1.getId, "pp1")
  }

  test("Player can remove units"){
    println(player1.getUnits)
    player1.removeUnit(knight)
    assertEquals(player1.getUnits, ListBuffer(archer, thief))
  }

  /**
   * Test case to check if a player is defeated.
   */
  test("Player is defeated?") {
    val Defeated: Boolean =  player3.isDefeated
    println(s"The player is defeated? $Defeated")
    assert(player3.isDefeated)
  }

  /**
   * Test case to verify the JSON representation of players with characters.
   */
  test("toJson should return correct JSON for players with characters"){
    val expectedJson1: JsObj = JsObj(
      "characters" -> JsArr(
        units1.map(_.toJson)
      )
    )
    assertEquals(player1.toJson, expectedJson1)

    val expectedJson2: JsObj = JsObj(
      "characters" -> JsArr(
        units2.map(_.toJson)
      )
    )
    assertEquals(player2.toJson, expectedJson2)

    val expectedJson3: JsObj = JsObj(
      "characters" -> JsArr(
        units3.map(_.toJson)
      )
    )
    assertEquals(player3.toJson, expectedJson3)
  }


}
