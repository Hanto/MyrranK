package com.myrran.domain.world.collisionsystem

import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.Fixture
import kotlin.reflect.KClass

sealed interface ContactEvaluation<T, D> {

    fun andA( evaluationA: (T) -> Boolean ): ContactEvaluation<T, D>
    fun andB( evaluationB: (D) -> Boolean ): ContactEvaluation<T, D>
    fun and( fixtureEvaluation: (Fixture) -> Boolean ): ContactEvaluation<T, D>
    fun then( creationFunction: (T, D) -> ColissionListener.ContactType): ColissionListener.ContactType?

    data class Success<T, D>(
        val contact: Contact,
        val one: T,
        val two: D

    ): ContactEvaluation<T, D> {

        override fun andA(evaluationA: (T) -> Boolean): ContactEvaluation<T, D> =

            when (evaluationA.invoke(one)) {

                true -> this
                false -> Failure()
            }

        override fun andB(evaluationB: (D) -> Boolean): ContactEvaluation<T, D> =

            when (evaluationB.invoke(two)) {

                true -> this
                false -> Failure()
            }

        override fun and(fixtureEvaluation: (Fixture) -> Boolean): ContactEvaluation<T, D> =

            when (fixtureEvaluation.invoke(contact.fixtureA) || fixtureEvaluation.invoke(contact.fixtureB)) {

                true -> this
                false -> Failure()
            }

        override fun then(creationFunction: (T, D) -> ColissionListener.ContactType): ColissionListener.ContactType =

            creationFunction.invoke(one, two)
    }

    class Failure<T, D>: ContactEvaluation<T, D> {

        override fun andA(evaluationA: (T) -> Boolean): ContactEvaluation<T, D> =

            Failure()

        override fun andB(evaluationB: (D) -> Boolean): ContactEvaluation<T, D> =

            Failure()

        override fun and(fixtureEvaluation: (Fixture) -> Boolean): ContactEvaluation<T, D> =

            Failure()

        override fun then(creationFunction: (T, D) -> ColissionListener.ContactType): ColissionListener.ContactType? =

            null
    }
}

@Suppress("UNCHECKED_CAST")
fun<T: Any, D:Any> Contact.are(classzOne: KClass<T>, classzTwo: KClass<D>): ContactEvaluation<T, D>
{
    val userDataA = this.fixtureA.body.userData
    val userDataB = this.fixtureB.body.userData

    return when {

        classzOne.isInstance(userDataA) && classzTwo.isInstance(userDataB) ->
            ContactEvaluation.Success(this, userDataA as T, userDataB as D)

        classzOne.isInstance(userDataB) && classzTwo.isInstance(userDataA) ->
            ContactEvaluation.Success(this, userDataB as T, userDataA as D)

        else -> ContactEvaluation.Failure()
    }
}
