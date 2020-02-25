package projet;

import java.awt.Color;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Triangle {
	private Point middle,bottom, top ;
	
	private Type type;
	// Liste des troies sommets du triangle.
	private Set<Point> sommets = new HashSet<>();
	// Polygône associé au triangle.
	private Polygon polygone;
	// Liste des voisins du triangle.
	private Set<Triangle> adjacents = new HashSet<>();
	// Boolean permettant de voir si le triangle appartient à un losange.
	private boolean inLosange = false;
	//attribut Losange qui indique a quel losange appartient le dit triangle
	private Losange losange;
//==============================================================================//

	// Constructeur.
	public Triangle(Point p1, Point p2, Point p3) {
		this.sommets.add(p1);
		this.sommets.add(p2);
		this.sommets.add(p3);
		initType(p1,p2,p3);
		inLosange=false;
		initPolygone();
	}

//==============================================================================//

	// Getters.
	public Point getBottom() {
		return bottom;
	}
	
	public void setBottom(Point bottom) {
		this.bottom = bottom;
	}
	
	public Point getMiddle() {
		return middle;
	}
	
	public Point getTop() {
		return top;
	}
	
	public Type getType() {
		return type;
	}
	
	public Set<Point> getSommets() {
		return sommets;
	}

	public Polygon getPolygone() {
		return polygone;
	}
	
	public Set<Triangle> getAdjacents() {
		return adjacents;
	}
	
	public boolean getInLosange() {
		return inLosange;
	}
	
	public Losange getLosange() {
		return losange;
	}
//==============================================================================//
	
	//setters
	
	public void setInLosange(boolean inLosange) {
		this.inLosange = inLosange;
	}
	
	public void setLosange(Losange losange) {
		this.losange = losange;
	}
//==============================================================================//
	
	// Méthodes.




	// Redéfinition de equals.
	@Override
	public boolean equals(Object other) {
	     if (other == null) return false;
	     if (!(other instanceof Triangle)) return false;
	     if (this == other) return true;
	     Triangle triangle = (Triangle)other;
	     return sommets.equals(triangle.getSommets());
	}
	@Override 
	public int hashCode() {
		return sommets.hashCode();
	}
		
//==============================================================================//
	
	// Ajoute un voisin.
	public void addAdjacent(Triangle t) {
		if (!adjacents.contains(t)) {
			adjacents.add(t);
			if (!t.adjacents.contains(this))
				t.getAdjacents().add(this);
		}
	}
	
//============================================================================//

	
	public void initType(Point p1, Point p2, Point p3) {
		int diff=p1.getY()-p2.getY();
		if ( diff>0 ) {
			if( diff==JFrameGraphics.dimCote ) {
				top=p2; 
				middle=p3;
				bottom=p1;
			}
			else if(p3.getY()<p1.getY()) {
				top=p3; 
				middle=p2;
				bottom=p1;
			}
			else {
				top=p2; 
				middle=p1;
				bottom=p3;
			}
		}
		else {
			if( diff==-JFrameGraphics.dimCote ) {
				top=p1; 
				middle=p3;
				bottom=p2;
			}
			else if ( p3.getY()<p1.getY()) {
				top=p3; 
				middle=p1;
				bottom=p2;
			}
			else {
				top=p1; 
				middle=p2;
				bottom=p3;
			}
		}
		if(middle.getX()>top.getX())
			type=Type.DROITE;
		else
			type=Type.GAUCHE;
	}
	
	private void initPolygone(){
		int[] xValues = {top.getX(),middle.getX(),bottom.getX()};
		int[] yValues = {top.getY(),middle.getY(),bottom.getY()};
		this.polygone=new Polygon(xValues, yValues, 3);
	}

	
}
