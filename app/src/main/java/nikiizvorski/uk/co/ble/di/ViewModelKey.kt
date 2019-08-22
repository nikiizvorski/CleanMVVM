package nikiizvorski.uk.co.ble.di

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

/**
 *
 * @property value KClass<out ViewModel>
 * @constructor
 */
@MustBeDocumented
@kotlin.annotation.Retention
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)