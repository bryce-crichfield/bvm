package heap

import common.operation.*
import common.operation.given
import common.result.*
import common.result.given


trait BlockSelectionStrategy {
    def apply(size: Int, blocks: List[Block]): Result[(Block, Int)]
}
object BestFit extends BlockSelectionStrategy:
    override def apply(size: Int, blocks: List[Block]): Result[(Block, Int)] =
        var best: Option[(Block, Int)] = None
        var index = 0
        for ((b, i) <- blocks.zipWithIndex) {
            if b.free && b.fits(size) then {
                best match
                    case None => 
                        best = Some(b -> i)
                    case Some(bst) if b.size < bst.size =>
                        best = Some(b -> i)
                    case _ => ()
            }
        }
        best match
            case None => Failure("No Block Found")
            case Some(t) => Success(t)
    end apply
end BestFit