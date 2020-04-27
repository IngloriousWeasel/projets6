package projet;

import java.awt.Color;

public class Main {
	
	public static int dimCote =10;
	
	//couleur du losange horizontal
	public static Color horizontalColor=Color.red;
	
	//couleur du losange a gauche;
	public static Color leftColor = Color.GRAY;
	
	//couleur du losange a droite;
	public static Color rightColor = Color.DARK_GRAY;
	
	private static final long serialVersionUID = 1L;
	
	// Dimension de la fenêtre.
	private static int[] dim = {1920,1080};
	
	private  static String mot ="-vw-vw-vw-vw-vw-vw-vw-vw-vw-vw-vw-vw-vw-vw-vw-vw-vw-vw-vw"
			+ "-vw-vw-vw-vw-vw-vw-vw-vw-vw-vw-vw-vw-vw-vw-vw-vw-vw-vw-vw-vw"
			+ "-vwuwuwuwuwuwuwuwuwuwuwuwuwuwuwuwuwuwuwuwuwuwuwuwuwuwuwuwuwu"
			+ "wuwuwuwuwuwuwuwuwuwuwuv-wv-wv-wv-wv-wv-wv-wv-wv-wv-wv-wv-wv"
			+ "-wv-wv-wv-wv-wv-wv-wv-wv-wv-wv-wv-wv-wv-wv-wv-wv-wv-wv-wv-w"
			+"v-wv-wv-wv-wv-wv-wv-wv-w-u-w-u-w-u-w-u-w-u-w-u-w-u-w-u-w-u-w-"
			+ "u-w-u-w-u-w-u-w-u-w-u-w-u-w-u-w-u-w-u-w-u-w-u-w-u-w-u-w-u-w-"
			+ "u-w-u-w-u-w-u-w-u-w-u-w-u-w-u-w-u-w-u-w-u-w-u-w-u-w-u-w-u-w";
	
	
	private static FigureBase F = new FigureBase(mot,dimCote, new Point(0,0));
	
	public static FigureBase getF() {
		return F;
	}
	
	
	public static void setF(FigureBase f) {
		F = f;
	}

	public static void setSave(FigureBase save) {
		Main.save = save;
	}







	public static FigureBase save;

	
	
	

	public static void main(String[] args) {
		F.initPoints();
		F.initAdjacentsPoint();
		F.initTriangle();
		F.initAdjacentTriangle();
		F.initLosangesplan();
		F.initMotif();
		F.initHauteurPlan();
		save=new FigureBase(F);
		Window fenetre = new Window(dim[0],dim[1],"test");
	}

	public static FigureBase getSave() {
		return save;
	}



}
