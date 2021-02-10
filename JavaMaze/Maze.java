import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Maze extends JFrame implements ActionListener,MouseListener,MouseMotionListener{

  int init_gap=40,x,y,arr_i,arr_j,val,grid=10;
  JRadioButton start,end,blue,white;
  JButton draw,paint;
  // JTextField l;
  int redraw;
  Color gray= new Color(200,200,200);
  int startend[][]=new int[3][2];//{
                   //  {1,1,}, // initail start and stop count
                   //  {2,0,}, // start arr index
                   //  {3,9,}  // end arr index
                   // };
  int [][] grids= new int[grid][grid];//{
                 //   {1,1,1,1,1,1,1,1,1,1,},
                 //   {1,1,1,0,0,0,0,5,1,1,},
                 //   {1,1,1,0,1,1,1,1,1,1,},
                 //   {1,1,1,0,1,1,1,1,1,3,},
                 //   {1,1,1,0,1,1,1,1,1,0,},
                 //   {1,1,1,0,0,1,1,1,1,0,},
                 //   {1,1,1,1,0,1,1,1,1,0,},
                 //   {1,1,1,1,0,1,1,1,1,0,},
                 //   {1,1,1,1,0,0,0,0,0,0,},
                 //   {1,1,1,1,1,1,1,1,1,1,}
                 // };
  
  int width= 40,height=40 ,rows=grids.length ,columns=grids[0].length;

  Maze(){
    redraw=0;
    val=0;
    x=y=init_gap;
    // l= new JTextField("",13);
    JPanel pane = new JPanel();
    start = new JRadioButton("start");
    end = new JRadioButton("end");
    blue = new JRadioButton("Block");
    white = new JRadioButton("Eraser");
    draw = new JButton("Re-draw");

    blue.setSelected(true);

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

    // JPanel rpane = new JPanel();
    paint = new JButton("Re-paint");
    // rpane.add(paint);
    // rpane.setLayout(null);
    // paint.setBounds(550,550,200,200);

    start.addActionListener(this);
    end.addActionListener(this);
    blue.addActionListener(this);
    white.addActionListener(this);
    draw.addActionListener(this);
    paint.addActionListener(this);

    addMouseMotionListener(this); 
    addMouseListener(this); 

    setLayout(new BorderLayout());
    add(pane,BorderLayout.SOUTH);
    add(paint,BorderLayout.EAST);
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
    else if(e.getSource()==draw){
      grids = new int[rows][columns];
      redraw=0;
      repaint();
    }
    else {//if(e.getSource()==paint){
      redraw=0;
      repaint();
    }
    System.out.println("val="+val);
  }

  public void mouseClicked(MouseEvent e) { }  
  public void mouseEntered(MouseEvent e) { }  
  public void mouseExited(MouseEvent e) { } 
  public void mouseReleased(MouseEvent e) { }   
  public void mousePressed(MouseEvent e) {  
    // x=e.getXOnScreen();
    // y=e.getYOnScreen();
    x=e.getX();
    y=e.getY();
    System.out.println("x="+x+" ,y="+y);
    arr_xy(x,y);
    // l.setText("Mouse Pressed");  
  }   
  public void mouseMoved(MouseEvent e) { }  
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
    grids[arr_i][arr_j]=val;
    repaint();
    System.out.println("arr_i="+arr_i+" ,arr_j="+arr_j);
  }
  int arrNumToGridNum(int i,int j){
    return i*rows+j;
  }
  int[] gridNumToArrNum(int num){
    int i=num/columns;
    int j=num%columns;

    int ij[] = {i,j};
    return ij;
  }



  public void paint(Graphics g){
    if(redraw<1){
      for(int i=0;i<rows;i++){
        for(int j=0;j<columns;j++){
          g.drawRect(j*width+init_gap,i*height+init_gap,width,height);
          switch(grids[i][j]){
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
              g.fillRect(arr_j*width+init_gap ,arr_i*height+init_gap ,width,height);
        break;
        case 1: g.setColor( Color.blue );
              g.fillRect(arr_j*width+init_gap,arr_i*height+init_gap,width,height);
        break;
        case 3: g.setColor( Color.white );
              g.fillRect(startend[2][1]*width+init_gap,startend[2][0]*height+init_gap,width,height);
              grids[startend[2][0]][startend[2][1]]=0;

              startend[2][0]=arr_i;
              startend[2][1]=arr_j;  

              g.setColor( Color.red );
              g.fillRect(arr_j*width+init_gap,arr_i*height+init_gap,width,height);
        break;
        case 5: g.setColor( Color.white );
              g.fillRect(startend[1][1]*width+init_gap,startend[1][0]*height+init_gap,width,height);
              grids[startend[1][0]][startend[1][1]]=0;

              startend[1][0]=arr_i;
              startend[1][1]=arr_j;  

              g.setColor( Color.green );
              g.fillRect(arr_j*width+init_gap,arr_i*height+init_gap,width,height);
        break;
      }
      
      //grid lines
      g.setColor(Color.black);

      int k= arr_i; //top & bottom
      g.drawLine(0+init_gap, k * height+init_gap, width*columns+init_gap, k * height+init_gap);
      g.drawLine(0+init_gap, (k+1) * height+init_gap, width*columns+init_gap, (k+1) * height+init_gap);

      k=arr_j; // right & left]
      g.drawLine(k * width+init_gap, 0+init_gap, k * width+init_gap, height*rows+init_gap);
      g.drawLine((k+1) * width+init_gap, 0+init_gap, (k+1) * width+init_gap, height*rows+init_gap);

    }
  }
  public static void main(String args[]){
    new Maze();
  }
}
