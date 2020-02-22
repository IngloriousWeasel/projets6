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

	public Map<Point, Set<Point>> getAdjacent() {
		return adjacent;
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
	public Map<Point,Set<Triangle>> getTriangleOfSommet(){
		return triangleOfSommet;
	}
	
	
	public Set<Motif> getMotifs() {
		return motifs;
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
			for (Point p2 : adjacent.get(p1)) {
				for (Point p3 : adjacent.get(p2)) {
					if (adjacent.get(p1).contains(p3) && !p3.equals(p1)) {
						Triangle triangle = new Triangle(p3, p2, p1);
						triangles.add(triangle);
						addMap(triangle);
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
			Set<Triangle> trianglePCommun=
					new HashSet(triangleOfSommet.get(t.getBottom()));
			trianglePCommun.retainAll(triangleOfSommet.get(t.getTop()));
			for (Triangle tTest : trianglePCommun) {
				if (!tTest.equals(t))
					t.addAdjacent(tTest);
			}
			trianglePCommun=new HashSet(triangleOfSommet.get(t.getBottom()));
			trianglePCommun.retainAll(triangleOfSommet.get(t.getMiddle()));
			for (Triangle tTest : trianglePCommun) {
				if (!tTest.equals(t))
					t.addAdjacent(tTest);
			}
			trianglePCommun=new HashSet(triangleOfSommet.get(t.getBottom()));
			trianglePCommun.retainAll(triangleOfSommet.get(t.getTop()));
			for (Triangle tTest : trianglePCommun) {
				if (!tTest.equals(t))
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
		for (Triangle t1 : triangles) {
			if (!t1.getInLosange()) {
				for (Triangle t2 : t1.getAdjacents()) {
					if (!t2.getInLosange()) {
						if (t1.getType()==Type.GAUCHE)
							l = new Losange(t1, t2);
						else 
							l = new Losange(t2, t1);
						t1.setLosange(l);
						t2.setLosange(l);
						t1.setInLosange(true);
						t2.setInLosange(true);
						losanges.add(l);
						break;
					}
				}
			}
		}
	}

//============================================================================//
//initialise les voisins des losange
	public void initVoisinnageLosange() {
		for( Losange l : this.losanges ) {
			for ( Triangle t : l.getTriangleG().getAdjacents() ) 
				if( !t.equals( l.getTriangleD() ) && t.getInLosange() )
					l.addAdjacent( t.getLosange() );
			for ( Triangle t : l.getTriangleD().getAdjacents() ) 
				if( !t.equals( l.getTriangleG() ) && t.getInLosange() )
					l.addAdjacent( t.getLosange() );	
		}
	}
//============================================================================//	
//	
//	Initialise les motifs composants la figure.
	public void initMotif() {
		for ( Losange lh : this.losanges)
			if ( lh.getCouleur().equals(JFrameGraphics.horizontalColor) ) 
				for ( Losange ld : lh.getAdjacents())
					if(ld.getCouleur().equals(JFrameGraphics.rightColor))
						for (Losange lg: ld.getAdjacents()) 
							if(lg.getCouleur().equals(JFrameGraphics.leftColor)
									&&lh.getAdjacents().contains(lg)) 
									motifs.add(new Motif(lh,ld,lg));
	}
			
	
		
//

//=======================================================================================================

//methode pour remplir la map qui a un point associe les trinagles dont il est le sommet
	public void addMap(Triangle triangle) {
		for (Point p : triangle.getSommets()) {
			if (triangleOfSommet.containsKey(p)) {
				triangleOfSommet.get(p).add(triangle);
			}
			else {
				Set<Triangle> nouveauSet = new HashSet();
				nouveauSet.add(triangle);
				triangleOfSommet.put(p, nouveauSet);
			}
		}
	}
}
