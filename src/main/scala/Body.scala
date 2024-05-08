class Body(val x: Int, val y: Int, val z: Int, val dx: Int, val dy: Int, val dz: Int, val mass: Int, val radius: Int, val name: String) {
  def calcMotion(other: Body, dt: Int): Body = {
    val velocity = ((6.67*math.pow(10,-11)*mass*other.mass)/(Math.pow(Math.sqrt(Math.pow(x+other.x,2)+Math.pow(y+other.y,2)+Math.pow(z+other.z,2)),2)))/mass*dt


    Body(x,y,z,dx,dy,dz,mass,radius,name)
  }
  def doMotion(dt: Int): Body = {
    Body(x+dx*dt,y+dy*dt,z+dy*dt,dx,dy,dz,mass,radius,name)
  }
}
