package projet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Point {

	// Coordonnées du point dans la base des 3 vecteurs.
	private int u;
	private int v;
	private int w;
	private int x;
	private int y;
	// enemble des points adjacents.
	private Set<Point> adjacents = new HashSet<Point>();
//==============================================================================//
	
	// Constructeur.
	public Point(int u, int v, int w) {
		this.u = 0;
		this.v = v-u;
		this.w = w-u;
		this.x=(int)(0.5 * Math.sqrt(3) * (w-v) * JFrameGraphics.dimCote);
		this.y=(int)((u - 0.5 * (v + w)) * JFrameGraphics.dimCote);
	}
	
	public Point(Point p) {
		this(p.getU(), p.getV(),p.getW());
	}
	
//==============================================================================//
	
	// Getters.
	public int getU() {
		return u;
	}
	
	public int getV() {
		return v;
	}
	
	public int getW() {
		return w;
	}
	
	public Set<Point> getAdjacents() {
		return adjacents;
	}

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
//==============================================================================//
	
	// Méthodes
	
//==============================================================================//

	/*
	 * On compare dans la base a deux vecteurs car il n'y a pas unicité des coordonnées d'un point dans la base a 3 dimensions. 
	 */
	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (o instanceof Point && this == o) return true;
		Point point = (Point)o;
		return (x==point.getX()&&y==point.getY());
	}
	
//==============================================================================//
	 

	
	
	public Point newAddP(int u, int v, int w) {
		return new Point(this.u + u,this.v + v, this.w + w);
	}
	
	@Override
	public int hashCode() {
		return 31*u+17*v+w;
	}
	//essayer trouver une meilleure distance 
	public boolean isAdjacent(Point p) {
		double dx=Math.pow(x-p.getX(), 2);
		double dy=Math.pow(y-p.getY(), 2);
		int dist= (int)Math.sqrt(dx+dy);
		return dist<=JFrameGraphics.dimCote&&!this.equals(p);
//		int dv=Math.abs(p.getV()-v);
//		int dw=Math.abs(p.getW()-w);
//		int manhattan=dv+dw;
//		int diff=Math.abs((v+w)-p.getV()-p.getW());
//		return (diff==1 && manhattan==1)||(diff==2&&manhattan==2);
	}

	
}
