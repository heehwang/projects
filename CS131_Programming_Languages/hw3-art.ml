(*Implementation for art functor *)

module Art =
functor (Expr : EXPR) ->
struct
  type elt = Expr.t

  (* forLoop: int * int * (int -> unit) -> unit Applies the function f to all
     the integers between low and high inclusive; the function returns no useful
     result; it is used only for its side effects (in our case, writing to a
     file).  *)
  let rec forLoop (low,high,f) = if low > high then ()
      else let x = f low in 
    forLoop ((low+1),high,f)

  (* build: int > Expr.t 
     Build an expression tree of the given maximum depth.
  *)
  let rec build depth = 
      if (depth = 0) then match (Random.int 2) with
        0 -> Expr.buildX
      | 1 -> Expr.buildY
     
      else match Random.int 4 with
        0 -> Expr.buildSin (build (depth-1)) 
      | 1 -> Expr.buildCos (build (depth-1))
      | 2 -> Expr.buildAvg ((build (depth-1)),(build (depth-1)))
      | 3 -> Expr.buildMult ((build (depth-1)),(build (depth-1)))
     





  (********************* Bitmap creation code ***************)

  (*
     You should not have to modify the remaining functions.
  *)
  
  (*
   Converts an integer i from the range [-n,n]
   into a float in [-1,1]
   *)
  let toFloat(i,n) = (float i) /. (float n)
    
  (*
   Converts a float in [-1,1] to an integer in the range [0,255]
   *)
  let toIntensity(z) =
    let round f =
      let c = ceil f in
      truncate(if (c -. f) <= 0.5 then c else c -. 1.)
    in
    round(127.5 +. (127.5 *. z))


  (* Creates a file name for the given parameters. *)
  let artFileName (depth, seed) =
    "art-"^(string_of_int depth)^"-"^(string_of_int seed)

      
  (* emitGrayscale :  ((float * float) -> float) * int * string -> unit
     emitGrayscale(f, n, name) emits the values of the expression f
     (converted to intensity) to a file name.pgm for a 2n+1 by 2n+1 grid of
     points taken from [-1,1] x [-1,1].
 
     You can "man pgm" on the SEAS Unix machines for a full description of
     the file format, but it's essentially a one-line header followed by
     one byte (representing gray value 0..255) per pixel.
   *)
  let emitGrayscale (f,n,name) =
    (* Open the output file and write the header *)
    let stream = open_out (name ^ ".pgm") in
    set_binary_mode_out stream true;
    (* Picture will be 2*n+1 pixels on a side *)
    let numPixels = n*2+1 in 
    let _ = output_string stream ("P5 " ^ (string_of_int numPixels) ^ " " ^
      (string_of_int numPixels) ^ " 255\n") in
      
    let _ =
      forLoop (-n, n,
               fun ix ->
                 forLoop (-n, n,
                          fun iy ->
                            (* Convert grid locations to [-1,1] *)
                            let x = toFloat(ix,n) in
                            let y = toFloat(iy,n) in
                              
                            (* Apply the given random function *)
                            let z = f(x,y) in
                              
                            (* Convert the result to a grayscale value *)
                            let iz = toIntensity z in
  			    (* Emit one byte for this pixel *)
  			    output_byte stream iz))
    in close_out stream

    
  (* doRandomGray : int * int -> unit
     Given a depth and a seed for the random number generator,
     create a single random expression and convert it to a
     grayscale picture.
   *)
  let doRandomGray (depth,seed) =
        (* Initialize random-number generator g *)
  	let _ = Random.init seed in
        
  	(* Generate a random expression, and turn it into an ML function *)
	let e = build depth in
  	let f = Expr.eval e in
		  
  	(* 301 x 301 pixels *)
  	let n = 150 in
		  
  	let name = artFileName(depth, seed) in
  	print_endline (Expr.exprToString e);
  	(* Emit the picture *)
  	emitGrayscale(f,n,name)

  
  (* emitColor : (float*float->float) * (float*float->float) *
  		 (float*float->float) * int * string -> unit
     emitColor(f1, f2, f3, n, name) emits the values of the expressions
     f1, f2, and f3 (converted to RGB intensities) to the output
     file name.ppm (note the different extension) for a 2n+1 by 2n+1
     grid of points taken from [-1,1] x [-1,1].
 
     The file format is essentially a one-line header followed by
     three bytes (representing red, green, and blue values in the
     range 0..255) per pixel.
   *)
  let emitColor (f1,f2,f3,n,name) =
    let stream = open_out (name^".ppm") in
    set_binary_mode_out stream true;		       

    let numPixels = n*2+1 in
    let _ = output_string stream ("P6 " ^ (string_of_int numPixels) ^ " " ^
  				       (string_of_int numPixels) ^ " 255\n") in
		  
    let _ =
      forLoop (-n, n,
  	       fun ix ->
  		 forLoop (-n, n,
  			  fun iy ->
  			    (* Map grid locations into [-1,1] *)
  			    let x = toFloat(ix,n) in
  			    let y = toFloat(iy,n) in
					  
  			    (* Apply the given random functions *)
  			    let z1 = f1(x,y) in
  			    let z2 = f2(x,y) in
  			    let z3 = f3(x,y) in
			    
  			    (* Convert the result to R, G, and B values *)
  			    let iz1 = toIntensity(z1) in
  			    let iz2 = toIntensity(z2) in
  			    let iz3 = toIntensity(z3) in
  			    (* Emit three bytes for this pixel *)
  			    output_byte stream iz1;
  			    output_byte stream iz2;
  			    output_byte stream iz3))
    in close_out stream
	      
  	 
  (* doRandomColor : int * int -> unit
     Given a depth and a seed for the random number generator,
     create a three random expressions and convert them to a
     color picture.
   *)
  let doRandomColor (depth,seed) =
    (* Initialize random-number generator g *)
    let _ = Random.init seed in
		  
    (* Generate a random expressions, and turn them into ML functions *)
    let e1 = build depth in
    let e2 = build depth in
    let e3 = build depth in
    let f1 = Expr.eval e1 in
    let f2 = Expr.eval e2 in
    let f3 = Expr.eval e3 in
		   
    let _ = (print_string "red   = "; print_endline (Expr.exprToString e1)) in
    let _ = (print_string "green   = "; print_endline (Expr.exprToString e2)) in
    let _ = (print_string "blue   = "; print_endline (Expr.exprToString e3)) in
		  
    (* Open the output file and write the header *)
    let n = 150 in		  
    let name = artFileName(depth,seed) in
    (* Emit the picture *)
    emitColor(f1,f2,f3,n,name)

      
  (* doManyRandom: ((int * int -> unit) * int * int * int) -> unit
     f is intended to be either doRandomGray or doRandomColor above
     Invokes f(depth, seedStart); f(depth, seedStart+1); ... ; f(depth, seedEnd)
  *)   
  let doManyRandom (f, depth, seedStart, seedEnd) =
    forLoop (seedStart, seedEnd, fun s -> f(depth, s))
end



