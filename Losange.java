package projet;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

public class Losange implements Comparable<Losange>{
	//2 triangles du losange.
	private Triangle triangleG;
	
	private Triangle triangleD;
	
	// Couleur utilisé pour l'affichage.
	private Color couleur;
	 
	//Liste des motifs auxquels le losange appartient 
	private Set<Motif> inMotifs = new HashSet<>();
	
	
	
//=========================================================================================//
	
	
	// Constructeur.
	public Losange(Triangle t1, Triangle t2) {
		super();
		if (t1.getType().equals(Type.GAUCHE)) {
			this.triangleG=t1;
			this.triangleD=t2;
		}
		else {
			this.triangleG=t2;
			this.triangleD=t1;
		}
		t1.setInLosange(true);
		t2.setInLosange(true);
		actualiseCouleur();
	}
	
	// Initialisation de la couleur.
	public void actualiseCouleur() {
		
		if (triangleG.getTop().getY() > triangleD.getTop().getY())
			couleur = Main.leftColor;
		else if (triangleG.getTop().getY()==triangleD.getTop().getY())
			couleur = Main.horizontalColor;
		else 
			couleur = Main.rightColor;
	}
//===============================================================================//

	// Getters.
	public Triangle getTriangleG() {
		return triangleG;
	}
	public Triangle getTriangleD() {
		return triangleD;
	}


	public Color getCouleur() {
		return couleur;
	}

	public Set<Losange> getAdjacents() {
		Set<Losange> adjacents= new HashSet<>();
		for ( Triangle t : triangleD.getAdjacents()) {
			if ( !t.equals( triangleG ))
				adjacents.add(t.getLosange());
		}
		for ( Triangle t : triangleG.getAdjacents()) {
			if ( !t.equals( triangleD ))
				adjacents.add(t.getLosange());
		}
		adjacents.remove(null);
		return adjacents;
	}
	
	public Set<Motif> getInMotifs() {
		return inMotifs;
	}
	//setter
	public void setCouleur(Color couleur) {
		this.couleur = couleur;
	}
////==============================================================================//

//setters
	public void setTriangleG(Triangle triangleG) {
		this.triangleG = triangleG;
	}

	public void setTriangleD(Triangle triangleD) {
		this.triangleD = triangleD;
	}
	
	public void setTriangles(Triangle g, Triangle d) {
		setTriangleG(g);
		setTriangleD(d);
		g.setLosange(this);
		d.setLosange(this);
	}

//================================================================================//
	// Redéfinition de equals() et hashCode()
	@Override
	public boolean equals( Object o ) {
		if ( o == null ) return false;
		if ( o instanceof Losange && this == o ) return true;
		Losange losange = ( Losange )o;
		return this.triangleD.equals( losange.getTriangleD() )&&
				this.triangleG.equals( losange.getTriangleG() );
	}
	
	@Override
	public int hashCode() {
		return triangleG.hashCode()+triangleD.hashCode();
	}
	
//==============================================================================//
	

	
	public boolean contains ( Point p) {
		return triangleD.contains(p) || triangleG.contains(p);
	}

	@Override
	public int compareTo(Losange o) {
		// TODO Auto-generated method stub
		return 0;
	}
}
