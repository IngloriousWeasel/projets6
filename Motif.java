package projet;

import java.awt.Color;


public class Motif {
//============================================================================//
	

	//losange Horizontal <> : 
	private Losange lHorizontal;
	
	//losange a Gauche |\
	//                  \|
	private Losange lLeft;
	
	//losange a Droite /|
	//                |/
	private Losange lRight;
	
	private double E=0.d;
	
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
	
	public double getE() {
		return E;
	}
	
//============================================================================//
	
	//Redéfinition de equals et hashCode
	
	@Override
	public boolean equals( Object o ) {
		if ( o == null ) return false;
		if ( o instanceof Motif && this == o ) return true;
		Motif motif = ( Motif )o;
		return this.lHorizontal.equals( motif.getlHorizontal() )&&
				this.lLeft.equals( motif.getlLeft() )&&
				this.lRight.equals( motif.getlRight() );
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
	
	public void flip(){
		if (isUp()) {
			
			down();
		}
		else {
			
			up();
		}
		updateE();
	}
	


	private boolean isUp() {
		return lHorizontal.getTriangleD().getTop().getY()<lLeft.getTriangleD().getTop().getY();
	}
	

	//effectue un flip vers le bas "retire un cube"
	private void down() {

		Triangle hg = lHorizontal.getTriangleG();
		Triangle hd = lHorizontal.getTriangleD();
		Triangle gg = lLeft.getTriangleG();
		Triangle gd = lLeft.getTriangleD();
		Triangle dg = lRight.getTriangleG();
		Triangle dd = lRight.getTriangleD();
		lHorizontal.setTriangles(gg,dd);
		lRight.setTriangles(hg, gd);
		lLeft.setTriangles(dg, hd);
		lHorizontal.getTriangleD().getTop().deltaDown();
	}
	private void up() {

		Triangle hg = lHorizontal.getTriangleG();
		Triangle hd = lHorizontal.getTriangleD();
		Triangle gg = lLeft.getTriangleG();
		Triangle gd = lLeft.getTriangleD();
		Triangle dg = lRight.getTriangleG();
		Triangle dd = lRight.getTriangleD();
		lHorizontal.setTriangles(dg,gd);
		lLeft.setTriangles(hg,dd );
		lRight.setTriangles(gg, hd);
		lHorizontal.getTriangleD().getBottom().deltaUp();
	}
	
	
	public boolean contains ( Point p ) {
		return lHorizontal.contains(p) 
				||lLeft.contains(p)
				||lRight.contains(p);
	}
	
	public void updateE() {
		double e=0.d;
		for (Losange l : lHorizontal.getAdjacents()) 
			if (l.getCouleur().equals(JFrameGraphics.horizontalColor))
				e++;
		for (Losange l : lLeft.getAdjacents()) 
			if (l.getCouleur().equals(JFrameGraphics.leftColor))
				e++;
		for (Losange l : lRight.getAdjacents()) 
			if (l.getCouleur().equals(JFrameGraphics.rightColor))
				e++;
		this.E=e;
		
		
		
	}

}
