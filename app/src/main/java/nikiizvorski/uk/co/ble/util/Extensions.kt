package nikiizvorski.uk.co.ble.util

import nikiizvorski.uk.co.ble.pojos.Photos

/**
 * Kotlin provides the ability to extend a class with new functionality without having to inherit from the class
 * or use design patterns such as Decorator.
 * This is done via special declarations called extensions. For example, you can write new functions for a class from a
 * third-party library that you can't modify.
 * Such functions are available for calling in the usual way as if they were methods of the original class.
 * This mechanism is called extension functions. There are also extension properties that let you define new properties
 * for existing classes.
 *
 * Scope of Extensions are defined as any other under package and require import outside of it. They can also be declared
 * as members of a class.
 *
 * Extensions Visibility can be declared as any other method.
 *
 * More about Extensions here https://kotlinlang.org/docs/reference/extensions.html
 *
 * https://github.com/ragunathjawahar/kaffeine
 */

/**
 *
 * @receiver String
 * @return String
 */
fun String.setFormatted(): String = this.toLowerCase()

/**
 *
 * @receiver String
 * @return String
 */
fun String.removeFirstLastChar(): String =  this.substring(1, this.length - 1)

/**
 *
 * @param device DeviceModel
 * @return String
 */
fun customResult(device: Photos) = with(device.page) {
        // in here, the scope is actually the String using this you can see it
        this.toString()
}


/**
 * Extension Property
 *
 * Note that, since extensions do not actually insert members into classes,
 * there's no efficient way for an extension property to have a backing field.
 * This is why initializers are not allowed for extension properties.
 * Their behavior can only be defined by explicitly providing getters/setters.
 */
val String.extensionVar: String
        get() = "Hello Device"

