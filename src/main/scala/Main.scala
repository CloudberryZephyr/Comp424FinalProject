import scala.annotation.tailrec
import scala.util.Random

object Main {

  @main def main(): Unit = {


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
