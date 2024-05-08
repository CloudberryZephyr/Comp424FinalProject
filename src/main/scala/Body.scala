class Body(val x: Double, val y: Double, val z: Double, val dx: Double, val dy: Double, val dz: Double, val mass: Double, val radius: Double, val name: String) {

  /**
   * calculates the effect of other Body object upon this Body's velocity parameters
   * @param other the other Body object 
   * @param dt time increment in units
   * @return a Body object with the current velocity parameters
   */
  def calcMotion(other: Body, dt: Int): Body = {
    if (other != this) {
      val velocity = ((6.67 * math.pow(10, -11) * mass * other.mass) / (Math.pow(Math.sqrt(Math.pow(x + other.x, 2) + Math.pow(y + other.y, 2) + Math.pow(z + other.z, 2)), 3))) / mass * dt

      Body(x, y, z, dx + velocity * (x + other.x), dy + velocity * (y + other.y), dz + velocity * (z + other.z), mass, radius, name)
    }
    else {
      this
    }
  }

  /**
   * applies current velocity parameters to update current position parameters
   * @param dt time increment in units
   * @return a Body object with the current position parameters
   */
  def applyMotion(dt: Int): Body = {
    Body(x+dx*dt,y+dy*dt,z+dy*dt,dx,dy,dz,mass,radius,name)
  }
}
