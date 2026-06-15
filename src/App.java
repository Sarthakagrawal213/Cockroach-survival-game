import javax.swing.JFrame;
public class App {
    public static void main(String[] args) throws Exception {
        int rowCount=21;
        int colCount=19;
        int titleSize=32;
        int boardWidth=colCount*titleSize;
        int boardHeight=rowCount*titleSize;
        JFrame frame = new JFrame("Cockroach Survival Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(boardWidth, boardHeight);
        // frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        cockroach cockroachPanel = new cockroach();
        frame.add(cockroachPanel);
        frame.pack();
        cockroachPanel.requestFocus();
        frame.setVisible(true);
    }

}
