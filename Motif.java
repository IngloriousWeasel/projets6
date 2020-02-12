package projet;

import java.awt.Color;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Motif {

	// List des losanges composants le motif.
	private List<Losange> losanges = new ArrayList<Losange>();

	// Constructeur.
	public Motif(Losange l1, Losange l2, Losange l3) {
		super();
		this.losanges.add(l1);
		this.losanges.add(l2);
		this.losanges.add(l3);
	}

	// Getters.
	public List<Losange> getLosanges() {
		return losanges;
	}
	//Redéfinition de equals
	@Override
	public boolean equals(Object o){
		if(o instanceof Motif) {
			Motif motif=(Motif)o;
			for(Losange l : motif.losanges) {
				if(!this.losanges.contains(l)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	public void surbrillance() {
		losanges.get(0).setCouleur(Color.GREEN);
		losanges.get(1).setCouleur(Color.RED);
		losanges.get(2).setCouleur(Color.BLUE);
}
//	public void flip() {
//		Triangle temp1,temp2;
//		int distance=(int)(Math.sqrt((double)((JFrameGraphics.dimCote*JFrameGraphics.dimCote)-((JFrameGraphics.dimCote/2)*(JFrameGraphics.dimCote/2)))));
//		//if(Collections.max(losanges.get(0).getSommets(),(Point a,Point b)->a.yValue()-b.yValue()).yValue()<Collections.max(losanges.get(1).getSommets(),(Point a,Point b)->a.yValue()-b.yValue()).yValue()) {
//			temp1=losanges.get(0).getTriangle1();
//			temp2=losanges.get(2).getTriangle1();
//			losanges.get(0).setTriangle1(losanges.get(1).getTriangle1());
//			losanges.get(2).setTriangle1(temp1);
//			losanges.get(1).setTriangle1(temp2);
//			temp1=losanges.get(0).getTriangle2();
//			temp2=losanges.get(1).getTriangle2();
//			losanges.get(0).setTriangle2(losanges.get(2).getTriangle2());
//			losanges.get(1).setTriangle2(temp1);
//			losanges.get(2).setTriangle2(temp2);
////			losanges.get(0).getPolygone().translate(0,-JFrameGraphics.dimCote);
////			losanges.get(1).getPolygone().translate((int)(0.5 * Math.sqrt(3)* (JFrameGraphics.dimCote)),JFrameGraphics.dimCote/2 );
////			losanges.get(2).getPolygone().translate((int)(0.5 * Math.sqrt(3) *(-JFrameGraphics.dimCote)),(int)JFrameGraphics.dimCote/2);
//		for(Losange l:losanges) {
//			l.updateSommetsLosange();
//			l.updatePolygon();
//		}
//		
//	}
//	
//	
//	
}
