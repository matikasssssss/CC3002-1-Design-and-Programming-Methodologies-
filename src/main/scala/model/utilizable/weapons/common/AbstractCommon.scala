package model.utilizable.weapons.common

import model.entity.Global
import model.entity.character.Entity
import model.utilizable.weapons.AbstractWeapons

/**
 * Abstract class `AbstractCommon` provides a base implementation for common weapon types.
 * It extends the `AbstractWeapons` class, allowing for specific weapon characteristics and behaviors
 * to be implemented by subclasses.
 *
 * @param id     The unique identifier for the weapon.
 * @param name   The name of the weapon.
 * @param atk    The attack power of the weapon.
 * @param weight The weight of the weapon.
 * @param owner  The character or entity that owns the weapon.
 */
abstract class AbstractCommon(id: String, name: String, atk: Int, weight: Int, owner: Global)
  extends AbstractWeapons(id, name, atk, weight, owner){
  
  

}
