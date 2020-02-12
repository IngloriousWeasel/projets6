package projet;

import java.awt.Color;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;

public class FigureBase {

	// Mot représentant la figure.
	private String mot;
	// Taille de référence.
	private int cote;
	// Sommet origine de la figure.
	private Point origine;
	// Polygone créé.
	private Polygon polygone;
	// map des points adjacents
	private Map<Point, Set<Point>> adjacent = new HashMap<>();
	// ensemble de point
	private Set<Point> points = new HashSet<>();
	// ensemble des trangles (polygône à 3 côtés) qui pave la figure.
	private Set<Triangle> triangles = new HashSet<>();
	// Liste des losanges composants le polygone.
	private Set<Losange> losanges = new HashSet<>();
	// map de pour la création des voisins
	private Map<Point, Set<Triangle>> triangleOfSommet = new HashMap<>();
	// Liste des motifs du polygone
	private Set<Motif> motifs = new HashSet<>();

//==============================================================================//
	// Constructeur.
	public FigureBase(String mot, int cote, Point origine) {
		super();
		this.mot = mot;
		this.cote = cote;
		this.origine = origine;
		initPolygone();
	}

//==============================================================================//
	// Gettres.
	public String getMot() {
		return mot;
	}

	public int getCote() {
		return cote;
	}

	public Point getOrigine() {
		return origine;
	}

	public Polygon getPolygone() {
		return polygone;
	}

	public Set<Point> getPoints() {
		return points;
	}

	public Set<Triangle> getTriangles() {
		return triangles;
	}

	public Set<Losange> getLosanges() {
		return losanges;
	}
//==============================================================================//
	// Méthodes.
//==============================================================================//

	// Récupère les coordonnées en base 3 des points des sommets.
	private List<Point> creerBordure() {
		Point pointact = new Point(origine);
		List<Point> bordure = new ArrayList<>();
		// On ajoute l'origine.
		bordure.add(origine);
		int k = 1;
		for (char c : mot.toCharArray()) {
			switch (c) {
			case 'u':
				pointact = pointact.newAddP(k, 0, 0);
				bordure.add(pointact);
				k = 1;
				break;
			case 'v':
				pointact = pointact.newAddP(0, k, 0);
				bordure.add(pointact);
				k = 1;
				break;
			case 'w':
				pointact = pointact.newAddP(0, 0, k);
				bordure.add(pointact);
				k = 1;
				break;
			case '-':
				k = -1;
				break;
			}

		}
		return bordure;
	}

//==============================================================================//

	// Calcul des coordonnées des sommets en base 2.
	private int[][] calculBordure() {

		List<Point> bordure = creerBordure();
		points.addAll(bordure);
		// Tableau de la liste des abscisses et des ordonnées.
		int[][] coords = new int[2][points.size()];
		int i = 0;
		for (Point p : bordure) {
			coords[0][i] = p.getX();
			coords[1][i] = p.getY();
			i++;
		}
		return coords;
	}
//==============================================================================//

	// Initialise le polygone qui sera note scene.
	private void initPolygone() {
		int[][] coords = calculBordure();
		polygone = new Polygon(coords[0], coords[1], points.size());
	}

//==============================================================================//

	// Initialisise les points contenu dans la figure.
	public void initPoints() {
		int minU = points.stream().mapToInt(Point::getU).min().getAsInt();
		int maxU = points.stream().mapToInt(Point::getU).max().getAsInt();
		int minV = points.stream().mapToInt(Point::getV).min().getAsInt();
		int maxV = points.stream().mapToInt(Point::getV).max().getAsInt();
		int minW = points.stream().mapToInt(Point::getW).min().getAsInt();
		int maxW = points.stream().mapToInt(Point::getW).max().getAsInt();
		for (int u = minU; u <= maxU; u++) {
			for (int v = minV; v <= maxV; v++)
				for (int w = minW; w <= maxW; w++) {
					Point p = new Point(u, v, w);
					if (polygone.contains(p.getX(), p.getY()))
						points.add(p);

				}
		}
	}
//==============================================================================//

	// Initialise les voisins de chacun des points de la figure.
	public void initAdjacentsPoint() {
		for (Point p1 : points) {
			Set<Point> voisins = new HashSet<>();
			for (Point p2 : points) {
				if (p2.isAdjacent(p1))
					voisins.add(p2);
			}
			adjacent.put(p1, voisins);
		}
	}

//==============================================================================//
	// Initialisation pavage en triangles.
	public void initTriangle() {
		for (Point p1 : points) {
			System.out.println("=============================================");
			for (Point p2 : adjacent.get(p1)) {
				System.out.println("--------");
				for (Point p3 : adjacent.get(p2)) {
					System.out.println("carlos");
					if (adjacent.get(p1).contains(p3) && !p3.equals(p1)) {
						Triangle triangle = new Triangle(p3, p2, p1);
						triangles.add(triangle);
					}
				}
			}
		}
	}

////==============================================================================//
//	
	// Initialise les voisins des triangles.
	public void initAdjacentTriangle() {
		for (Triangle t : triangles) {
			Set<Triangle> trianglePCommun = triangleOfSommet.get(t.getBottom());
			trianglePCommun.retainAll(triangleOfSommet.get(t.getTop()));
			for (Triangle tTest : trianglePCommun) {
				if (!tTest.equals(t))
					t.addAdjacent(tTest);
			}
			trianglePCommun = triangleOfSommet.get(t.getBottom());
			trianglePCommun.retainAll(triangleOfSommet.get(t.getMiddle()));
			for (Triangle tTest : trianglePCommun) {
				if (!tTest.equals(t))
					;
				t.addAdjacent(tTest);
			}
			trianglePCommun = triangleOfSommet.get(t.getMiddle());
			trianglePCommun.retainAll(triangleOfSommet.get(t.getTop()));
			for (Triangle tTest : trianglePCommun) {
				if (!tTest.equals(t))
					;
				t.addAdjacent(tTest);
			}
		}
	}

//	
////==============================================================================//
//	
	// Initialise les losanges.
	public void initLosanges() {
		Losange l;
		// Indice de fin de bouvle while.
		for (Triangle t : triangles) {
			if (!t.getInLosange()) {
				for (Triangle t2 : t.getAdjacents()) {
					if (!t2.getInLosange()) {
						if (t.getType().equals(Type.GAUCHE)) {
							l = new Losange(t, t2);
						} else {
							l = new Losange(t2, t);
						}
						t.setLosange(l);
						t2.setLosange(l);
						t.setInLosange(true);
						t2.setInLosange(true);
						losanges.add(l);
						break;
					}
				}
			}
		}
	}

////==============================================================================//
//
//	Initialise les voisins des triangles.
//	/*public void initAdjacentlosange() {
//		for (int i = 0; i < losanges.size(); i++) 
//			for (int j = i + 1; j < losanges.size(); j++) 
//				if (losanges.get(i).getTriangle1().getAdjacents().contains(losanges.get(j).getTriangle1()) 
//						|| losanges.get(i).getTriangle1().getAdjacents().contains(losanges.get(j).getTriangle2())
//						|| losanges.get(i).getTriangle2().getAdjacents().contains(losanges.get(j).getTriangle1()) 
//						|| losanges.get(i).getTriangle2().getAdjacents().contains(losanges.get(j).getTriangle2()))
//					losanges.get(i).addAdjacent(losanges.get(j));	
//	}*/
//	
////==============================================================================//	
//	
//	// Initialise les motifs composants la figure.
//	public void initMotif() {
//		for(Losange l:this.losanges) {
//			if(l.getCouleur().equals(Color.white)) {
//				for(Losange l2:l.getAdjacents()) {
//					if(l2.getCouleur().equals(Color.GRAY)) {
//						for (Losange l3:l2.getAdjacents()) {
//							if(l3.getCouleur().equals(Color.darkGray)&&l3.getAdjacents().contains(l))
//								this.motifs.add(new Motif(l, l2, l3));
//								
//						}
//					}
//				}
//			}
//		}
//	}
//
//	public Set<Motif> getMotifs() {
//		return motifs;
//	}
//=======================================================================================================
//determine le type d'un triangle:
//	public boolean type1(Point a, Point b, Point c) {
//		return (a.xValue()==b.xValue()&&c.xValue()<a.xValue())||(a.xValue()==c.xValue()&&b.xValue()<a.xValue())||(b.xValue()==c.xValue()&&a.xValue()<b.xValue());
//	}
//	public void updateVoisinsL(Losange l) {
//		l.getAdjacents().removeAll(l.getAdjacents());
//		for(Triangle t : l.getTriangle1().getAdjacents()) {
//			if(!t.equals(l.getTriangle2())){
//				l.addAdjacent(t.getLosange());			}
//		}
//		for(Triangle t : l.getTriangle2().getAdjacents()) {
//			if(!t.equals(l.getTriangle1 ())){
//				l.addAdjacent(t.getLosange());
//				}
//		}
//		
//	}
//methode pour remplir la map qui a un point associe les trinagles dont il est le sommet
	private void addMap(Triangle triangle) {
		for (Point p : triangle.getSommets()) {
			if (triangleOfSommet.containsKey(p))
				triangleOfSommet.get(p).add(triangle);
			else {
				Set<Triangle> nouveauSet = new HashSet();
				nouveauSet.add(triangle);
				triangleOfSommet.put(p, nouveauSet);
			}
		}
	}
}
