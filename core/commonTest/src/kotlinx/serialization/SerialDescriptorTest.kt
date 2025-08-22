/*
 * Copyright 2017-2025 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license.
 */

package kotlinx.serialization

import kotlinx.serialization.descriptors.*
import kotlin.test.*

class SerialDescriptorTest {
    @Test
    fun testBaseDescriptorDefault() {
        val customDescriptor = object : SerialDescriptor {
            // Should have a default implementation (`super.baseDescriptor` won't compile otherwise)
            override val baseDescriptor: SerialDescriptor get() = super.baseDescriptor

            override val serialName: String get() = error("Not implemented")
            override val kind: SerialKind get() = error("Not implemented")
            override val elementsCount: Int get() = error("Not implemented")
            override fun getElementName(index: Int): String = error("Not implemented")
            override fun getElementIndex(name: String): Int = error("Not implemented")
            override fun getElementAnnotations(index: Int): List<Annotation> = error("Not implemented")
            override fun getElementDescriptor(index: Int): SerialDescriptor = error("Not implemented")
            override fun isElementOptional(index: Int): Boolean = error("Not implemented")
        }

        // Should implement the base descriptor as being itself by default
        assertSame(customDescriptor, customDescriptor.baseDescriptor)
    }

    @Test
    fun testBaseDescriptorDelegation() {
        val base = SerialDescriptor("BaseDescriptor", setSerialDescriptor<Unit>())
        val delegatingToBase = object : SerialDescriptor by base {}

        // Should delegate to the underlying base descriptor by default
        assertSame(base.baseDescriptor, delegatingToBase.baseDescriptor)
    }
}
