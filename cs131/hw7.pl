/*
   Name: Hee Hwang
   UID: 804212513

   Others With Whom I Discussed Things: 
   Hesam Samimi, Jerry Wang

   Other Resources I Consulted: 
   google.com
   piazza.com   
*/


/* Problem 1 */
duplist([],[]).
duplist([Head|Tail],[Head,Head|Y]):-
    duplist(Tail,Y).



/* Problem 2 */
oddsize([_]).
oddsize([_,_|T]):-
    oddsize(T).


/* Problem 3 */

my_sum_list([],0).
my_sum_list([H|T],S1):-
    my_sum_list(T,S2),
    S1 is S2+H.

my_sublist([],[]).
my_sublist([Car|Cdr],[Car|S]):-
    my_sublist(Cdr,S).
my_sublist([_|Cdr],S):-
    my_sublist(Cdr,S).

subsetsum(_,0,[]).
subsetsum(L,Sum,X):-
    my_sublist(L,X),
    my_sum_list(X,Sum).


/* Problem 4 */

move(world([H1|T1],S2,S3,none), pickup(stack1), world(T1,S2,S3,H1)).
move(world(S1,[H2|T2],S3,none), pickup(stack2), world(S1,T2,S3,H2)).
move(world(S1,S2,[H3|T3],none), pickup(stack3), world(S1,S2,T3,H3)).
move(world(S1,S2,S3,Something), putdown(stack1), world([Something|S1],S2,S3,none)).
move(world(S1,S2,S3,Something), putdown(stack2), world(S1,[Something|S2],S3,none)).
move(world(S1,S2,S3,Something), putdown(stack3), world(S1,S2,[Something|S3],none)).    

blocksworld(Goal, Goal, []).

blocksworld(Start, Goal, [Action|Actions]) :-
    move(Start, Action, NextWorld), blocksworld(NextWorld, Goal, Actions).








/* Problem 5 */

typeInfer(intconst(_), _ , int).
typeInfer(boolconst(true), _ , bool).
typeInfer(boolconst(false), _, bool).

typeInfer(plus(E1,E2),Env,int) :-
    typeInfer(E1,Env,int), 
    typeInfer(E2,Env,int).


typeInfer(var(X),Env,Result) :-
    member([X,Result],Env).

typeInfer(if(Cond,Thn,Els),Env,Result) :-
    typeInfer(Cond,Env,bool),
    typeInfer(Thn,Env,Result),
    typeInfer(Els,Env,Result).


/* function- It does two things: 
   1. set return type to arrow(Ftype,Btype)    
   2. add connection for F and B to the environment */
typeInfer(function(F,B),Env,arrow(Ftype,Btype)) :-
    typeInfer(B, [[F,Ftype]|Env], Btype).
    

/* funCall- Check input and output types. */
typeInfer(funCall(Func,A),Env,Btype) :-
    typeInfer(Func,Env,arrow(Ftype,Btype)),
    typeInfer(A,Env,Ftype).
  



