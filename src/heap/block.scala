package heap

import common.operation.*
import common.operation.given
import common.result.*
import common.result.given

final case class Block(start: Int, end: Int, free: Boolean) {
    def size: Int = end - start
    def fits(req_size: Int): Boolean =
        req_size <= this.size 
}
object Block {
    def split(size: Int): Operation[Block, Option[Block]] =
        (block: Block) => block match
            case Block(_, _, _) if size > block.size =>
                Failure("Block Split Oversize")
            case Block(_, _, f) if !f =>
                Failure("Block Split Not Free")
            case Block(_, _, _) if size == block.size =>
                Success(block, None)
            case Block(s, e, _) =>
                Success((
                    Block(s, s+size, true),
                    Some(Block(s+size, e, true))
                ))
    end split

    def join(other: Block): Operation[Block, Unit] =
        (self: Block) =>
            if !self.free || !other.free then
                Failure("Block Join Not Free")
            else
                val start = Math.min(self.start, other.start)
                val end = Math.max(self.end, other.end)
                Success(Block(start, end, true) -> ())
    end join    
}
