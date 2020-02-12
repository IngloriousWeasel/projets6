package projet;

import java.awt.Color;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Losange {

	public void setCouleur(Color couleur) {
		this.couleur = couleur;
	}
	// List des 2 triangles du losange.
	private Triangle triangle1;
	private Triangle triangle2;
	// Couleur utilisé pour l'affichage.
	private Color couleur;
	// Liste des losanges adjacents.
	private List<Losange> adjacents = new ArrayList<Losange>();

	// Constructeur.
	public Losange(Triangle t1, Triangle t2) {
		super();
		this.triangle1=t1;
		this.triangle2=t2;
		t1.setInLosange(true);
		t2.setInLosange(true);
		//updateSommetsLosange();
		//actualiseCouleur();
		//this.updatePolygon();
	}

	// Getters.
	public Triangle getTriangle1() {
		return triangle1;
	}
	public Triangle getTriangle2() {
		return triangle2;
	}

	public Color getCouleur() {
		return couleur;
	}

	public List<Losange> getAdjacents() {
		return adjacents;
	}
	
//==============================================================================//
//	// Méthodes.
//	// Initialisation des sommets du losange.
//	public void updateSommetsLosange() {
//		sommets.removeAll(sommets);
//		List<Point> pointsCommun = triangle1.pointsCommun(triangle2);
//		sommets.add(pointsCommun.get(0));
//		sommets.add(triangle1.pointsNonCommun(triangle2));
//		sommets.add(pointsCommun.get(1));
//		sommets.add(triangle2.pointsNonCommun(triangle1));
//	}
//	
////==============================================================================//
//	// Initialisation de la couleur.
//	public void actualiseCouleur() {
//		Point point1 = Collections.max(sommets, (Point a, Point b)->a.yValue()-b.yValue());
//		Point point2 = Collections.min(sommets, (Point a, Point b)->a.yValue()-b.yValue());
//		if (point1.xValue() > point2.xValue())
//			couleur = Color.darkGray;
//		else if (point1.xValue() == point2.xValue())
//			couleur = Color.white;
//		else 
//			couleur = Color.gray;
//	}
//	
////==============================================================================//
//
//	public void setTriangle1(Triangle triangle1) {
//		this.triangle1 = triangle1;
//	}
//
//	public void setTriangle2(Triangle triangle2) {
//		this.triangle2 = triangle2;
//	}
//
//	// Redéfinition de equals.
//	@Override
//	public boolean equals(Object o) {
//		if (o == null) return false;
//		if (o instanceof Losange && this == o) return true;
//		Losange losange = (Losange)o;
//		if (sommets.size() != losange.getSommets().size()) return false;
//		for (int i = 0; i < sommets.size(); i++) 
//			if (!sommets.contains(losange.getSommets().get(i))) return false;
//		return true;
//	}
//	
////==============================================================================//
//	// Ajoute un voisin.
//	public void addAdjacent(Losange l) {
//		if (!adjacents.contains(l)) {
//			adjacents.add(l);
//			if (!l.adjacents.contains(this))
//				l.getAdjacents().add(this);
//		}
//	} 
//	public void updatePolygon() {
//		int[] xValues = {sommets.get(0).xValue(), sommets.get(1).xValue(), sommets.get(2).xValue(), sommets.get(3).xValue()};
//		int[] yValues = {sommets.get(0).yValue(), sommets.get(1).yValue(), sommets.get(2).yValue(), sommets.get(3).yValue()};
//		this.polygone = new Polygon(xValues, yValues, 4);
//	}
}
