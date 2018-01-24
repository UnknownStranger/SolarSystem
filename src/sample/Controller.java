package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Slider;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

	//setting variables for speed and scale differences
	double mercuryOrbit = 4.149377 , venusOrbit = 1.626016, earthOrbit = 1, marsOrbit = 0.531914, moonOrbit = 13.368983;
	double mercuryDistance = 80, venusDistance = 140, earthDistance = 200, marsDistance = 260, moonDistance = 30;
	double mercuryAngle = 0, venusAngle = 0, earthAngle = 0, marsAngle = 0, moonAngle = 0;
	double sunLocation = 550;

	//modify this to change speed of calculations and thereby speed of orbits while keeping all accurate
	private double refreshRate = 5;

	//temporary size holder
	private double radius;

	//grabbing objects from fxml to use in controller
	@FXML
	public Circle sun;
	public Canvas canvas;
	public Circle mercury;
	public Circle venus;
	public Circle earth;
	public Circle moon;
	public Circle mars;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		Timeline timeline = new Timeline(
				new KeyFrame(Duration.millis(refreshRate),
						new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent actionEvent) {
								mercury.setRadius(earth.getRadius() * 0.383);
								venus.setRadius(earth.getRadius() * 0.949);
								mars.setRadius(earth.getRadius() * 0.532);
								moon.setRadius(earth.getRadius() * 0.2724);
								mercuryAngle = orbitCalculations(mercury, mercuryDistance, mercuryOrbit, mercuryAngle, sunLocation, sunLocation);
								venusAngle = orbitCalculations(venus, venusDistance, venusOrbit, venusAngle, sunLocation, sunLocation);
								earthAngle = orbitCalculations(earth, earthDistance, earthOrbit, earthAngle, sunLocation , sunLocation);
								moonAngle = orbitCalculations(moon, moonDistance, moonOrbit, moonAngle,earth.getCenterX(), earth.getCenterY());
								marsAngle = orbitCalculations(mars, marsDistance, marsOrbit, marsAngle, sunLocation ,sunLocation);
							}
						}));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
	}

	private double orbitCalculations(Circle planet, double distance,double angleModifier, double angle, double objectX, double objectY){
			angle += (0.001 * Math.PI) * angleModifier;//distance of orbit traveled per refresh
			double x = (int)( distance*Math.cos(angle) );//calculating new center points for draw
			double y = (int)( distance*Math.sin(angle) );
			planet.setCenterX(objectX + x);//setting new center points
			planet.setCenterY(objectY + y);
			return angle;
	}
}
