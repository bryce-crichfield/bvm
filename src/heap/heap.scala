package heap
import common.operation.*
import common.operation.given
import common.result.*
import common.result.given

import cats.implicits._

case class Heap  (
    size: Int,
    blocks: List[Block],
    data: Array[Int]
)

object Heap {
    def apply(size: Int): Heap = 
        Heap (
            size,  
            List(Block(0, size, true)), 
            Array.fill(size)(0)
        )
    end apply


    def alloc(size: Int)(select: BlockSelectionStrategy): Operation[Heap, Int] =
        (heap: Heap) => heap match 
            case _ if size > heap.size =>
                Failure("Alloc Out of Memory")
            case _ if size == 0 => 
                Failure("Alloc Undefined Size")
            case Heap(s, bs, d) =>
                for {
                    (b, i)   <- select(size, bs)
                    (b1, optb2) <- Block.split(size).run(b)
                } yield { 
                    val b1_not_free = b1.copy(free = false)
                    val sublist = b1_not_free::optb2.toList
                    val patched = bs.patch(i, sublist, 1)
                    Heap(s, patched, d) -> i
                }
    end alloc

                
}