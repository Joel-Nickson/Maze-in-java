import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Maze extends JFrame implements ActionListener,MouseListener,MouseMotionListener{

  int init_gap=40,x,y,arr_i,arr_j,val,grid=20;
  boolean bool_si,bool_sj,bool_ei,bool_ej,bool_s,bool_e;
  JRadioButton start,end,blue,white;
  JButton draw;
  // JTextField l;
  int redraw;
  int startend[][]=new int[3][2];//{
                   //  {1,1,}, // initail start and stop count
                   //  {2,0,}, // start arr index
                   //  {3,9,}  // end arr index
                   // };
  int [][] map= new int[grid][grid];//{
                //    {0,0,0,0,0,0,0,0,0,0,},
                //    {0,0,0,0,0,0,0,0,0,0,},
                //    {5,1,1,1,0,0,0,0,0,0,},
                //    {0,0,0,1,0,0,0,1,1,3,},
                //    {0,0,0,1,0,0,0,1,0,0,},
                //    {0,0,0,1,0,0,0,1,0,0,},
                //    {0,0,0,1,1,1,1,1,0,0,},
                //    {0,0,0,0,0,0,0,0,0,0,},
                //    {0,0,0,0,0,0,0,0,0,0,},
                //    {0,0,0,0,0,0,0,0,0,0,}
                //  };
  
  int width= 20,height=20 ,rows=map.length ,columns=map[0].length;

  Maze(){
    // bool_si=false;
    // bool_sj=false;
    // bool_ei=false;
    // bool_ej=false;
    // bool_s=false;
    // bool_e=false;
    redraw=0;
    val=0;
    x=y=init_gap;
    // l= new JTextField("",13);
    JPanel pane = new JPanel();
    start = new JRadioButton("start");
    end = new JRadioButton("end");
    blue = new JRadioButton("Blue");
    white = new JRadioButton("White");
    draw = new JButton("Re-draw");

    ButtonGroup grp = new ButtonGroup();
    grp.add(start);
    grp.add(end);
    grp.add(blue);
    grp.add(white);

    pane.add(start);
    pane.add(end);
    pane.add(blue);
    pane.add(white);
    pane.add(draw);

    start.addActionListener(this);
    end.addActionListener(this);
    blue.addActionListener(this);
    white.addActionListener(this);
    draw.addActionListener(this);

    // JFrame f = new JFrame("Framey");
    addMouseMotionListener(this); 
    addMouseListener(this); 

    setLayout(new BorderLayout());
    add(pane,BorderLayout.SOUTH);
    // setLayout(null);
    // add(l);
    // l.setBounds(450,250,200,200);
    setSize(510,510);
    setVisible(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  public void actionPerformed(ActionEvent e){
    if(e.getSource()==start)
      val=5;
    else if(e.getSource()==end)
      val=3;
    else if(e.getSource()==blue)
      val=1;
    else if(e.getSource()==white)
      val=0;
    else{
      redraw=0;
      repaint();
    }
    System.out.println("val="+val);
  }
  public void mouseClicked(MouseEvent e) {  
      // l.setText("Mouse Clicked");  
  }  
  public void mouseEntered(MouseEvent e) {  
      // l.setText("Mouse Entered");  
  }  
  public void mouseExited(MouseEvent e) {  
      // l.setText("Mouse Exited");  
  }  
  public void mousePressed(MouseEvent e) {  
    // x=e.getXOnScreen();
    // y=e.getYOnScreen();
    x=e.getX();
    y=e.getY();
    System.out.println("x="+x+" ,y="+y);
    arr_xy(x,y);
    // l.setText("Mouse Pressed");  
  }  
  public void mouseReleased(MouseEvent e) {  
      // l.setText("Mouse Released");  
  }   
  public void mouseMoved(MouseEvent e) {  
      // l.setText("Mouse Moved");  
  }  
  public void mouseDragged(MouseEvent e) {      
    x=e.getX();
    y=e.getY();
    System.out.println("x="+x+" ,y="+y);
    arr_xy(x,y);
      // l.setText("Mouse Dragged");  
  }  
  void arr_xy(int x,int y){
    arr_i=(y-init_gap)/height;  //arr_i=(y-40)/40
    arr_j=(x-init_gap)/width;    
    map[arr_i][arr_j]=val;
    repaint();
    System.out.println("arr_i="+arr_i+" ,arr_j="+arr_j);
  }
  public void paint(Graphics g){
    // bool_si = (arr_i==startend[1][0]);
    // bool_sj = (arr_j==startend[1][1]);
    // bool_ei = (arr_i==startend[2][0]);
    // bool_ej = (arr_j==startend[2][1]);
    // bool_s = bool_si && bool_sj;
    // bool_e = bool_ei && bool_ej;
    if(redraw<1){
      for(int i=0;i<rows;i++){
        for(int j=0;j<columns;j++){
          g.drawRect(j*width+init_gap,i*height+init_gap,width,height);
          switch(map[i][j]){
            case 0: g.setColor( Color.white );
                  g.fillRect(j*width+init_gap,i*height+init_gap,width,height);
            break;
            case 1: g.setColor( Color.blue );
                  g.fillRect(j*width+init_gap,i*height+init_gap,width,height);
            break;
            case 3: g.setColor( Color.red );
                  g.fillRect(j*width+init_gap,i*height+init_gap,width,height);

                  startend[2][0]=arr_i;
                  startend[2][1]=arr_j;
            break;
            case 5: g.setColor( Color.green );
                  g.fillRect(j*width+init_gap,i*height+init_gap,width,height);

                  startend[1][0]=arr_i;
                  startend[1][1]=arr_j;  
            break;
          }
        }  
      }
       // draw grid lines
      g.setColor(Color.black);
      int htOfRow = height / rows;
      for (int k = 0; k <= rows; k++) {
          g.drawLine(0+init_gap, k * htOfRow*rows+init_gap, width*columns+init_gap, k * htOfRow*rows+init_gap);
      }

      int wdOfRow = width / columns;
      for (int k = 0; k <= columns; k++) {
          g.drawLine(k * wdOfRow*columns+init_gap, 0+init_gap, k * wdOfRow*columns+init_gap, height*rows+init_gap);
      }
      redraw++;
      val=1;
    }
    else{
      g.drawRect(arr_j*width+init_gap,arr_i*height+init_gap,width,height);
      switch(val){
        case 0: g.setColor( Color.white );
              g.fillRect(arr_j*width+init_gap,arr_i*height+init_gap,width,height);
        break;
        case 1: g.setColor( Color.blue );
              g.fillRect(arr_j*width+init_gap,arr_i*height+init_gap,width,height);
        break;
        case 3: g.setColor( Color.white );
              g.fillRect(startend[2][1]*width+init_gap,startend[2][0]*height+init_gap,width,height);
              map[startend[2][0]][startend[2][1]]=0;
              startend[2][0]=arr_i;
              startend[2][1]=arr_j;
                
              g.setColor( Color.red );
              g.fillRect(arr_j*width+init_gap,arr_i*height+init_gap,width,height);
        break;
        case 5: g.setColor( Color.white );
              g.fillRect(startend[1][1]*width+init_gap,startend[1][0]*height+init_gap,width,height);
              map[startend[1][0]][startend[1][1]]=0;
              startend[1][0]=arr_i;
              startend[1][1]=arr_j;
                
              g.setColor( Color.green );
              g.fillRect(arr_j*width+init_gap,arr_i*height+init_gap,width,height);
        break;
      }

      g.setColor(Color.black);
      int k= arr_i;
      // int htOfRow = height / rows;
      g.drawLine(0+init_gap, k * height+init_gap, width*columns+init_gap, k * height+init_gap);
      g.drawLine(0+init_gap, (k+1) * height+init_gap, width*columns+init_gap, (k+1) * height+init_gap);

      k=arr_j;
      // int wdOfRow = width / columns;
      g.drawLine(k * width+init_gap, 0+init_gap, k * width+init_gap, height*rows+init_gap);
      g.drawLine((k+1) * width+init_gap, 0+init_gap, (k+1) * width+init_gap, height*rows+init_gap);
    }
  }
  public static void main(String args[]){
    new Maze();
  }
}

// class Grids extends Canvas {

//     int width, height, rows, columns;

//     Grids(int w, int h, int r, int c) {
//         setSize(width = w, height = h);
//         rows = r;
//         columns = c;
//     }

//     @Override
//     public void paint(Graphics g) {
//         int k;
//         width = getSize().width;
//         height = getSize().height;

//         int htOfRow = height / (rows);
//         for (k = 0; k < rows; k++) {
//             g.drawLine(0, k * htOfRow, width, k * htOfRow);
//         }

//         int wdOfRow = width / (columns);
//         for (k = 0; k < columns; k++) {
//             g.drawLine(k * wdOfRow, 0, k * wdOfRow, height);
//         }
//     }
// }

// public class DrawGrids extends Frame {

//     DrawGrids(String title, int w, int h, int rows, int columns) {
//         setTitle(title);
//         Grids grid = new Grids(w, h, rows, columns);
//         add(grid);
//     }

//     public static void main(String[] args) {
//         new DrawGrids("Draw Grids", 200, 200, 2, 10).setVisible(true);
//     }
// }
// ﻿
