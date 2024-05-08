
@main def test(): Unit = {
  val dt = 1
  var nBodies: List[Body] = generateBodies(5000)
  // keep repeating loop
  val (time, x) = timeIt ( 
    for (i <- 0 until 50) {
    // calculate ALL new motions
    nBodies = calculateAllNewMotions(nBodies, dt)
    // apply ALL new motions
    nBodies = applyAllNewMotions(nBodies, dt)
  })
  println(time)
}

/**
 * Returns both the answer and the clock time required to compute it for any function
 *
 * @param f the expression to be executed
 * @tparam A the type to which f evaluates
 * @return the time to compute and value of f
 */
def timeIt[A](f: => A): (Double, A) = {
  val startTime = System.currentTimeMillis()
  val result = f
  val endTime = System.currentTimeMillis()
  (endTime-startTime, result)
}
