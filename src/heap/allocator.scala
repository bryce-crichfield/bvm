package heap

import common.operation.*
import common.operation.given
import common.result.*
import common.result.given

import cats.implicits._

trait Allocator {
    def select(size: Int)(blocks: List[Block]): Result[Block]
    def alloc(size: Int): Operation[Heap, Block] =
        (heap: Heap) => 
            println(s"Alloc : ${heap.blocks}")

            heap match 
            case _ if size > heap.size =>
                Failure("Alloc Out of Memory")
            case _ if size == 0 => 
                Failure("Alloc Undefined Size")
            case Heap(s, bs, d) =>
                for {
                    selected   <- select(size)(bs)
                    (block1, block2) <- Block.split(size).run(selected)
                } yield { 
                    val b1_not_free = block1.copy(free = false)
                    val sublist = b1_not_free::block2.toList
                    val patched = bs.patch(selected.index, sublist, 1)
                    Heap(s, patched, d) -> b1_not_free
                }
    end alloc
}

given BestFitAllocator : Allocator with
    override def select(size: Int)(blocks: List[Block]): Result[Block] =
        blocks.filter (b => b.free && b.size >= size)
            .minByOption(_.size).toResult("Failed to Find Appropriate Block")
    end select
end BestFitAllocator

given FirstFitAllocator : Allocator with
    override def select(size: Int)(blocks: List[Block]): Result[Block] =
        blocks.filter (b => b.free && b.size >= size)
            .headOption.toResult("Failed to Find Appropriate Block")
    end select
end FirstFitAllocator