package model.utilizable.weapons.common

import api.Source
import model.entity.Global
import model.entity.character.Entity

/**
 * Class `Bow` represents a bow weapon in the game, extending the `AbstractCommon` class.
 * It provides specific characteristics for a bow weapon, including its attack power, weight,
 * and the character that owns it.
 *
 * @param id     The unique identifier for the bow.
 * @param name   The name of the bow.
 * @param atk    The attack power of the bow.
 * @param weight The weight of the bow.
 * @param owner  The character that owns the bow, represented by an instance of `AbstractCharacters`.
 */
class Bow(id: String, name: String, atk: Int, weight: Int, owner: Global)
  extends AbstractCommon(id, name, atk, weight, owner) {
}
