package modelos;

public class Barco {

    private int x;
    private int y;
    private boolean vertical;
    private boolean hundido;
    private int longitud;
    private int impactos;

    public Barco(int x, int y, boolean vertical, int longitud) {

        this.x = x;
        this.y = y;
        this.vertical = vertical;
        this.longitud = longitud;
        this.hundido = false;
        this.impactos = 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isVertical() {
        return vertical;
    }

    public int getLongitud() {
        return longitud;
    }

    public boolean isHundido() {
        return hundido;
    }

    public void recibirImpacto() {

        impactos++;

        if (impactos >= longitud) {
            hundido = true;
        }
    }
}