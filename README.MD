# MPSC Queue

This is a small implementation of a linearizable Multi-Producer-Single-Consumer concurrent queue in Kotlin using
AtomicFU.

## Implementation

My implementation can be found
[here](https://github.com/LlamaLad7/JetBrainsProject-MPSCQueue/blob/main/src/main/kotlin/com/llamalad7/mpsc/MpscQueue.kt).
It is based on [this paper](https://www.cs.rice.edu/~johnmc/papers/cqueues-mellor-crummey-TR229-1987.pdf).
The queue is actually safe for use by multiple consumers, but they will block each other when consuming an element, so
it is best in a single-consumer setting.

## Tests

The queue is tested with LinCheck
[here](https://github.com/LlamaLad7/JetBrainsProject-MPSCQueue/blob/main/src/test/kotlin/com/llamalad7/mpsc/MpscQueueTest.kt).