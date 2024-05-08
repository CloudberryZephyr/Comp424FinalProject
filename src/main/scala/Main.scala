import scala.annotation.tailrec
import scala.util.Random
import scala.collection.parallel.CollectionConverters.*

object Main {

  @main def main(): Unit = {
    var bodies: List[Body] = generateBodies(5)
    val dt: Int = 1

    // keep repeating loop
    while(true) {
      // calculate ALL new motions
      bodies.par.map((x)=>(bodies.foldLeft(x)(_.calcMotion(_,dt)))).toList
      // apply ALL new motions
      bodies.par.map(_.applyMotion(dt)).toList
    }
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
}
