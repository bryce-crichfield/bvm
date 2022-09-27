import common.operation.*
import common.operation.given
import common.result.*
import common.result.given

import heap.BestFit
import heap.Heap
import heap.Block


def time(f: => Unit)(runs: Int = 1): Unit = {
    val start = System.nanoTime()
    f
    val end = System.nanoTime()
    println(f"${((end - start) / 1e9)/runs}s")
}

object Main extends App {
    val heap = Heap (
        size = 256, 
        blocks = List(
            Block(0, 256, true)
        ),
        data = Array.fill(256)(0)
    )
    val size = 24
    val runs = 1000
    time { 
        for (i <- 0 until 1000) {
            val r = Heap.alloc(20)(BestFit).run(heap) 
            print("")
        }
    } (1000)
}
