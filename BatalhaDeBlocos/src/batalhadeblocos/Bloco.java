/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package batalhadeblocos;

/**
 *
 * @author Rafael
 */
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Bloco extends Rectangle {
    boolean morto = false;
    final String tipo;

    Bloco(int posX, int posY, int largura, int altura, 
    String tipo, Color color) {
        super(largura, altura, color);
        this.tipo = tipo;
        setTranslateX(posX);
        setTranslateY(posY);
    }

    void moverParaEsquerda() {
        setTranslateX(getTranslateX() - 5);
    }

    void moverParaDireita() {
        setTranslateX(getTranslateX() + 5);
    }

    void moverParaCima() {
        setTranslateY(getTranslateY() - 5);
    }

    void moverParaBaixo() {
        setTranslateY(getTranslateY() + 5);
    }
}
