package projet;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Window extends JFrame {
	
	
	  CardLayout cl = new CardLayout();
	  
	  
	  //Liste des noms de nos conteneurs pour la pile de cartes
	  String[] listContent = {"CARD_1", "CARD_2"};
	  int indice = 0;
	  private double T;
	  
	  
	public Window(int xMax,int yMax, String title) {
		
		JPanel content = new JPanel();
		JFrameGraphics jfg= new JFrameGraphics(); 
		
		JButton start = new JButton("Commencer la simulation");
		 start.addActionListener(new ActionListener(){
			 
			 public void actionPerformed(ActionEvent event){
		    	  int i=0;
					while ( i <100000) {
						Main.getF().melange(0.5d);
						i++;
						}
					Main.setSave(new FigureBase(Main.getF()));
					
					jfg.repaint();
				cl.next(content);
		        
		      }
		 });
		 
		 
		 
		 JButton restart = new JButton("revenir au plan");
		 restart.addActionListener(new ActionListener(){
		      public void actionPerformed(ActionEvent event){
		    	Main.getF().getLosanges().clear();
		    	Main.getF().getMotifs().clear();
		    	for (Triangle t : Main.getF().getTriangles()) {
		    		t.setInLosange(false);
		    	}
		    	Main.getF().initLosangesplan();
		    	Main.getF().initMotif();
		        Main.getF().initHauteurPlan();
		        Main.getF().getLosanges();
		        
		        jfg.repaint();
		        cl.next(content);
		      }
		 });
		 
		 
		 
		 JButton reset = new JButton("revenir au pavage initial");
		 reset.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.setF(new FigureBase(Main.getSave()));
				jfg.repaint();
			}
		});
		 
		 
		 
		 
		 JFormattedTextField jtf =new JFormattedTextField("entrer la température ");
		 Font police = new Font("Arial", Font.BOLD, 14);
		 jtf.setFont(police);
		 jtf.setPreferredSize(new Dimension(150, 30));
		
		 
		 JButton calcul = new JButton("calcul");
		 calcul.addActionListener(new ActionListener() {
			 
			 @Override
			 public void actionPerformed(ActionEvent e) {
				 T=Double.valueOf(jtf.getText());
				 int i=0;
				 while ( i <100000) {
					 Main.getF().compute(T);
					 i++;
				 }
				 jfg.repaint();
				 
			 }
		 });
		 
		 
		this.setTitle(title);
		this.setSize(xMax, yMax);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		
		
		JPanel JPrestart= new JPanel();
		JPrestart.add(restart);
		JPanel JPCalcul = new JPanel();
		JPCalcul.add(calcul);
		JPanel JPreset = new JPanel();
		JPreset.add(reset);
		JPanel input = new JPanel();
		input.add(jtf);
		
		
		
		
		
		
		
		
		
		
	    JPanel card1 = new JPanel();
	    card1.setBackground(Color.blue);
	    card1.add(start,BorderLayout.CENTER);
	    JPanel card2 = new JPanel();
	    card2.setBackground(Color.red);
	    card2.add(JPrestart,BorderLayout.CENTER);
	    card2.add(JPCalcul,BorderLayout.CENTER);
	    card2.add(JPreset,BorderLayout.CENTER);
	    card2.add(input,BorderLayout.CENTER);
	    
	    
			
	  
	    //On définit le layout
	    content.setLayout(cl);
	    //On ajoute les cartes à la pile avec un nom pour les retrouver
	    content.add(card1, listContent[0]);
	    content.add(card2, listContent[1]);
	    
	    //On prévient notre JFrame que notre JPanel sera son content pane
	    this.getContentPane().add(content, BorderLayout.NORTH);
	    this.getContentPane().add(jfg,BorderLayout.CENTER);
		//rend la fênetre visible
		
	    	       
	    
	}
	
//	class BoutonStart implements ActionListener{
//		public void actionPerformed(ActionEvent e) {
//			int i=0;
//			while ( i <100000) {
//				Main.getF().computeMotif(1);
//				i++;
//				}
//			scene.remove(start);
//			JPanel carlos = new JPanel();
//			carlos.add(restart);
//			carlos.repaint();
//			scene.add(carlos,BorderLayout.EAST);
//	
//			scene.repaint();
//			System.out.println("carlos");
//			
//			
//		}
//	}
//	
//	class BoutonRestart implements ActionListener {
//		public void actionPerformed(ActionEvent e) {
//			Main.getF().initLosangesplan();
//			scene.remove(restart);
//			scene.add(start,BorderLayout.NORTH);
//			scene.repaint();
//		}
//		
//	}
//	
//	
}
