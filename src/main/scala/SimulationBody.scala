import java.awt.Color

class SimulationBody(var x: Float,var y: Float,var radius: Float, val mass: Float, val color: Color, var label: String = "") {
  var velX: Float = 0;
  var velY: Float = 0;
  var accX: Float = 0
  var accY: Float = 0;
}
