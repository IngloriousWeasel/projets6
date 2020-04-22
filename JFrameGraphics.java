package projet;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class JFrameGraphics extends JPanel {
	
	//                    \      /
	//                   v \    / w 
	//                      \  /
	//						 \/
	//                       |
	//						 |
	//					   u |
	//                       |
	// Unitée de mesure.
	public static int dimCote =10;
	
	//couleur du losange horizontal
	public static Color horizontalColor=Color.red;
	
	//couleur du losange a gauche;
	public static Color leftColor = Color.GRAY;
	
	//couleur du losange a droite;
	public static Color rightColor = Color.DARK_GRAY;
	
	private static final long serialVersionUID = 1L;
	// Dimension de la fenêtre.
	private static int[] dim = {1080,1080};
	
	public void pait(Graphics g) {
		// Précondition le mot doit fermer le polygone.
		
		String mot ="-vw-vw-vw-vw-vw-vw-vw-vw-vw-vw-vw-vw-vw-vw-vw-vw-vw-vw-vw"
				+ "-vw-vw-vw-vw-vw-vw-vw-vw-vw-vw-vw-vw-vw-vw-vw-vw-vw-vw-vw-vw"
				+ "-vwuwuwuwuwuwuwuwuwuwuwuwuwuwuwuwuwuwuwuwuwuwuwuwuwuwuwuwuwu"
				+ "wuwuwuwuwuwuwuwuwuwuwuv-wv-wv-wv-wv-wv-wv-wv-wv-wv-wv-wv-wv"
				+ "-wv-wv-wv-wv-wv-wv-wv-wv-wv-wv-wv-wv-wv-wv-wv-wv-wv-wv-wv-w"
				+"v-wv-wv-wv-wv-wv-wv-wv-w-u-w-u-w-u-w-u-w-u-w-u-w-u-w-u-w-u-w-"
				+ "u-w-u-w-u-w-u-w-u-w-u-w-u-w-u-w-u-w-u-w-u-w-u-w-u-w-u-w-u-w-"
				+ "u-w-u-w-u-w-u-w-u-w-u-w-u-w-u-w-u-w-u-w-u-w-u-w-u-w-u-w-u-w";
		g.translate(10,900);
		FigureBase F = new FigureBase(mot,dimCote, new Point(0,0));
		long debut = System.currentTimeMillis();
		F.initPoints();
		F.initAdjacentsPoint();
		F.initTriangle();
		F.initAdjacentTriangle();
		F.initLosangesplan();
		F.initMotif();
		F.initHauteurPlan();
//		g.drawPolygon(F.getPolygone());
//		
//		
		System.out.println(System.currentTimeMillis()-debut);
		int i=0;
		while ( i <100000) {
			F.computeMotif(1);
			i++;
			}
		System.out.println(F.peakMotif(10).getE());
		System.out.println(F.moyHauteurs());
//		for ( Point p : F.getPoints())
//			System.out.println(p.getHauteur());
		
//============================================================================//
//		
////		
		
	//	remplissage des losanges
		for (Losange l : F.getLosanges()) 
				afficheLosange(l,g);
		
//		System.out.println("done");
////	
//	//	bordure en triangle
//		g.setColor(Color.black);
//		for (Triangle t : F.getTriangles() ) {
//			g.drawPolygon(t.getPolygone());
//		
//		}
		
//		for (Motif M : F.getMotifs()) {
//			afficheMotif(M, g);
//				
//			}
			
		
		
		
//		//affichage des points
//		for(Point p :F.getPoints())	{
//			g.drawLine(p.getX(),p.getY(),p.getX(),p.getY());
//			if ( i==11 )
//				break;
//			i++;
//		}
//			
			
//		g.drawPolygon(F.getPolygone());
//		
		
	}
	
	public void afficheLosange(Losange l, Graphics g) {
		g.setColor(l.getCouleur());
		g.fillPolygon(l.getTriangleG().getPolygone());
		g.fillPolygon(l.getTriangleD().getPolygone());
	}
	
	public void afficheMotif(Motif m, Graphics g) {
		afficheLosange(m.getlHorizontal(), g);
		afficheLosange(m.getlLeft(), g);
		afficheLosange(m.getlRight(), g);
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("Projet");
		frame.getContentPane().add(new JFrameGraphics());
	    frame.setSize(dim[0],dim[1]);
	    frame.setLocationRelativeTo(null);
	    frame.setVisible(true);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	    frame.setResizable(true);

	}

}
