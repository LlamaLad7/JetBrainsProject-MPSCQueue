package com.llamalad7.mpsc

import kotlinx.atomicfu.atomic

class MpscQueue<T> {
    private class Node<T>(var element: T) {
        val next = atomic<Node<T>?>(null)
    }

    private val head = atomic<Node<T>?>(null)
    private val tail = atomic<Node<T>?>(null)

    fun push(element: T) {
        val newNode = Node(element)
        val last = tail.getAndSet(newNode)
        if (last == null) {
            head.value = newNode
        } else {
            last.next.value = newNode
        }
    }

    fun pop(): T? {
        var node1: Node<T>?
        while (true) {
            node1 = head.getAndSet(null)
            if (node1 != null) {
                // Claimed an element
                break
            }
            if (tail.value == null) {
                // List is empty
                return null
            }
        }
        val node2 = node1!!.next.value
        if (node2 == null) {
            // node1 was the only one in the list, need to update the tail
            if (!tail.compareAndSet(node1, null)) {
                // node1 is no longer at the tail: concurrent enqueue
                // spin until it completes
                while (true) {
                    val next = node1.next.value
                    if (next != null) {
                        head.value = next
                        break
                    }
                    Thread.onSpinWait()
                }
            }
        } else {
            // New head is the next element
            head.value = node2
        }
        return node1.element
    }
}
