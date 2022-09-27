package heap
import common.operation.*
import common.operation.given
import common.result.*
import common.result.given

import cats.implicits._

case class Heap  (
    size: Int,
    blocks: List[Block],
    data: Array[Byte]
)

object Heap {
    def apply(size: Int): Heap = 
        Heap (
            size,  
            List(Block(0, size, 0, true)), 
            Array.fill(size)(0)
        )
    end apply


    def alloc(size: Int)(using allocator: Allocator): Operation[Heap, Block] =
        allocator.alloc(size)

    def free(block: Block): Operation[Heap, Unit] =
        (heap: Heap) => 
            println(s"Free : ${heap.blocks}")
            println(s"Free : ${block}")
            if block.index > heap.blocks.size then 
                Failure("Free : Block Index Out of Bounds")
            else if heap.blocks.get(block.index).isEmpty then
                Failure("Free : Block at Index Does Not Exist")
            else if !heap.blocks(block.index).equals(block) then
                Failure("Free : Block at Index Does not Match")
            else {
                val freed = heap.blocks.updated(block.index, 
                    heap.blocks(block.index).copy(free = true))
                val (head, tail) = freed.splitAt(block.index)
                tail match 
                    case b1::b2::t => 
                        for {
                            (joined, ()) <- Block.join(b2).run(b1)
                        } yield heap.copy(blocks = head:::joined::t) -> ()
                    case b1::Nil => Success(
                        heap.copy(blocks = head:::b1::Nil) -> ()
                    )
                    case Nil => Failure ("Free Index is Nil")
            }
            
    end free
}