import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Random;

import javax.swing.*;

public class cockroach extends JPanel implements ActionListener, KeyListener {
        class Block{
                int x;
                int y;
                int width;
                int height;
                Image image;
                int StartX;
                int StartY;
                char direction='U';
                int velocityX=0;
                int velocityY=0;
                public Block(Image image,int x,int y,int width,int height){
                        this.image=image;
                        this.x=x;
                        this.y=y;
                        this.width=width;
                        this.height=height;
                        this.StartX=x;
                        this.StartY=y;
                }
                void updateDirection(char direction){
                        char prev=this.direction;

                        this.direction=direction;
                        updatvelocity();
                        this.x+=this.velocityX;
                        this.y+=this.velocityY;
                        for(Block wall:walls){
                                if(collision(this, wall)){
                                        this.x-=this.velocityX;
                                        this.y-=this.velocityY;
                                        this.direction=prev;
                                        updatvelocity();
                                }
                        }
                }
                void updatvelocity(){
                        if(this.direction=='U'){
                                this.velocityX=0;
                                this.velocityY=-titleSize/4;
                        }
                        else if(this.direction=='D'){
                                this.velocityX=0;
                                this.velocityY=titleSize/4;
                        }
                        else if(this.direction=='L'){
                                this.velocityY=0;
                                this.velocityX=-titleSize/4;
                        }
                        else if(this.direction=='R'){
                                this.velocityY=0;
                                this.velocityX=titleSize/4;
                        }
                }
                void reset(){
                        this.x=this.StartX;
                        this.y=this.StartY;
                }

        }
        private int rowCount=21;
        private int colCount=19;
        private int titleSize=32;
        private int boardWidth=colCount*titleSize;
        private int boardHeight=rowCount*titleSize;
        private Image wallimage;
        private Image blueghostImage;
        private Image orangeghostImage;
        private Image pinkghostImage;
        private Image redGhostImage;
        private Image packmanupImage;
        private Image packmandownImage;
        private Image packmanleftImage;
        private Image packmanrightImage;
        HashSet<Block> walls;
        HashSet<Block> foods;
         HashSet<Block> ghosts;
         Block pacman;
         Timer gameloop;
         char[] directions={'U','L','R','D'};
         Random random=new Random();
         int score=0;
         int lives=3;
         boolean gameOver=false;
         private String[] tileMap = {
        "XXXXXXXXXXXXXXXXXXX",
        "X        X        X",
        "X XX XXX X XXX XX X",
        "X                 X",
        "X XX X XXXXX X XX X",
        "X    X       X    X",
        "X XX XXXX XXXX  XXX",
        "XOO  X       X  OOX",
        "X XX X  XrX  X XX X",
        "X       bpo       X",
        "X XX X XXXXX X XX X",
        "XOO  X       X  OOX",
        "X XX X XXXXX X XX X",
        "X        X        X",
        "X XX XXX X XXX X  X",
        "X  X     P     X  X",
        "X  X X XXXXX X X  X",
        "X    X   X   X    X",
        "X   XXXX X XXXX   X",
        "X                 X",
        "XXXXXXXXXXXXXXXXXXX" 
    };


        cockroach() {
            setPreferredSize(new Dimension(boardWidth, boardHeight));
            setBackground(Color.BLACK);
            addKeyListener(this);
            setFocusable(true);
             wallimage = new ImageIcon(getClass().getResource("/wall.png")).getImage();
             blueghostImage = new ImageIcon(getClass().getResource("/blueGhost.png")).getImage();
                orangeghostImage = new ImageIcon(getClass().getResource("/orangeGhost.png")).getImage();
                pinkghostImage = new ImageIcon(getClass().getResource("/pinkGhost.png")).getImage();
                redGhostImage = new ImageIcon(getClass().getResource("/redGhost.png")).getImage();
                packmanupImage = new ImageIcon(getClass().getResource("/pacmanRight.png")).getImage();
                packmandownImage = new ImageIcon(getClass().getResource("/pacmanRight.png")).getImage();
                packmanleftImage = new ImageIcon(getClass().getResource("/pacmanRight.png")).getImage();
                packmanrightImage = new ImageIcon(getClass().getResource("/pacmanRight.png")).getImage();
                loadmap();
                for(Block ghost:ghosts){
                        char newdirection=directions[random.nextInt(4)];
                        ghost.updateDirection(newdirection);
                }
                gameloop= new Timer(50, this);
                gameloop.start();
        }
        public void loadmap(){
                walls=new HashSet<Block>();
                foods=new HashSet<Block>();
                ghosts=new HashSet<Block>();
                for(int r=0;r<rowCount;r++){
                        for(int c=0;c<colCount;c++){
                                String row=tileMap[r];
                                char tile=row.charAt(c);
                                int x=c*titleSize;
                                int y=r*titleSize;
                                if(tile=='X'){
                                        Block wall=new Block(wallimage, x, y, titleSize, titleSize);
                                        walls.add(wall);
                                }
                                else if(tile=='b'){
                                        Block ghost=new Block(blueghostImage, x, y, titleSize, titleSize);
                                        ghosts.add(ghost);
                                }
                                else if(tile=='o'){
                                        Block ghost=new Block(orangeghostImage, x, y, titleSize, titleSize);
                                        ghosts.add(ghost);
                                }
                                else if(tile=='p'){
                                        Block ghost=new Block(pinkghostImage, x, y, titleSize, titleSize);
                                        ghosts.add(ghost);
                                }
                                else if(tile=='r'){
                                        Block ghost=new Block(redGhostImage, x, y, titleSize, titleSize);
                                        ghosts.add(ghost);
                                }
                                else if(tile=='P'){
                                        pacman=new Block(packmanrightImage, x, y, titleSize, titleSize);
                                }
                                else if(tile==' '){
                                        Block food=new Block(null, x+14, y+14, 4, 4);
                                        foods.add(food);
                                }
                        }
                }
        }
        public void paintComponent(Graphics g){
                super.paintComponent(g);
                draw(g);
        }
        public void draw(Graphics g){
                g.drawImage(pacman.image, pacman.x,pacman.y,pacman.width,pacman.height,null);
                for(Block ghost:ghosts){
                        g.drawImage(ghost.image, ghost.x,ghost.y,ghost.width,ghost.height,null);
                }
                for(Block wall:walls){
                        g.drawImage(wall.image, wall.x,wall.y,wall.width,wall.height,null);
                }
                g.setColor(Color.WHITE);
                for(Block food:foods){
                        g.fillRect( food.x,food.y,food.width,food.height);
                }
                g.setFont(new Font("Arial",Font.PLAIN,18));
                if(gameOver){
                        g.drawString("Game Over: "+ String.valueOf(score),titleSize/2,titleSize/2);
                }else{
                        g.drawString("cockroach lives: "+ String.valueOf(lives) + " score: "+ String.valueOf(score),titleSize/2,titleSize/2);
                }
                
        }
        public void move(){
                pacman.x+=pacman.velocityX;
                pacman.y+=pacman.velocityY;
                for(Block wall : walls){
                        if(collision(pacman, wall)){
                               pacman.x-=pacman.velocityX;
                               pacman.y-=pacman.velocityY; 
                              
                               break;
                        }
                }
                for(Block ghost: ghosts){
                        if(collision(ghost, pacman)){
                             lives-=1;
                             if(lives==0){
                                gameOver=true;
                                return;
                             }
                             resetPositions();  
                        }
                       ghost.x+=ghost.velocityX;
                       ghost.y+=ghost.velocityY; 
                       for(Block wall : walls){
                        if(collision(ghost, wall)){
                               ghost.x-=ghost.velocityX;
                               ghost.y-=ghost.velocityY; 
                              ghost.updateDirection(directions[random.nextInt(4)]);
                               break;
                        }
                }
                }
                Block foodeat=null;
                for(Block food: foods){
                        if(collision(pacman, food)){
                                score+=10;
                                foodeat=food;
                        }
                }
                foods.remove(foodeat);
                if(foods.isEmpty()){
                        loadmap();
                        resetPositions();
                }
        }
        public boolean collision(Block a,Block b){
                return a.x<b.x+ b.width &&
                 a.x + a.width>b.x &&
                 a.y <b.y +b.height &&
                 a.y+ a.height > b.y;   
        }
        public void resetPositions(){
                pacman.reset();
                pacman.velocityX=0;
                pacman.velocityY=0;
                for(Block ghost:ghosts){
                        ghost.reset();
                        ghost.updateDirection(directions[random.nextInt(4)]);
                }
        }
        @Override
        public void actionPerformed(ActionEvent e) {
                move();
               repaint();
               if(gameOver){
                gameloop.stop();
               }
        }
        @Override
        public void keyTyped(KeyEvent e) {
                
        }
        @Override
        public void keyPressed(KeyEvent e) {
                
        }
        @Override
        public void keyReleased(KeyEvent e) {
                if(gameOver){
                        loadmap();
                        resetPositions();
                        lives=3;
                        score=0;
                        gameOver=false;
                        gameloop.start();
                }
               if(e.getKeyCode()==KeyEvent.VK_UP){
                pacman.updateDirection('U');
               }
               else if(e.getKeyCode()==KeyEvent.VK_DOWN){
                pacman.updateDirection('D');
               }
               else if(e.getKeyCode()==KeyEvent.VK_LEFT){
                pacman.updateDirection('L');
               }
               else if(e.getKeyCode()==KeyEvent.VK_RIGHT){
                pacman.updateDirection('R');
               }
        }
}
