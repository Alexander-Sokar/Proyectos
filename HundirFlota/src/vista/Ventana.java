package vista;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import modelos.Tablero;
import java.awt.Toolkit;

public class Ventana extends JFrame {
	private JLabel hora;
	private JLabel balas;
	private JLabel barcos;
	private Timer timer;
	private int segundos = 0;

    public Ventana() {
    	setIconImage(Toolkit.getDefaultToolkit().getImage(Ventana.class.getResource("/recursos/flota.png")));

        setTitle("Hundir la Flota");

        setSize(1000, 1000);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        Tablero tablero = new Tablero(this);

        JPanel panel = new JPanel(new BorderLayout());
        JPanel panelGrid = new JPanel(new GridLayout(3,3,10,20));
       

        JLabel tiempo = new JLabel("Tiempo");
        hora = new JLabel("00:00");
        
        JLabel disparos = new JLabel("Disparos");
        balas = new JLabel("0 /50");
        
        JLabel hundidos = new JLabel ("Barcos Hundidos");
        barcos = new JLabel("0 / 5");
        
        JMenuBar barra = new JMenuBar();
        JMenu archivo = new JMenu("Archivo");
		JMenu ayuda = new JMenu("Ayuda");
		
		JMenuItem salir= new JMenuItem("Salir");
		JMenuItem acerca= new JMenuItem("Acerca de");

		salir.addActionListener(e -> System.exit(0));
		
		acerca.addActionListener(e ->
	    JOptionPane.showMessageDialog(null,"Hundir la flota\nVersión 1.0"
	    )
	);
        
		archivo.add(salir);
		ayuda.add(acerca);
		
		barra.add(archivo);
		barra.add(ayuda);
		
		setJMenuBar(barra);
		
        panelGrid.add(tiempo);
        panelGrid.add(hora);
        
        panelGrid.add(disparos);
        panelGrid.add(balas);
        
        panelGrid.add(hundidos);
        panelGrid.add(barcos);
      
        panelGrid.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        
        panel.add(panelGrid,BorderLayout.NORTH);
        panel.add(tablero, BorderLayout.CENTER);

        getContentPane().add(panel);
        
        tiempo.setHorizontalAlignment(JLabel.CENTER);
        hora.setHorizontalAlignment(JLabel.CENTER);

        disparos.setHorizontalAlignment(JLabel.CENTER);
        balas.setHorizontalAlignment(JLabel.CENTER);

        hundidos.setHorizontalAlignment(JLabel.CENTER);
        barcos.setHorizontalAlignment(JLabel.CENTER);
        
        Font fuente = new Font("Arial", Font.BOLD, 16);

        tiempo.setFont(fuente);
        disparos.setFont(fuente);
        hundidos.setFont(fuente);
        
        iniciarTemporizador();

        setVisible(true);
    }
    
    public void setTiempo(String tiempo) {
        hora.setText(tiempo);
    }

    public void setDisparos(int disparos) {
        balas.setText(String.valueOf(disparos + " / 50"));
    }

    public void setBarcosHundidos(int hundidos, int total) {
        barcos.setText(hundidos + " / " + total);
    }
    
    private void iniciarTemporizador() {

        timer = new Timer(1000, e -> {

            segundos++;

            int minutos = segundos / 60;
            int seg = segundos % 60;

            setTiempo(String.format("%02d:%02d", minutos, seg));

        });

        timer.start();
    }
    
    public void detenerTemporizador() {
        timer.stop();
    }
    
   

}