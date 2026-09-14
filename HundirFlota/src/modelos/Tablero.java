package modelos;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import vista.Ventana;

public class Tablero extends JPanel {

    private ImageIcon mar;
    private Ventana ventana;
    private JButton[][] botones;
    private boolean[][] ocupadas;
    private ArrayList<Barco> barcos;

    private int barcosHundidos = 0;
    private int disparos = 0;
    private int maxDisparos=50;

    public Tablero(Ventana ventana) {

        this.ventana = ventana;

        setLayout(new GridLayout(10, 10));

        mar = new ImageIcon(getClass().getResource("/recursos/mar.png") );
        
        botones = new JButton[10][10];
        ocupadas = new boolean[10][10];
        barcos = new ArrayList<>();

       
        for (int fila = 0; fila < 10; fila++) {

            for (int columna = 0; columna < 10; columna++) {

                final int f = fila;
                final int c = columna;

                botones[f][c] = new JButton();

                botones[f][c].setIcon(mar);

                botones[f][c].addActionListener(
                    e -> disparar(f, c)
                );

                add(botones[f][c]);
            }
        }

        crearBarcos();
    }

    private void disparar(int fila, int columna) {

        botones[fila][columna].setEnabled(false);

        disparos++;

        ventana.setDisparos(disparos);

        Barco barco = buscarBarco(fila, columna);

        if (barco != null) {

            botones[fila][columna].setIcon(null);

            botones[fila][columna].setBackground(Color.RED);

            botones[fila][columna].setText("X");

            barco.recibirImpacto();

            if (barco.isHundido()) {

                barcosHundidos++;

                ventana.setBarcosHundidos(barcosHundidos,barcos.size());

                longitudBarco(barco);
                
                JOptionPane.showMessageDialog(this,"¡Barco hundido!");

                if (barcosHundidos == barcos.size()) {

                    ventana.detenerTemporizador();

                    JOptionPane.showMessageDialog( this,"¡Has ganado!");
                }else  if (disparos >= maxDisparos) {

                    ventana.detenerTemporizador();
                    
                    for (int fi = 0; fi < 10; fi++) {
                        for (int c = 0; c < 10; c++) {
                            botones[fi][c].setEnabled(false);
                        }
                    }
                    
                	 JOptionPane.showMessageDialog(this,"¡Has agotado tus municiones!");
                	 
                }
            }


        } else {

            botones[fila][columna].setIcon(mar);

            botones[fila][columna].setBackground(new Color(30, 144, 255));

            botones[fila][columna].setText("");
        }
        
       
    }

    private void crearBarcos() {

        colocarBarco(5);
        colocarBarco(4);
        colocarBarco(3);
        colocarBarco(3);
        colocarBarco(2);
    }

    private void colocarBarco(int longitud) {

        Random random = new Random();

        boolean colocado = false;

        while (!colocado) {

            int fila = random.nextInt(10);
            int columna = random.nextInt(10);

            boolean vertical = random.nextBoolean();



            if (vertical) {

                if (fila + longitud <= 10) {

                    boolean libre = true;

                    for (int i = 0; i < longitud; i++) {

                        if (ocupadas[fila + i][columna]) {

                            libre = false;
                            break;
                        }
                    }

                    if (libre) {

                        Barco barco = new Barco(fila,columna,true,longitud);

                        barcos.add(barco);

                        for (int i = 0; i < longitud; i++) {

                            ocupadas[fila + i][columna] = true;
                        }

                        colocado = true;
                    }
                }



            } else {

                if (columna + longitud <= 10) {

                    boolean libre = true;

                    for (int i = 0; i < longitud; i++) {

                        if (ocupadas[fila][columna + i]) {

                            libre = false;
                            break;
                        }
                    }

                    if (libre) {

                        Barco barco = new Barco(fila,columna,false,longitud);

                        barcos.add(barco);

                        for (int i = 0; i < longitud; i++) {

                            ocupadas[fila][columna + i] = true;
                        }

                        colocado = true;
                    }
                }
            }
        }
    }

    private Barco buscarBarco(int fila, int columna) {

        for (Barco barco : barcos) {

            if (barco.isVertical()) {

                for (int i = 0;
                     i < barco.getLongitud();
                     i++) {

                    if (barco.getX() + i == fila
                        && barco.getY() == columna) {

                        return barco;
                    }
                }


            } else {

                for (int i = 0;
                     i < barco.getLongitud();
                     i++) {

                    if (barco.getX() == fila
                        && barco.getY() + i == columna) {

                        return barco;
                    }
                }
            }
        }

        return null;
    }
    
    private void longitudBarco(Barco barco) {

        Color color;

        switch (barco.getLongitud()) {

            case 5:
                color = Color.white;
                break;

            case 4:
                color = Color.ORANGE;
                break;

            case 3:
                color = Color.YELLOW;
                break;

            case 2:
                color = Color.GREEN;
                break;

            default:
                color = Color.GRAY;
                break;
        }

        int fila = barco.getX();
        int columna = barco.getY();

        for (int i = 0; i < barco.getLongitud(); i++) {

            int f;
            int c;

            if (barco.isVertical()) {

                f = fila + i;
                c = columna;

            } else {

                f = fila;
                c = columna + i;
            }

            botones[f][c].setIcon(null);
            botones[f][c].setBackground(color);
            botones[f][c].setText("-");
        }
    }
   
}
