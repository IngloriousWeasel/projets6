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
	
	public void paint(Graphics g) {
		super.paintComponent(g);
		// Précondition le mot doit fermer le polygone.
		g.translate(10,900);
//		F.initPoints();
//		F.initAdjacentsPoint();
//		F.initTriangle();
//		F.initAdjacentTriangle();
//		F.initLosangesplan();
//		F.initMotif();
//		F.initHauteurPlan();
//		g.drawPolygon(F.getPolygone());
//		
//		
//		int i=0;
//		while ( i <100000) {
//			Main.getF().computeMotif(1);
//			i++;
//			}
//		System.out.println(Main.getF().peakMotif(10).getE());
		
		g.drawString("hauteur initiale : "+ Math.abs(Main.getF().moyHauteurs()),1500,-800);
//		for ( Point p : F.getPoints())
//			System.out.println(p.getHauteur());
		
//============================================================================//
//		
////		
		
	//	remplissage des losanges
		for (Losange l : Main.getF().getLosanges()) 
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
	

}
