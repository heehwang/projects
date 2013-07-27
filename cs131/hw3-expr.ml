
(* Signature for expressions in x and y. *)

module type EXPR = 
  sig
    type t
    val pi : float
    val buildX : t
    val buildY : t
    val buildSin : t -> t
    val buildCos : t -> t
    val buildAvg : t * t -> t
    val buildMult : t * t -> t
    val exprToString : t -> string
    val eval : t -> float * float -> float
    val sampleExpr : t
  end
