
import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
        //
        int boardAltura = 600;
        int boardLargura = 600;
        JFrame janela = new JFrame("Cobrinha ;)");
        //janela config
        janela.setVisible(true);
        janela.setSize(boardAltura, boardLargura);
        janela.setLocationRelativeTo(null); // janela no meio da tela
        janela.setResizable(false); //nao permite mudar tamanho
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //sair no X
        //

        //integrando o Game.Java aqui
        Game game = new Game(boardAltura, boardLargura);
        janela.add(game);
        janela.pack();
        game.requestFocus();
    }

}
