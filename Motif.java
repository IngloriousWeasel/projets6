package projet;

import java.awt.Color;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Motif {
//============================================================================//
	

	//losange Horizontal <> : 
	Losange lHorizontal;
	
	//losange a Gauche |\
	//                  \|
	Losange lLeft;
	
	//losange a Droite /|
	//                |/
	Losange lRight;
	
	// Constructeur.
	public Motif( Losange l1, Losange l2, Losange l3) {
		lHorizontal=l1;
		lLeft=l2;
		lRight=l3;
	}
	
//============================================================================//
	// Getters.
	public Losange getlHorizontal() {
		return lHorizontal;
	}

	public Losange getlLeft() {
		return lLeft;
	}

	public Losange getlRight() {
		return lRight;
	}
	
//============================================================================//
	
	//Redéfinition de equals et hashCode
	
	@Override
	public boolean equals( Object o ) {
		if ( o == null ) return false;
		if ( o instanceof Motif && this == o ) return true;
		Motif motif = ( Motif )o;
		return this.lHorizontal.equals( motif.lHorizontal )&&
				this.lLeft.equals( motif.lLeft )&&
				this.lRight.equals( motif.lRight );
	}
	
	@Override
	public int hashCode() {
		return lHorizontal.hashCode()+lLeft.hashCode()+lRight.hashCode();
	}

//============================================================================//
	
	//permet d'afficher les motif en RVJ
	public void surbrillance() {
		lHorizontal.setCouleur(Color.GREEN);
		lLeft.setCouleur(Color.yellow);
		lRight.setCouleur(Color.BLUE);
	}
	
//============================================================================//
	
	//effectue un flip;
	public void flip() {
		if(this.lHorizontal.getTriangleD().getTop().getY()
				>this.lLeft.getTriangleD().getTop().getY()) 
			this.down();
		else 
			this.up();
			this.lHorizontal.updateVoisinnage();
			this.lLeft.updateVoisinnage();
			this.lRight.updateVoisinnage();
	}
	

	//effectue un flip vers le bas "retire un cube"
	private void down() {
		Triangle tempon1=lHorizontal.getTriangleG();
		Triangle tempon2=lRight.getTriangleG();
		lHorizontal.setTriangleG( lLeft.getTriangleG() );
		lRight.setTriangleG( tempon1 );
		lLeft.setTriangleG( tempon2 );
		tempon1=getlHorizontal().getTriangleD();
		lHorizontal.setTriangleD( lRight.getTriangleD() );
		lRight.setTriangleD( lLeft.getTriangleD() );
		lLeft.setTriangleD( tempon1 );
	}
	
	
	//effectue un flip vers le haut "ajoute un cube"
	private void up() {
		Triangle tempon1=lHorizontal.getTriangleG();
		lHorizontal.setTriangleG(lRight.getTriangleG());
		lRight.setTriangleG(lLeft.getTriangleG());
		lLeft.setTriangleG(tempon1);
		tempon1=lHorizontal.getTriangleD();
		lHorizontal.setTriangleD(lLeft.getTriangleD());
		lLeft.setTriangleD(lRight.getTriangleD());
		lRight.setTriangleD(tempon1);
	}
	

}
