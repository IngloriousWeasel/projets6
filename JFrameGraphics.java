package projet;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class JFrameGraphics extends JPanel {

	// Unitée de mesure.
	public static int dimCote = 20;
	
	private static final long serialVersionUID = 1L;
	// Dimension de la fenêtre.
	private static int[] dim = {1080,920};
	
	public void paint(Graphics g) {
		// Précondition le mot doit fermer le polygone.
		
		String mot ="wwwwwwwwwwwwww-v-v-v-v-v-v-v-v-v-v-v-v-v-vuuuuuuuuuuuuuu-w-w-w-w-w-w-w-w-w-w-w-w-w-wvvvvvvvvvvvvvv-u-u-u-u-u-u-u-u-u-u-u-u-u";
		g.translate(dim[0]/2,dim[1]/2);
		FigureBase F = new FigureBase(mot,dimCote, new Point(-6,14,0));
		F.initPoints();
		F.initAdjacentsPoint();
		//g.drawPolygon(F.getPolygone());;
		//F.initAdjacentsPoint();
		F.initTriangle();
		g.drawPolygon(F.getPolygone());
		//F.initAdjacentTriangle();
		//F.initLosanges();
		//F.initAdjacentlosange();
//		System.out.println(F.getTriangles().size());
		g.setColor(Color.black);
		for(Triangle t:F.getTriangles()) {
			g.drawPolygon(t.getPolygone());
		}
//		for(Point p:F.getPoints()) {
//			g.drawLine(p.getX(),p.getY(),p.getX(),p.getY());
//		}
		
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("Projet");
	    frame.getContentPane().add(new JFrameGraphics());
	    frame.setSize(dim[0],dim[1]);
	    frame.setVisible(true);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	    frame.setResizable(false);
	}

}
