import  java.awt.Color;
object Simulation {
  def solarSystem: Simulation = {
    val s = Simulation()
    s.addSimulationBody(SimulationBody(0,0,696,1_988_500,Color.YELLOW,"The Sun"))
    s.addSimulationBody(SimulationBody(58_000,0,2.5,.330,Color.GRAY,"Mercury"))
    s.addSimulationBody(SimulationBody(108_000,0,6,4.87,Color.LIGHT_GRAY,"Venus"))
    s.addSimulationBody(SimulationBody(149_600,0,6.4,5.97,Color(0,180,0),"Earth"))
    s.addSimulationBody(SimulationBody(227_900,0,3.4,0.642,Color.ORANGE,"Mars"))
    s
  }
}

class Simulation() {
  private var objects: List[SimulationBody] = List();

  def getObjects: List[SimulationBody] = objects;
  def addSimulationBody(s: SimulationBody): Unit = {
    objects :+= s;
  }
  def update(): Unit = {
      // 1 pixel = 1,000 km
      // Object masses are 1/10^24x less than the actual values
      // You may determine how often this method is run
      // TODO: this is the part where someone else implements the physics
      // I recommend first calculating the forces (in parallel)
      // then updating positions in a separate step (also in parallel)
  }
}
