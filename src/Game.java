import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
public class Game extends JPanel implements ActionListener, KeyListener {
    private class Grafico{
        int x;
        int y;
        
        Grafico(int x, int y) {
            this.x = x;
            this.y = y;
            
        }
    }
    
    int boardAltura;
    int boardLargura;
    int graficoSize = 25;
    
    //COBRA
    Grafico cobraHead;
    ArrayList<Grafico> cobraBody;
    
    //COMIDA
    Grafico comida;
    Random random;

    // game loop
    Timer gameLoop;
    int velocidadeX;
    int velocidadeY;
    
    boolean gameOver = false;

    Game(int boardAltura, int boardLargura) {

        
        this.boardAltura = boardAltura;
        this.boardLargura = boardLargura;
        setPreferredSize(new Dimension(this.boardAltura, this.boardLargura));
        setBackground(Color.gray);
        addKeyListener(this);

        setFocusable(true);
        cobraHead = new Grafico(5, 5);
        cobraBody = new ArrayList<Grafico>();
        
        comida = new Grafico(10, 10);
        random = new Random();
        posComida();
        
        velocidadeX = 0;
        velocidadeY = 0;
        
        gameLoop = new Timer(100, this);
        gameLoop.start();
    
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);

        
    }

    public void draw(Graphics g){
        //desenhando linhas pra dividir quadrados
        for (int i = 0; i < boardLargura/graficoSize; i++) {
            g.drawLine(i*graficoSize, 0, i*graficoSize, boardAltura);
            g.drawLine(0, i*graficoSize, boardLargura, i*graficoSize);
        }
        //criando comida
        g.setColor(Color.orange);
        g.fillRect(comida.x * graficoSize, comida.y * graficoSize, graficoSize, graficoSize);

        //criação da cobra
        g.setColor(Color.black);
        g.fillRect(cobraHead.x * graficoSize, cobraHead.y * graficoSize, graficoSize, graficoSize);
    
        //corpo da cobra dps de comer
        for (int i = 0; i < cobraBody.size(); i++) {
            Grafico cobraPart = cobraBody.get(i);
            g.fillRect(cobraPart.x * graficoSize, cobraPart.y * graficoSize, graficoSize, graficoSize);
            
        }
        //placar
        g.setFont(new Font("Arial", Font.BOLD, 36));
        if (gameOver) {
            g.setColor(Color.red);
            g.drawString("Game Over!!  " + String.valueOf(cobraBody.size()), graficoSize - 16, graficoSize);
        }
        else {
            g.drawString("Pontuação: " + String.valueOf(cobraBody.size()), graficoSize - 16, graficoSize);
        }
    }


    public void posComida() {
        comida.x = random.nextInt(boardLargura / graficoSize); // 0 - 24
        comida.y = random.nextInt(boardAltura / graficoSize); // 0 - 24 pos

    }

    public boolean colisao(Grafico grafico1, Grafico grafico2) {
        return grafico1.x == grafico2.x && grafico1.y == grafico2.y;
    }


    public void move() {
        //comendo comida
        if (colisao(cobraHead, comida)) {
            cobraBody.add(new Grafico(comida.x, comida.y));
            posComida();
        }
        //ima de comida
        for (int i = cobraBody.size()-1; i >= 0; i--) {
            Grafico cobraPart = cobraBody.get(i);
            if (i == 0) {
                cobraPart.x = cobraHead.x;
                cobraPart.y = cobraHead.y;

            }
            else {
                Grafico prevCobraPart = cobraBody.get(i-1);
                cobraPart.x = prevCobraPart.x;
                cobraPart.y = prevCobraPart.y;
            }
        
        }

        
        
        //corpo d cobra
        cobraHead.x += velocidadeX;
        cobraHead.y += velocidadeY;
    
        //gameover cond
        for (int i = 0; i < cobraBody.size(); i++) {
            Grafico cobraPart = cobraBody.get(i);
            //colisao da cobra coma  cobra
            if (colisao(cobraHead, cobraPart)) {
                gameOver = true;
            }
        }

        if (cobraHead.x * graficoSize < 0 || cobraHead.x * graficoSize > boardLargura ||
            cobraHead.y * graficoSize < 0 || cobraHead.y * graficoSize > boardAltura) {
                gameOver = true;
            }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver) {
            gameLoop.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
       //configurando teclas de movimento
        if (e.getKeyCode() == KeyEvent.VK_UP && velocidadeY != 1) {
        velocidadeX = 0;
        velocidadeY = -1;
       }
       else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocidadeY != -1){
        velocidadeX = 0;
        velocidadeY = 1;
       }
       else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocidadeX != 1){
        velocidadeX = -1;
        velocidadeY = 0;
       }
       else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocidadeX != -1){
        velocidadeX = 1;
        velocidadeY = 0;
       }

    }
    
    //
    @Override
    public void keyTyped(KeyEvent e) {
        
    }


    @Override
    public void keyReleased(KeyEvent e) {
        
    }
}


