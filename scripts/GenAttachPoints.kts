print("Number of attach points on left hand side: ")
val numL = readln().toInt()
print("Number of attach points on right hand side: ")
val numR = readln().toInt()

val leftPoints = List(numL) { i ->
	listOf(0, (i+1)/(numL+1).toDouble())
}
val rightPoints = List(numR) { i ->
	listOf(1, ((i+1)/(numR+1).toDouble()))
}

println("points=${leftPoints+rightPoints};")