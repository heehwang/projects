module Expr1 = 
struct

  type t = 
      X
    | Y
    | Sin      of t
    | Cos      of t
    | Avg      of t * t
    | Mult     of t * t

  (* the mathematical constant pi *)
  let pi = 4. *. atan 1.

  (* Expression creation *)
  let buildX = X
  let buildY = Y
  let buildSin e = Sin e
  let buildCos e = Cos e
  let buildAvg (e1,e2) = Avg(e1,e2)
  let buildMult (e1,e2) = Mult(e1,e2)


  (* exprToString : t1 -> string *)
  let rec exprToString e = match e with
	X -> "x"
      | Y -> "y"
      | Sin e -> "sin(pi*"^exprToString(e)^")"
      | Cos e -> "cos(pi*"^exprToString(e)^")"
      | Avg(e1, e2) ->
	"("^exprToString(e1)^"+"^exprToString(e2)^")/2.0"
      | Mult(e1, e2) ->
	exprToString(e1)^"*"^exprToString(e2)

	  
  (* eval : t1 -> float*float -> float
     Evaluate an expression for the given (x,y) coordinate.
  *)

let rec eval e (x,y) = match e with
      X              -> x
    | Y              -> y
    | (Sin a)        -> sin(pi *. (eval a (x,y)))
    | (Cos b)        -> cos(pi *. (eval b (x,y)))
    | (Avg (c1,c2))  -> ((eval c1 (x,y))+.(eval c2 (x,y)))/.2.0
    | (Mult (d1,d2)) -> (eval d1 (x,y))*.(eval d2 (x,y))

  let sampleExpr =
    buildCos(buildSin(buildMult(buildCos(buildAvg(buildCos(
      buildX),buildMult(buildCos (buildCos (buildAvg
        (buildMult (buildY,buildY),buildCos (buildX)))),
        buildCos (buildMult (buildSin (buildCos
        (buildY)),buildAvg (buildSin (buildX), buildMult
        (buildX,buildX))))))),buildY)));

end
