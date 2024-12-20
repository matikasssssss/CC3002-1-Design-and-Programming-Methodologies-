package model.utilizable.weapons.magic

import api.Source
import model.entity.Global
import model.entity.character.Entity

/**
 * Class `Wand` represents a specific type of magic weapon in the game.
 * It extends the `AbstractMagic` class and defines a magic weapon with a unique 
 * identifier, name, physical attack power, weight, owner, and magical attack power.
 *
 * @param id     The unique identifier for the magic wand.
 * @param name   The name of the magic wand.
 * @param atk    The physical attack power of the wand.
 * @param weight The weight of the wand.
 * @param owner  The character that owns the wand, represented by an instance of `AbstractCharacters`.
 * @param atkm   The magical attack power of the wand.
 */
class Wand(id: String, name: String, atk: Int, weight: Int, owner: Global, atkm: Int)
  extends AbstractMagic(id, name, atk, weight, owner, atkm) {
}
