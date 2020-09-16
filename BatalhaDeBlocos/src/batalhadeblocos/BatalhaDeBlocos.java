/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package batalhadeblocos;

import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.List;
import static javafx.application.Application.launch;

public class BatalhaDeBlocos extends Application {

    private Pane tela = new Pane();

    private double t = 0;

    private Bloco jogador = new Bloco(300, 550, 40, 40, "jogador", Color.BLUE);

    private Parent createContent() {
        tela.setPrefSize(600, 600);

        tela.getChildren().add(jogador);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                atualizarTela();
            }
        };

        timer.start();

        proximoNivel();

        return tela;
    }

    private void proximoNivel() {
        for (int i = 0; i < 5; i++) {
            Bloco p = new Bloco(90 + i * 100, 150, 30, 30, "inimigo", Color.RED);
            tela.getChildren().add(p);
        }
    }

    // Versão convencional
    private List<Bloco> personagens() {
        List<Bloco> lista = new ArrayList();
        for(Object o : tela.getChildren()){
            lista.add((Bloco)o);
        }
        return lista;
    }

    private void atualizarTela() {
        t += 0.016;

        personagens().forEach(s -> {
            switch (s.tipo) {

                case "tiroinimigo":
                    s.moverParaBaixo();

                    if (s.getBoundsInParent()
                    .intersects(jogador.getBoundsInParent())) {
                        jogador.morto = true;
                        s.morto = true;
                    }
                    break;

                case "tirojogador":
                    s.moverParaCima();

                    personagens().stream()
                    .filter(e -> e.tipo.equals("inimigo")).forEach(inimigo -> {
                        if (s.getBoundsInParent()
                        .intersects(inimigo.getBoundsInParent())) {
                            inimigo.morto = true;
                            s.morto = true;
                        }
                    });

                    break;

                case "inimigo":
                    if (t > 2) {
                        if (Math.random() < 0.3) {
                            atirar(s);
                        }
                    }

                    break;
            }
        });

        tela.getChildren().removeIf(n -> {
            Bloco s = (Bloco) n;
            return s.morto;
        });

        if (t > 2) {
            t = 0;
        }
       
    }

    private void atirar(Bloco quem) {
        Bloco bloco = new Bloco((int) quem.getTranslateX() + 20,
            (int) quem.getTranslateY(), 5, 20, "tiro" + quem.tipo, Color.BLACK);

        tela.getChildren().add(bloco);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Scene cena = new Scene(createContent());

        cena.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case A:
                    jogador.moverParaEsquerda();
                    break;
                case D:
                    jogador.moverParaDireita();
                    break;
                case SPACE:
                    atirar(jogador);             
                    break;
            }
        });

        stage.setScene(cena);
        stage.show();
    }

    
    public static void main(String[] args) {
        launch(args);
    }
    
}