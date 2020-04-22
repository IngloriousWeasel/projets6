package projet;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.HashMap;

public class FigureBase {
	private float moyHPlan=0.f;
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
	private Set<Point> points = new TreeSet<>();
	// ensemble des trangles (polygône à 3 côtés) qui pave la figure.
	private Set<Triangle> triangles = new TreeSet<>();
	// Liste des losanges composants le polygone.
	private Set<Losange> losanges = new HashSet<>();
	// map de pour la création des voisins
	private Map<Point, Set<Triangle>> triangleOfSommet = new HashMap<>();
	// Liste des motifs du polygone
	private Set<Motif> motifs = new HashSet<>();
//============================================================================//
	// Constructeur.
	public FigureBase(String mot, int cote, Point origine) {
		super();
		this.mot = mot;
		this.cote = cote;
		this.origine = origine;
		initPolygone();
	}

//============================================================================//
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
//============================================================================//
	// Méthodes.
//============================================================================//


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
				pointact = pointact.newAddP( 0, k, k);
				if ( bordure.contains(pointact))
					System.out.println( true);
				bordure.add(pointact);
				k = 1;
				break;
			case 'v':
				pointact = pointact.newAddP(0, k, 0);
				if ( bordure.contains(pointact))
					System.out.println( true);
				bordure.add(pointact);
				k = 1;
				break;
			case 'w':
				pointact = pointact.newAddP(0, 0, k);
				if ( bordure.contains(pointact))
					System.out.println( true);
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

//============================================================================//

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
//============================================================================//

	// Initialise le polygone qui sera note scene.
	private void initPolygone() {
		int[][] coords = calculBordure();
		polygone = new Polygon(coords[0], coords[1], points.size());
	}

//============================================================================//

	// Initialisise les points contenu dans la figure.
	public void initPoints() {
		int minV = points.stream().mapToInt(Point::getV).min().getAsInt();
		int maxV = points.stream().mapToInt(Point::getV).max().getAsInt();
		int minW = points.stream().mapToInt(Point::getW).min().getAsInt();
		int maxW = points.stream().mapToInt(Point::getW).max().getAsInt();
		for (int v = minV; v <= maxV; v++)
			for (int w = minW; w <= maxW; w++) {
				Point p = new Point(v, w);
				if (polygone.contains(p.getX(), p.getY()))
					points.add(p);
			}
	}
	
//============================================================================//

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

//============================================================================//
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

//============================================================================//	
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

	
//============================================================================//
	
	// Initialise les losanges.
	public void initLosangesCube() {
		Losange l;
		for (Triangle t1 : triangles) {
			if (!t1.getInLosange()) {
				if(onTop(t1))
				for (Triangle t2 : t1.getAdjacents()) {
					if (t2.getTop().equals(t1.getTop())
							&&t2.getBottom().equals(t1.getBottom())
							&&!t2.getInLosange()) {
						
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
				 if(onLeft(t1)) 
					for (Triangle t2 : t1.getAdjacents()) {
						if (t1.getType()==Type.GAUCHE)
							if (t2.getMiddle().equals(t1.getTop())
									&&t2.getBottom().equals(t1.getMiddle())
									&&!t2.getInLosange()){ 
									l = new Losange(t1, t2);
									t1.setLosange(l);
									t2.setLosange(l);
									t1.setInLosange(true);
									t2.setInLosange(true);
									losanges.add(l);
									break;
							}
						else 
							if(t1.getTop().equals(t2.getTop())
									&&t2.getMiddle().equals(t1.getBottom())
									&&!t2.getInLosange()&&!onRight(t2)) {
								l = new Losange(t2, t1);
								t1.setLosange(l);
								t2.setLosange(l);
								t1.setInLosange(true);
								t2.setInLosange(true);
								losanges.add(l);
								break;
							}
					}
				else if(onRight(t1))
					for (Triangle t2 : t1.getAdjacents()) {
						if (t1.getType()==Type.DROITE)
							if (t1.getTop().equals(t2.getBottom())
									&&t1.getMiddle().equals(t2.getBottom()))
									{ 
									l = new Losange(t1, t2);
									t1.setLosange(l);
									t2.setLosange(l);
									t1.setInLosange(true);
									t2.setInLosange(true);
									losanges.add(l);
									break;
							}
						else 
							if(t1.getTop().equals(t2.getMiddle())
									&&t1.getMiddle().equals(t2.getBottom())
									&&!t2.getInLosange()) {
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
	
	public void initLosangesplan() {
		Losange l;
		for (Triangle t1: this.getTriangles()) {
			if (!t1.getInLosange())
				for (Triangle t2 : t1.getAdjacents()){
					if (!t2.getInLosange()) {
						if(t1.getTop().equals(t2.getTop())
								&&t1.getBottom().equals(t2.getBottom())) {
							l = new Losange(t1, t2);
							t1.setLosange(l);
							t2.setLosange(l);
							t1.setInLosange(true);
							t2.setInLosange(true);
							losanges.add(l);
							for (Triangle t3 : t2.getAdjacents()) {
								if(!t3.getInLosange()&&
										t2.getMiddle().equals(t3.getBottom())
										&&t2.getTop().equals(t3.getMiddle())) {
									for (Triangle t4 : t3.getAdjacents()) {
										if(!t4.getInLosange()
												&&t3.getTop().equals(t4.getMiddle())
												&&t3.getMiddle().equals(t4.getBottom())) {
											Losange l3 = new Losange(t3,t4);
											t4.setLosange(l3);
											t3.setLosange(l3);
											t4.setInLosange(true);
											t3.setInLosange(true);
											losanges.add(l3);
									}
								}
							}		
						}
					}
					
						else if(t1.getMiddle().equals(t2.getBottom())
								&&t1.getTop().equals(t2.getMiddle())) {
							for (Triangle t3 : t2.getAdjacents()) {
								if(!t3.getInLosange()
										&&t2.getTop().equals(t3.getMiddle())
										&&t2.getMiddle().equals(t3.getBottom())) {
									Losange l2 = new Losange(t3,t2);
									t2.setLosange(l2);
									t3.setLosange(l2);
									t2.setInLosange(true);
									t3.setInLosange(true);
									losanges.add(l2);
									
								}
							}
						}
					}
				}
		}
	}
	
	public void initLosangesrand() {
		Losange l;
		for (Triangle t1 : this.triangles) {
			if(!t1.getInLosange()) {
				for (Triangle t2 : t1.getAdjacents())
					if(!t2.getInLosange()) {
						l = new Losange(t1, t2);
						t1.setLosange(l);
						t2.setLosange(l);
						t1.setInLosange(true);
						t2.setInLosange(true);
						losanges.add(l);
						
					}
			}
		}
	}
	
	
			
	
//============================================================================//	
	
//	Initialise les motifs composants la figure.
	public void initMotif() {
		for ( Losange lh : this.losanges)
			if ( lh.getCouleur().equals(JFrameGraphics.horizontalColor) ) 
				for ( Losange ld : lh.getAdjacents())
					if(ld.getCouleur().equals(JFrameGraphics.rightColor))
						for (Losange lg: ld.getAdjacents()) 
							if(lg.getCouleur().equals(JFrameGraphics.leftColor)
									&&lh.getAdjacents().contains(lg)) {
								Motif motif=new Motif(lh,lg,ld);
								motif.getlHorizontal().getInMotifs().add(motif);
								motif.getlLeft().getInMotifs().add(motif);
								motif.getlRight().getInMotifs().add(motif);
								motifs.add(motif);
							}
	}
	
//============================================================================//
//	//Initialise la hauteur des points de la figure.
	public void initHauteurPlan() {
		for ( Motif m : this.motifs) {
			m.getlHorizontal().getTriangleD().getTop().setHauteur(-1);
			m.getlHorizontal().getTriangleD().getMiddle().setHauteur(0);
			m.getlHorizontal().getTriangleG().getMiddle().setHauteur(0);
			m.getlHorizontal().getTriangleD().getBottom().setHauteur(1);
			m.getlRight().getTriangleD().getTop().setHauteur(1);
			m.getlRight().getTriangleG().getTop().setHauteur(0);
			m.getlLeft().getTriangleG().getTop().setHauteur(1);
		}
		float sum = 0.f;
		for(Point p : this.points) {
			sum+= p.getHauteur();
		}
		moyHPlan=sum/this.points.size();
		
	}
	
	
	
// met a jour la liste d'est motifs apres le flip du motif M
	public void updateMotif ( Motif m ) {
		for ( Losange lg : m.getlHorizontal().getAdjacents() ) 
			if ( lg.getCouleur().equals( JFrameGraphics.leftColor ))
				for(Losange ld :  lg.getAdjacents() ) 
					if ( ld.getCouleur().equals( JFrameGraphics.rightColor ) 
							&& m.getlHorizontal().getAdjacents().contains(ld) ){ 
							creerMotif(m.getlHorizontal(),lg,ld);
					}
		
		for ( Losange lh : m.getlLeft().getAdjacents() )
			if ( lh.getCouleur().equals( JFrameGraphics.horizontalColor ) )
				for ( Losange ld : lh.getAdjacents() )
					if ( ld.getCouleur().equals( JFrameGraphics.rightColor ) 
							&& m.getlLeft().getAdjacents().contains(ld) ) {
							creerMotif(lh,m.getlLeft(),ld);
					}
		
		
		for ( Losange lh : m.getlRight().getAdjacents() )
			if ( lh.getCouleur().equals( JFrameGraphics.horizontalColor ) )
				for ( Losange lg : lh.getAdjacents() )
					if ( lg.getCouleur().equals( JFrameGraphics.leftColor ) 
							&& m.getlRight().getAdjacents().contains(lg) )  
							creerMotif(lh,lg,m.getlRight());
	
	}
	
	//suprime les motifs qui ont été cassé lors du flip
	public void removeOldMotifs(Motif m) {
		Set<Motif> tampon= new HashSet<>();
		
		for(Motif motif : m.getlHorizontal().getInMotifs()) {
			if(!motif.equals(m)) {
				this.motifs.remove(motif);
				tampon.add(motif);
			}
		}
		m.getlHorizontal().getInMotifs().removeAll(tampon);
		
		for(Motif motif : m.getlLeft().getInMotifs()) {
			if(!motif.equals(m)) {
				motifs.remove(motif);
				tampon.add(motif);;
			}
		}
		m.getlLeft().getInMotifs().removeAll(tampon);
		
		for(Motif motif : m.getlRight().getInMotifs()) {
			if(!motif.equals(m)) {
				motifs.remove(motif);
				tampon.add(motif);
			}
		}
		m.getlRight().getInMotifs().removeAll(tampon);
	}
	
//===========================================================================//
	
	//effectue un tirage pour effectuer un flip avec un probilité donnée
	public boolean pile_Ou_Face(double proba) {
		double val = Math.random();
		return val<=proba;
	}
	
// fonction de calcul de proba :1-(exp(-E/T)/2)
//E est 	un nombre dans {0,1,2,3,4,5,6} qui est égal au nombre de défaut  identique collé à un losange du même type)
//T est la température
	
	public double proba(double E , float T) {
		return 1-(Math.exp(E/T))/2;
	}
	
	//tire un point au hasard dans la liste:
	public Point tiragePoint() {
		Random rand = new Random();
		int index=rand.nextInt(this.getPoints().size());
		int i=0;
		for ( Point p : points ) {
			if( i == index )
				return p;
			i++;
		}
		return null;	
	}
	
	public Motif tirageMotif() {
		Random rand = new Random();
		int index=rand.nextInt(motifs.size());
		return peakMotif(index);
	}
	
	
	//regarde si il y a un motif qui contient le point p retourne null sinon
	public Motif findMotif ( Point p ) {
		for ( Motif m : motifs) 
			if ( m.contains(p) )
					return m;
		return null;
	}
	
	public Motif peakMotif(int index) {
		int i=0;
		for ( Motif m : motifs)
			if ( i==index ) return m;
			else i++;
		return null;
	}
	
	//tire un point au hasard si il est dans un motif effectue un flip avec une 
	//proba donnée et retourne true retourne false si aucun flip n'est effectué
	// auto genrated Jan Georg Smaus method 
	public boolean compute ( double proba ) {
		Point p =tiragePoint();
		Motif m =findMotif(p);
		if ( m!=null ) 
			if(pile_Ou_Face(proba)) {
				this.removeOldMotifs(m);
				m.flip();
				this.updateMotif(m);
				return true;
			}
		return false;
	}
	
	public boolean computeMotif ( double proba ) {
		Motif m =tirageMotif();
		if ( m!=null ) 
			if(pile_Ou_Face(proba)) {
				this.removeOldMotifs(m);
				m.flip();
				this.updateMotif(m);
				return true;
			}
		return false;
	}
	

//============================================================================//

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
	
	
	public Motif creerMotif(Losange lh,Losange lg, Losange ld) {
		Motif m =new Motif(lh, lg, ld);
		lh.getInMotifs().add(m);
		lg.getInMotifs().add(m);
		ld.getInMotifs().add(m);
		motifs.add(m);
		return m;
	}
	
	public boolean onTop(Triangle t) {
		return t.getBottom().getV()>=0&&t.getBottom().getW()>=0;
	}
	public boolean onLeft(Triangle t){
		return t.getMiddle().getX()<0&&!onTop(t);
	}
	public boolean onRight(Triangle t) {
		return t.getMiddle().getX()>0&&!onTop(t);
	}
	
	public float moyHauteurs() {
		float sum=-0.f;
		for ( Point p : this.points)  
			sum += p.getHauteur();
		return (sum/this.points.size())-moyHPlan;
		
	}
	
}


