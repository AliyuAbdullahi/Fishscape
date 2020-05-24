package com.example.awesomefish.domain.scenebase

import java.util.*

object SceneManager {
    private val scenes = stackOf<Scene>()
    
    fun addScene(scene: Scene) {
        scenes.push(scene)
    }
    
    fun get(): Scene = scenes.peek()
    
    fun clear() = scenes.clear()
    
}

fun <T> stackOf(vararg items: T): Stack<T> {
    val stack = Stack<T>()
    items.forEach { stack.push(it) }
    return stack
}
