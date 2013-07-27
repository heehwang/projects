(* 
   Name: Hee Hwang
   UID: 804212513

   Others With Whom I Discussed Things: 
   Hesam Samimi, Jerry Wang

   Other Resources I Consulted: 
   google.com
   stackoverflow.com
   piazza.com   
*)

exception ImplementMe of string
exception DynamicTypeError
exception MatchFailure  
exception StaticTypeError



(* Helper function for eval
   Input:  pattern, value, and environment 
   Output:  a new environment
*)

let rec addToEnv p v env= (match (p,v) with
    ((IntPat i1),(IntVal i2)) -> 
      if (i1=i2) then env
      else raise MatchFailure

  | ((BoolPat b1),(BoolVal b2)) ->
    if (b1=b2) then env
    else raise MatchFailure

  | ((WildcardPat w1),_) -> env

  | (VarPat (s1,t1),_) -> Env.add_binding s1 v env

  | ((TuplePat l1),(TupleVal l2)) -> (match (l1,l2) with
      ([],[])-> env
    | (_,[]) -> raise MatchFailure
    | ([],_) -> raise MatchFailure
    | (pattern::patterns,value::values) -> 
      (let newEnv = addToEnv pattern value env in
       addToEnv (TuplePat (patterns)) (TupleVal (values)) newEnv
      )
  )
  | _ -> raise MatchFailure
)


(*----------------------------------------------------------------------------*) 

let rec eval (e:moexpr) (env:moenv) : movalue =
  match e with
    IntConst i -> IntVal i

  | BoolConst b -> BoolVal b

  | Var s  -> (try (Env.lookup s env) with    
    Env.NotBound -> raise DynamicTypeError)    

  | Plus (e1,e2) -> (match ((eval e1 env),(eval e2 env)) with
		       ((IntVal i1),(IntVal i2)) -> IntVal (i1+i2)
		     | _ -> raise DynamicTypeError)

  | If(guard,thn,els) -> (match (eval guard env) with
      BoolVal b -> (match b with
	true -> eval thn env
      | false -> eval els env)
    | _ -> raise DynamicTypeError)

  | Tuple l -> (let tupleHelper = (fun l1 l2 -> (eval l1 env)::l2) in
		TupleVal (List.fold_right tupleHelper l []))

  | Let ((pat1,expr1),expr2) -> 
    (let value1 = (eval expr1 env) in
    let env2 = (addToEnv pat1 value1 env) in
    (eval expr2 env2))      

  | Function (p1,e1) -> FunctionVal (p1,e1,env)

  | FunctionCall (e1,e2) -> 
    (let fv1 = (eval e1 env) in 
     match fv1 with
       FunctionVal (fv1Pat,fv1Expr,fv1Env) -> 
	 let v1 = eval e2 env in
	 let env2 = addToEnv fv1Pat v1 fv1Env in
	 eval fv1Expr env2    
     | _ -> raise DynamicTypeError)

  | Match (expr,lst) ->  match lst with
    [] -> raise MatchFailure
    | (pat1,expr1)::xs -> try ( let val1 = eval expr env in
				let newEnv = addToEnv pat1 val1 env in
				eval expr1 newEnv) with
      MatchFailure -> eval (Match (expr,xs)) env


(*----------------------------------------------------------------------------*)

(* Helper function for typecheck
   Input: a pattern and a tenv
   Output: a type for the pattern and a new tenv 
*)

let rec addToTenv p tenv = 
  (match p with
    IntPat i -> (IntType, tenv)
  | BoolPat b -> (BoolType, tenv)
  | WildcardPat mt -> (mt, tenv)
  | VarPat (v,mt) -> (mt, (Env.add_binding v mt tenv))
  | TuplePat l -> (match l with
      [] -> (UnitType, tenv)   
    | (x::xs)-> (let (newType,newTenv) = addToTenv x tenv in
		let (tailType ,tailEnv) = addToTenv (TuplePat xs) newTenv in
                let finalType = (match tailType with 
		    UnitType -> (TupleType [])
		  | (TupleType tail) ->  TupleType (newType::tail)) in
		let finalEnv = (Env.combine_envs newTenv tailEnv) in
		(finalType, finalEnv))
    | _ -> raise StaticTypeError))

(*----------------------------------------------------------------------------*)

let rec typecheck (e:moexpr) (tenv:motenv) :motyp =
  match e with
    IntConst i -> IntType

  | BoolConst b -> BoolType

  | Var s -> (try (Env.lookup s tenv) with
    Env.NotBound -> raise StaticTypeError)

  | Plus (e1,e2) -> (
    if ((typecheck e1 tenv = IntType) && (typecheck e2 tenv = IntType))
    then IntType else raise StaticTypeError)

  | If (cond,thn,els) -> (let condt = (typecheck cond tenv) in 
     match condt with
       BoolType -> (if((typecheck thn tenv) = (typecheck els tenv)) 
	 then (typecheck thn tenv) 
	 else raise StaticTypeError)
     | _ -> raise StaticTypeError)

  | Tuple l -> (match l with
      []-> UnitType
    | x::xs -> (let tupleHelper = (fun l1 l2 -> (typecheck l1 tenv)::l2) in
	   TupleType (List.fold_right tupleHelper l [])))

  | Let ((p1,e1),e2) -> 
    (let t1 = (typecheck e1 tenv) in
    let (newType,newTenv) = addToTenv p1 tenv in
    if (t1 = newType) 
    then  typecheck e2 newTenv
	  else raise StaticTypeError)
  
  | Function (p1,e1) -> 
    let (typeX,tenv1) = addToTenv p1 tenv in
    let typeY = typecheck e1 tenv1 in
    ArrowType (typeX,typeY)
                                 
  | FunctionCall(e1,e2) -> 
    (let fType = (typecheck e1 tenv) in
     let argType = (typecheck e2 tenv) in
     (match fType with 
       ArrowType (xType,yType) -> (if (argType=xType)
	 then yType 
	 else raise StaticTypeError)
     | _ -> raise StaticTypeError))

(* check only pattern. *)
  | Match(expr,lst) -> (match lst with
    [] -> raise StaticTypeError
    | (pat1,expr1)::xs -> (try (let (newType,newTenv) = addToTenv pat1 tenv in
			       typecheck expr1 newTenv) with
      StaticTypeError -> typecheck (Match (expr,xs)) tenv))



(*----------------------------------------------------------------------------*)
