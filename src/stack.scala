import common.operation.*
import common.operation.given
import common.result.*
import common.result.given


case class Stack (
    size: Int = 256, 
    capacity: Int = 0, 
    data: List[Byte] = Nil
)

object Stack {
    def push(obj: Byte): Operation[Stack, Unit] =
        (stack: Stack) => stack match
            case Stack(s, c, _) if c == s => 
                Failure("Stack Overflow")
            case Stack(s, c, Nil) => 
                Success(Stack(s, c + 1, List(obj)) -> ())
            case Stack(s, c, h::t) =>
                Success(Stack(s, c + 1, obj::h::t) -> ())
    end push

    def pop(): Operation[Stack, Byte] =
        (stack: Stack) => stack match
            case Stack(_, _, Nil) => 
                Failure("Stack Underflow") 
            case Stack(s, c, h::t) =>
                Success(Stack(s, c - 1, t) -> h)
    end pop
}