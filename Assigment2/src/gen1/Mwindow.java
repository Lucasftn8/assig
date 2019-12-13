package gen1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

import gen1.GraphPanel.VisualSetting;

public class Mwindow extends JFrame implements Runnable{

	private GraphPanel Graphpan = new GraphPanel();
	private Thread fred = null;
	private boolean running=false,paused=false;
	private Client client = null;
	private int delay=2500;
	private Color GuiColor = Color.gray;
	//-------Software Details-------//
	private int color_num = 6;
	/*
	 * 	version 0a2
	 * 
	 */
	
	//----------MENU--------------//
	private JMenuBar menubar= new JMenuBar();
	private JMenu Fmenu = new JMenu("Files");
	private JMenu Omenu=new JMenu("Option");
	
	//--------------ACTION-----------//
	private JMenuItem clean_item = new JMenuItem("Reset");	private JMenuItem export_item = new JMenuItem("Export");
	private JMenuItem close_item = new JMenuItem("Leave");
	private JMenuItem run_item= new JMenuItem("Run");
	private JMenuItem pause_item= new JMenuItem("Pause");
	private JMenuItem round_item = new JMenuItem("Rond");
	
	//-------COLOR------//
	private JMenuItem[] color_item = new JMenuItem[color_num];

		//-----Custom ActioListener-----//
	private RateListener slist = new RateListener();
	private ThreadListener tlist = new ThreadListener();
	
	//--------------TOOLBAR---------//
	private JToolBar toolbar = new JToolBar();
	private JButton rate_plus = new JButton();
	private JButton rate_minus = new JButton();

	
	//--------Contextual Menu---------//
	private JPopupMenu popbar = new JPopupMenu();
	
	public Mwindow() {
		this.setTitle("Graphic Window");
		this.setSize(1200,1000);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//this.getContentPane().setLayout(new BorderLayout());
		//this.getContentPane().setBackground(GuiColor);
		
		this.initMenu();
		this.initToolBar();
		

		Graphpan.setBackground(Color.blue);
		
		this.getContentPane().add(Graphpan);
		
		this.setVisible(true);
		}
	
	public void initMenu() {
		//------ActionListener------//
		clean_item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(JOptionPane.showConfirmDialog(null,"Are you sure to reset?","All clear Now",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==0)
				{
					Graphpan.erase();
				}
			}
		});
		close_item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(JOptionPane.showConfirmDialog(null,"Do you really want to quit?","By see you soon!",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==0)
				{
					System.exit(0);
				}
			}
		});
		Omenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//GUI_Option options = new GUI_Option(Graphpan.get_set());
				//The option menu must cover General Setting (BackGround color) and Graphics ones (rate, points form,nb max,color graph (axis/points/courb) )
			}
		});
		
		
		close_item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,KeyEvent.VK_SHIFT));
		clean_item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,KeyEvent.VK_SHIFT));
		
		this.Fmenu.add(clean_item);
		this.Fmenu.add(export_item);
		this.Fmenu.addSeparator();
		this.Fmenu.add(close_item);
		
		Fmenu.setMnemonic('F');
		Omenu.setMnemonic('O');
		menubar.add(Fmenu);
		menubar.add(Omenu);
		this.setJMenuBar(menubar);
	}
	
	public void initToolBar() {
		rate_plus.addActionListener(slist);
		rate_minus.addActionListener(slist);
		run_item.addActionListener(tlist);
		pause_item.addActionListener(tlist);
		toolbar.add(pause_item);
		toolbar.add(run_item);
		
		//	Rate tools	//
		//toolbar.add(circle);
		toolbar.addSeparator();
		//	GUI Change	//
		//toolbar.add(size_plus);
		//toolbar.add(size_minus);
		toolbar.addSeparator();
		
		this.getContentPane().add(toolbar,BorderLayout.NORTH);
	}
	public void ActionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals(run_item)) {
		}
	}
	public void run() {
		 while(running){
			 System.out.println("Coucou je cours");
             try{
         		this.Graphpan.addPoint(client.getData());
                     Thread.sleep(this.delay);
                     synchronized(this){
                             while (this.paused) wait();
                     }
             }
             catch (InterruptedException e){
                     System.out.println("Counter was Interrupted!");
                     this.running = false;
             }
     }
	}
	class GUI_Option extends JDialog{
		private VisualSetting t_set;
		private boolean sendData;
		private JLabel formlabel,nameLabel;
		private JComboBox t_form;
		private JTextField t_name, posy;
		  
		public GUI_Option(JFrame parent, String title, boolean model) {
			super(parent,title,model);
			this.setSize(550,270);
			this.setLocationRelativeTo(null);
			this.setResizable(false);
			this.setAlwaysOnTop(true);
			this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
			this.initComponent();
			this.setVisible(true);
		}

		private void initComponent() {
			JPanel panName = new JPanel();
			panName.setBackground(Color.WHITE);
			//panName.setLayout(new GridLayout(1,2));
			t_name = new JTextField();
			t_name.setBorder(BorderFactory.createTitledBorder("Nom"));
			nameLabel = new JLabel("Nom:");
			panName.add(nameLabel);
			panName.add(t_name);

			JPanel panType = new JPanel();
			panType.setBackground(Color.WHITE);
			panType.setPreferredSize(new Dimension(220,60));
			panType.setBorder(BorderFactory.createTitledBorder("Type"));
			t_form = new JComboBox();
			t_form.addItem("SQUARE");
			t_form.addItem("CIRCLE");
			//Triangle?
			formlabel = new JLabel("Type : ");
			panType.add(formlabel);
			panType.add(t_form);

		    JPanel content = new JPanel();
		    content.setBackground(Color.WHITE);
		    content.add(panName);
		    content.add(panType);

		    JPanel control = new JPanel();
		    JButton okBouton = new JButton("OK");
		    
		    okBouton.addActionListener(new ActionListener(){
		      public void actionPerformed(ActionEvent arg0) {   
		    	//Here call modify setting
		        setVisible(false);
		      }     
		    });

		    JButton cancelBouton = new JButton("Annuler");
		    cancelBouton.addActionListener(new ActionListener(){
		      public void actionPerformed(ActionEvent arg0) {
		        setVisible(false);
		      }      
		    });

		    control.add(okBouton);
		    control.add(cancelBouton);

		    this.getContentPane().add(content, BorderLayout.CENTER);
		    this.getContentPane().add(control, BorderLayout.SOUTH);
		  }
		public void OptionGUI(VisualSetting _set) {
			this.sendData= false;
			this.setVisible(true);
			_set.ModifySetting(t_set);//Modify the actual setting with the new one
		}
	}
	
	public void testgraph() {
		//Workig for a minimum temp of 38 degres and maximum 50
		this.Graphpan.addPoint(new DataPackage((float)43.5 , "18:57"));
		this.Graphpan.addPoint(new DataPackage((float)39.4 , "18:58"));
		this.Graphpan.addPoint(new DataPackage((float)43.6 , "18:59"));
		this.Graphpan.addPoint(new DataPackage((float)39.2 , "19:00"));
		this.Graphpan.addPoint(new DataPackage((float)44.1 , "19:01"));
		this.Graphpan.addPoint(new DataPackage((float)50 , "18:57"));
		this.Graphpan.addPoint(new DataPackage((float)38.2 , "18:58"));
		this.Graphpan.addPoint(new DataPackage((float)43.6 , "18:59"));
		this.Graphpan.addPoint(new DataPackage((float)46.2 , "19:00"));
		this.Graphpan.addPoint(new DataPackage((float)44.1 , "19:01"));
		this.Graphpan.addPoint(new DataPackage((float)43.5 , "18:57"));
		this.Graphpan.addPoint(new DataPackage((float)39.4 , "18:58"));
		this.Graphpan.addPoint(new DataPackage((float)43.6 , "18:59"));
		this.Graphpan.addPoint(new DataPackage((float)46.2 , "19:00"));
		this.Graphpan.addPoint(new DataPackage((float)44.1 , "19:01"));
		this.Graphpan.addPoint(new DataPackage((float)43.5 , "18:57"));
		this.Graphpan.addPoint(new DataPackage((float)39.4 , "18:58"));
		this.Graphpan.addPoint(new DataPackage((float)43.6 , "18:59"));
		this.Graphpan.addPoint(new DataPackage((float)46.2 , "19:00"));
		this.Graphpan.addPoint(new DataPackage((float)44.1 , "19:01"));
	}
	public void setup_thread() {
		fred=new Thread(this);
		client = new Client("169.254.120.199");//peut etre a changer
		this.running=true;
		this.paused=false;
		this.fred.start();
	}
	class ThreadListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().getClass().getName()=="javax.swing.JMenuItem") {
				if(e.getSource()==run_item) {
					System.out.println("Hallo j'ecoute!");
					setup_thread();
				}
					
			}
		}
	}
	class RateListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			/*if(e.getSource().getClass().getName()=="javax.swing.JMenuItem") {
		        if(e.getSource()==size4_item)
		        	pan.setSize(100);
		        else if(e.getSource()==size3_item)
		        	pan.setSize(50);
		        else if(e.getSource()==size2_item)
		        	pan.setSize(20);
		        else 
		        	pan.setSize(10);
		      }
			else {
				if(e.getSource()==size_plus)
					pan.impSize(10);
				else if(e.getSource()==size_minus)
					if(pan.getS()>10)
						pan.impSize(-10);
					else if(pan.getS()>2)
						pan.impSize(-1);
			}*/
		 }
	}
	public static void main(String[] args) {
		Mwindow w=new Mwindow();
	}
}
	

