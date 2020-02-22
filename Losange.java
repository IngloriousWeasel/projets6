package projet;

import java.awt.Color;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Losange {
	//2 triangles du losange.
	private Triangle triangleG;
	
	private Triangle triangleD;
	
	// Couleur utilisé pour l'affichage.
	private Color couleur;
	
	// Liste des losanges adjacents.
	private Set<Losange> adjacents = new HashSet<Losange>();
	
	
	
//=========================================================================================//
	
	
	// Constructeur.
	public Losange(Triangle tg, Triangle td) {
		super();
		this.triangleG=tg;
		this.triangleD=td;
		tg.setInLosange(true);
		td.setInLosange(true);
		//updateSommetsLosange();
		actualiseCouleur();
		//this.updatePolygon();
	}
	
	// Initialisation de la couleur.
	public void actualiseCouleur() {
		
		if (triangleG.getTop().getY() > triangleD.getTop().getY())
			couleur = JFrameGraphics.leftColor;
		else if (triangleG.getTop().getY()==triangleD.getTop().getY())
			couleur = JFrameGraphics.horizontalColor;
		else 
			couleur = JFrameGraphics.rightColor;
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
		return adjacents;
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

//================================================================================//
	// Redéfinition de equals() et hashCode()
	@Override
	public boolean equals( Object o ) {
		if ( o == null ) return false;
		if ( o instanceof Losange && this == o ) return true;
		Losange losange = ( Losange )o;
		return this.triangleD.equals( losange.triangleD )&&
				this.triangleG.equals( losange.triangleG );
	}
	
	@Override
	public int hashCode() {
		return triangleG.hashCode()+triangleD.hashCode();
	}
	
//==============================================================================//
	// Ajoute un voisin.
	public void addAdjacent(Losange l) {
		if (!adjacents.contains(l)) {
			adjacents.add(l);
			if (!l.adjacents.contains(this))
				l.getAdjacents().add(this);
		}
	}
	
	public void removeAdjacent( Losange l) {
		l.getAdjacents().remove( l );
		this.getAdjacents().remove( l );
	}
	
	public void updateVoisinnage() {
		for( Losange l:this.getAdjacents() ) 
			removeAdjacent( l );
		
		for ( Triangle t:triangleG.getAdjacents() ) 
			if( !t.equals(this.getTriangleD() ) && t.getInLosange() )
				this.addAdjacent( t.getLosange() );
		
		for ( Triangle t:triangleD.getAdjacents() ) 
			if( !t.equals( this.getTriangleG() ) && t.getInLosange() ) 
				this.addAdjacent( t.getLosange() );	
				
		
	}
}
