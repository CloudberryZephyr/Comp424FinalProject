import scala.annotation.tailrec
import scala.util.Random
import scala.collection.parallel.CollectionConverters.*


  @main def main(): Unit = {

    val dt = 1
    var nBodies: List[Body] = generateBodies(5000)
    
    val (time, x) = timeIt(
    // keep repeating loop
    for( x <- 0 until 50) {
      // calculate ALL new motions
      nBodies = calculateAllNewMotions(nBodies,dt)
      // apply ALL new motions
      nBodies = applyAllNewMotions(nBodies,dt)
    })
    println(time)
  }

  def calculateAllNewMotions(nBodies: List[Body],dt: Int):List[Body] = {
    nBodies.par.map((x)=>(nBodies.foldLeft(x)(_.calcMotion(_,dt)))).toList
  }

  def applyAllNewMotions(nBodies: List[Body], dt: Int): List[Body] = {
    nBodies.par.map((x) => (x.applyMotion(dt))).toList
  }

  @tailrec
  def generateBodies(numBodies : Int, bodies: List[Body] = List[Body]()): List[Body] = {
    if (bodies.length == numBodies) {
      return bodies
    } else {
      val x : Double = Random().nextDouble() * 100
      val y : Double = Random().nextDouble() * 100
      val z : Double = Random().nextDouble() * 100
      
      val dx : Double = Random().nextDouble() * 10
      val dy : Double = Random().nextDouble() * 10
      val dz : Double = Random().nextDouble() * 10
      
      val mass : Double = new Random().nextDouble() * 10000
      val radius : Double = new Random().nextDouble() * 1000
      val name : String = "body" + bodies.length
      
      val newBody: Body = new Body(x, y, z, dx, dy, dz, mass, radius, name)
      generateBodies(numBodies, bodies ++ List[Body](newBody))
    }
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
    (endTime - startTime, result)
  }
