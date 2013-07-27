
(* Name: Hee Hwang

   UID: 804212513

   Others With Whom I Discussed Things: Jerry Wang

   Other Resources I Consulted: 
   google.com
   stackoverflow.com
   piazza.com
   
*)


(* Problem 1a
   fold_left: ('a -> 'b -> 'a) -> 'a -> 'b list -> 'a
*)

let rec fold_left f b l = 
  match l with
    []->b
  | x::xs-> fold_left f (f b x) xs;;



(* Problem 1b
   flatten: 'a list list -> 'a list
*)


let flatten l =
  let strip = (fun l1 l2 ->
    match l2 with
      []->l1
    | x::xs->l1@l2) in
    List.fold_left strip [] l;;

(* A helper function strip a bracket of l2 and put it into l1 *)



(* Problem 1c
   pipe: ('a -> 'a) list -> ('a -> 'a)
*)


let pipe l = 
  let x = fun a -> a in 
  let composition f1 f2 = (fun x -> f2 (f1 (x))) in 
  List.fold_left composition x l;;

(* x: Default function: it returns the given value. If l=[], it returns the same value since there is no function to apply on a given value.*)
(* composition: composition f g = f(g(x)) in mathematical term. In this case, x = function. *)





(* Problem 1d
   swap: ('a -> 'b -> 'c) -> ('b -> 'a -> 'c)
*)   

let swap f= fun x y -> f y x ;;
    
(* swap is a function which takes a function and returns a function which has converted input *)



(* Problem 2a
   put1: 'a -> 'b -> ('a * 'b) list -> ('a * 'b) list
   get1: 'a -> ('a * 'b) list -> 'b option
*)  


let rec put1 givenKey assocKey l =   
  match l with
    [] -> [(givenKey,assocKey)]
  | x::xs-> let (g,a)=x in
	    if g=givenKey then (givenKey,assocKey)::xs
	    else x::(put1 givenKey assocKey xs);;

let get1 givenKey l = 
  let suggestion = List.filter (function (y1,y2) -> givenKey = y1) l in
  match suggestion with
    []->None
  | (x1,x2)::xs -> Some x2;;



(* Problem 2b
   put2: string -> int -> dict2 -> dict2
   get2: string -> dict2 -> int option
*)  



type dict2=Empty | Entry of string*int*dict2;;
let rec put2 s n d = match d with
    Empty -> Entry(s,n,Empty)
  | Entry(a,b,c) -> Entry(s,n,d);;

let rec get2 s d = match d with
    Empty -> None
  | Entry(s1,n1,d1)-> if (s=s1) then Some n1 else get2 s d1;;






(* Problem 2c
   put3: string -> int -> dict3 -> dict3
   get3: string -> dict3 -> int option
*)  


type dict3 = Dict3 of (string -> int option)

let rec put3 s n d = match d with
    Dict3 f -> Dict3 (fun x -> if x=s then Some n else f x)

let get3 s d = match d with
    Dict3 f -> f s;;







(* Problem 3a
   evalAExp: aexp -> float
*)


type op = Plus | Minus | Times | Divide
type aexp = Num of float | BinOp of aexp * op * aexp

let rec evalAExp x = match x with
    Num f -> f
  | BinOp (a,b,c) -> match b with
      Plus -> (evalAExp a) +. (evalAExp c)
    | Minus -> (evalAExp a) -. (evalAExp c)
    | Times -> (evalAExp a) *. (evalAExp c)
    | Divide -> (evalAExp a) /. (evalAExp c);;





(* Problem 3b
   evalRPN: sopn list -> float
*)

(* evalHelper: sopn list -> float list -> float list *)

type sopn = Push of float | Swap | Calculate of op

let evalRPN l = 
 let rec evalHelper l s = 
(
  match l with
     [] -> s
   | x::xs -> match x with
       (Push a) -> evalHelper xs (a::s)
     | (Swap) -> (match s with y1::y2::yx -> y2::y1::yx)
     | (Calculate b) -> match s with (y1::y2::ys) -> 
       match b with
         Plus -> (evalHelper xs ((y2+.y1)::ys))
       | Minus -> (evalHelper xs ((y2-.y1)::ys))
       | Times -> (evalHelper xs ((y2*.y1)::ys))
       | Divide -> (evalHelper xs ((y2/.y1)::ys))
) in
match (evalHelper l []) with z::zs -> z;;





(* Problem 3c
   toRPN: aexp -> sopn list
*)

let rec toRPN aexp = match aexp with
    (Num f) -> [(Push f)]
  | BinOp(a,b,c) -> match b with
      Plus -> (toRPN a)@(toRPN c)@[(Calculate Plus)]
    | Minus -> (toRPN a)@(toRPN c)@[(Calculate Minus)]
    | Times -> (toRPN a)@(toRPN c)@[(Calculate Times)]
    | Divide -> (toRPN a)@(toRPN c)@[(Calculate Divide)];;





(* Problem 3d
   fromRPN: sopn list -> aexp
*)
(* fromRPNHelper: sopn list -> aexp list -> aexp list *)

let fromRPN l = 
 let rec fromRPNHelper l s = 
(
  match l with
     [] -> s
   | x::xs -> match x with
       (Push a) -> fromRPNHelper xs ((Num a)::s)
     | (Swap) -> (match s with y1::y2::ys -> y2::y1::ys)
     | (Calculate b) -> match s with (y1::y2::ys) -> 
       fromRPNHelper xs ((BinOp (y2,b,y1))::ys)
) in
match (fromRPNHelper l []) with z::zs -> z;;





