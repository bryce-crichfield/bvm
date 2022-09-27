import common.operation.*
import common.operation.given
import common.result.*
import common.result.given

import heap.Heap
import heap.Block
import heap.BestFitAllocator


def time(f: => Unit)(runs: Int = 1): Unit = {
    val start = System.nanoTime()
    for (i <- 0 until runs) {
        f
    }
    val end = System.nanoTime()
    println(f"${((end - start) / 1e9)/runs}s")
}

object Main extends App {
    val blocks = List (
        Block(0, 20, 0, false),
        Block(20, 40, 1, false), 
        Block(40, 60, 2, true),
        Block(60, 70, 3, true)
    )
    val heap = Heap(70, blocks, Array.fill(70)(0))

    val op = for {
        b <- Heap.alloc(20)
        _ <- Heap.free(b)
    } yield ()
    println(op.run(heap))
}
