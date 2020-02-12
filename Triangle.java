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
	public boolean equals(Object o) {
		if (o == null) return false;
		if (o instanceof Triangle && this == o) return true;
		Triangle triangle = (Triangle)o;
		if (sommets.size() != triangle.getSommets().size()) return false;
		for (Point p: triangle.getSommets()) 
			if (!sommets.contains(p)) return false;
		return true;
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
	
//==============================================================================//
//	
//	// Récupère les points en commun de deux triangles.
//	public List<Point> pointsCommun(Triangle t) {
//		List<Point> pointsCommun = new ArrayList<Point>();
//		for (int i = 0; i < sommets.size(); i++) {
//			if (t.getSommets().contains(sommets.get(i)))
//				pointsCommun.add(sommets.get(i));
//		}
//		return pointsCommun;
//	}

//==============================================================================//
	
//	// Récupère les points en commun de deux triangles.
//	public Point pointsNonCommun(Triangle t) {
//		for (int i = 0; i < sommets.size(); i++) {
//			if (!t.getSommets().contains(sommets.get(i)))
//				return sommets.get(i);
//		}
//		return null;
//	}
	public void initType(Point p1, Point p2, Point p3) {
		int diff=p1.getY()-p2.getY();
		if(diff==JFrameGraphics.dimCote) {
			top=p1;
			bottom=p2;
			middle=p3;
		}
		else if(diff==-JFrameGraphics.dimCote) {
			top=p2;
			bottom=p1;
			middle=p3;
		}
		else if(diff>0) {
			middle=p1;
			bottom=p2;
			top=p3;
		}
		else {
			middle=p2;
			bottom=p1;
			top=p3;
		}
	}
	
	private void initPolygone(){
		int[] xValues = {top.getX(),middle.getX(),bottom.getX()};
		int[] yValues = {top.getY(),middle.getY(),bottom.getY()};
		this.polygone=new Polygon(xValues, yValues, 3);
	}
	
}
