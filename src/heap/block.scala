package heap

import common.operation.*
import common.operation.given
import common.result.*
import common.result.given

final case class Block(
    start: Int, 
    end: Int, 
    index: Int,
    free: Boolean
) {
    def size: Int = end - start
    def fits(req_size: Int): Boolean =
        req_size <= this.size 
}
object Block {

    object SizeOrdering extends Ordering[Block] {
        override def compare(b1: Block, b2: Block): Int =
            if b1.size < b2.size then -1
            else if b1.size > b2.size then 1
            else 0
        end compare
    }

    def split(size: Int): Operation[Block, Option[Block]] =
        (block: Block) => block match
            case _ if size > block.size =>
                Failure("Block Split Oversize")
            case _ if !block.free =>
                Failure("Block Split Not Free")
            case _ if size == block.size =>
                Success(block, None)
            case Block(s, e, i, _) =>
                Success((
                    Block(s, s+size, i, true),
                    Some(Block(s+size, e, i+1, true))
                ))
    end split

    def join(other: Block): Operation[Block, Unit] =
        (self: Block) =>
            if !self.free || !other.free then
                Failure("Block Join Not Free")
            else
                val start = Math.min(self.start, other.start)
                val end = Math.max(self.end, other.end)
                val index = Math.min(self.index, other.index)
                Success(Block(start, end, index, true) -> ())
    end join    
}
