import java.util.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class Maze extends JFrame implements ActionListener,MouseListener,MouseMotionListener{

  ArrayDeque<Integer> que ;
  int init_gap=50 ,x ,y  ,arr_i ,arr_j,val,grid=20;
  JRadioButton start,end,blue,white;
  JButton retry,paint,find,rand;
  int redraw;

  int startend[][]=new int[3][2];//{
                   //  {1,1,}, // initail start and stop count
                   //  {1,7,}, // start arr index
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
  
  int width= 30,height=30 ,rows=grids.length ,columns=grids[0].length;
  int endNum,startNum,max;

  int ij[], dist[], edgeFrom[];
  boolean isVisited[] ,found;

  Random r;
  Color gray= new Color(100,100,100);

  Maze(){
    que = new ArrayDeque<Integer>(50);
    r = new Random();
    // width=400/grid;
    // height=400/grid;
    redraw=0;
    val=0;
    x=y=init_gap;

    max= rows*(rows+1)+columns;

    ij = new int[2];
    dist = new int[max];
    edgeFrom = new int[max];
    isVisited = new boolean[max] ;
    found=false;

    // l= new JTextField("",13);
    start = new JRadioButton("start");
    end = new JRadioButton("end");
    blue = new JRadioButton("Block");
    white = new JRadioButton("Eraser");
    retry = new JButton("Re-try");
    find = new JButton("Find-Path");
    rand = new JButton("Random");

    blue.setSelected(true);

    ButtonGroup grp = new ButtonGroup();
    grp.add(start);
    grp.add(end);
    grp.add(blue);
    grp.add(white);

    JPanel pane = new JPanel();
    pane.add(start);
    pane.add(end);
    pane.add(blue);
    pane.add(white);
    pane.add(retry);
    pane.add(find);
    pane.add(rand);

    JPanel tpane = new JPanel();
    paint = new JButton("Re-paint");
    tpane.setLayout(new FlowLayout());
    tpane.add(paint);
    // paint.setBounds(550,550,200,200);

    start.addActionListener(this);
    end.addActionListener(this);
    blue.addActionListener(this);
    white.addActionListener(this);
    retry.addActionListener(this);
    paint.addActionListener(this);
    find.addActionListener(this);
    rand.addActionListener(this);

    addMouseMotionListener(this); 
    addMouseListener(this); 

    setLayout(new BorderLayout());
    add(pane,BorderLayout.SOUTH);
    add(tpane,BorderLayout.NORTH);

    setSize(510,510);
    setExtendedState( getExtendedState()|JFrame.MAXIMIZED_BOTH );
    setVisible(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBorder();
  }
  public void actionPerformed(ActionEvent e){
    if(e.getSource()==start){
      val=5;
      redraw=1;
    }
    else if(e.getSource()==end){
      val=3;
      redraw=1;
    }
    else if(e.getSource()==blue)
      val=1;
    else if(e.getSource()==white)
      val=0;
    else if(e.getSource()==retry){
      found=false;
      startend=new int[3][2];
      blue.setSelected(true);
      val=1;
      // // grids = new int[rows][columns];
      // // edgeFrom = new int[max];
      // // isVisited = new boolean[max];
      setInitialToVal(grids,0);
      setInitialToVal(edgeFrom,0);
      setInitialToVal(isVisited,false);
      setBorder();
      redraw=0;
      repaint();
      // new Maze();
    }
    else if(e.getSource()==find){
      found=false;
      dist[startNum]=-1;
      edgeFrom[startNum]=-1;
      pathFinder(startNum);
    }
    else if(e.getSource()==rand)
      randomGridGenerator();
    else {
      redraw=0;
      repaint();
    }
    System.out.println("action perfval="+val);
  }


  public void mouseClicked(MouseEvent e) { }  
  public void mouseEntered(MouseEvent e) { }  
  public void mouseExited(MouseEvent e) { } 
  public void mouseReleased(MouseEvent e) { }   
  public void mousePressed(MouseEvent e) {  
    x=e.getX();
    y=e.getY();
    System.out.println("x="+x+" ,y="+y);
    arr_xy(x,y);
  }   
  public void mouseMoved(MouseEvent e) { }  
  public void mouseDragged(MouseEvent e) {      
    x=e.getX();
    y=e.getY();
    System.out.println("x="+x+" ,y="+y);
    arr_xy(x,y);
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

  void setInitialToVal(int arr[][],int val){
    for(int i=0;i<rows;i++)
      for(int j=0;j<columns;j++)
        arr[i][j]=val;
  }
  void setInitialToVal(int arr[],int val){
    for(int i=0;i<arr.length;i++)
      arr[i]=val;
  }
  void setInitialToVal(boolean arr[],boolean val){
    for(int i=0;i<arr.length;i++)
      arr[i]=val;
  }
  int count(int val){
    int c=0;
    while(val>0){
      val/=10;
      c++;
    }
    return c;
  }
  void printArr(int arr[]){
    int val;
    for(int i=0;i<rows;i++){
      for(int j=0;j<columns;j++){ 
        val=arr[i*rows+j];
        if(count(val)==1)
          System.out.print(val+"   ");
        else if(count(val)==2)
          System.out.print(val+"  ");
        else if(count(val)==3)
          System.out.print(val+" ");

      }
      System.out.println();
    }
  }

  void randomGridGenerator(){
    int n = (max+1)/2;
    int gridNum,num;
    for(int i=0;i<n;i++){
      num = r.nextInt((max-2*columns));
      ij=gridNumToArrNum(num);
      grids[ij[0]][ij[1]]=1;
      System.out.println(num+" "+Arrays.toString(ij));
    }
    redraw=0;
    repaint();
  }

  void setBorder(){
    for(int j=0;j<columns;j++){
      grids[0][j]=1;
      grids[rows-1][j]=1;
    }
    for(int i=0;i<rows;i++){
      grids[i][0]=1;
      grids[i][columns-1]=1;
    }
  }
  
  void pathFinder(int gridNum){
    grids[0][0]=1;
    System.out.println(gridNum+","+isVisited[gridNum]+" "+found);
    que.add(gridNum);

    ij = gridNumToArrNum(gridNum);
    arr_i = ij[0];
    arr_j = ij[1];
    adjElements(gridNum);
    isVisited[gridNum]=true;

    while(!found){// || edgeFrom[endNum]==0){// || edgeFrom[endNum]==0){   
      if(!isVisited[gridNum]){

        ij= gridNumToArrNum(gridNum);
        arr_i = ij[0];
        arr_j = ij[1];
        grids[ arr_i ][ arr_j ] = 8;
        
        adjElements(gridNum);
        isVisited[gridNum]=true;
      }
      System.out.println(gridNum+","+edgeFrom[gridNum]+","+dist[gridNum]+" "+found+" "+grids[ij[0]][ij[1]]);
      System.out.println("**"+gridNum+","+edgeFrom[gridNum]+","+dist[gridNum]+" "+found+" "+grids[ij[0]][ij[1]]);
      System.out.println("Head element remove : " + gridNum);//+" "+que);

      // redraw=0;
      // repaint();
      // try{  Thread.sleep(100);  }
      // catch(Exception e){  System.out.println(e);  }

      if(gridNum == endNum)
        found=true;
      if(found && edgeFrom[endNum]!=0)
        break;    

      if(que.isEmpty())
        break;
      gridNum=que.removeFirst();
    }
    System.out.println("Head element remove : " + gridNum+" "+que);
    que.clear();
    System.out.println("out of while");
    if(found){
      System.out.println("inside found");
      int shortpath=endNum;
      while(edgeFrom[shortpath]!=-1){
        shortpath=edgeFrom[shortpath];
        ij=gridNumToArrNum(shortpath);
        arr_i=ij[0];
        arr_j=ij[1];
        grids[arr_i][arr_j]=7;
        System.out.println("path="+shortpath+","+endNum+" "+Arrays.toString(gridNumToArrNum(endNum))+","+startNum+" "+Arrays.toString(gridNumToArrNum(startNum)));
        if(shortpath==0){
          // System.out.println( Arrays.toString(edgeFrom) );//+" "+que);
          printArr(edgeFrom);
          try{  
            Thread.sleep(500000);  
            break;
          }
          catch(Exception e){  System.out.println(e);  }
        }
      }
      System.out.println("out of while again");
      repaint();
    }
    ij=gridNumToArrNum(startNum);
    grids[ij[0]][ij[1]]=5;
    ij=gridNumToArrNum(endNum);
    grids[ij[0]][ij[1]]=3;
    redraw=0;
    repaint();
  }

  void adjElements(int gridNum){
    // right
    if( (arr_j+1 < columns-1) && (grids[arr_i][arr_j+1] != 1) && !isVisited[gridNum+1] ){ //checks if (its not a border) and (not a blue block)          
      if(dist[gridNum+1]==0){
        dist[gridNum+1]=dist[gridNum]+1;
        edgeFrom[gridNum+1]=gridNum;
      }
      else if(dist[gridNum+1]>dist[gridNum]+1){
        dist[gridNum+1]=dist[gridNum]+1;
        edgeFrom[gridNum+1]=gridNum;
      }      
      que.addLast(gridNum+1);
      System.out.println("1--"+que);
    }
    // down
    if( (gridNum+columns < max-columns) && (grids[arr_i+1][arr_j] != 1) && !isVisited[gridNum+columns] ){
      if(dist[gridNum+columns]==0){
        dist[gridNum+columns]=dist[gridNum]+1;
        edgeFrom[gridNum+columns]=gridNum;
      }
      else if(dist[gridNum+columns]>dist[gridNum]+1){
        dist[gridNum+columns]=dist[gridNum]+1;
        edgeFrom[gridNum+columns]=gridNum;
      }
      que.addLast(gridNum+columns);
      System.out.println("2--"+que);
    }
    // left
    if( (arr_j-1 > 0) && (grids[arr_i][arr_j-1] != 1) && !isVisited[gridNum-1] ){
      if(dist[gridNum-1]==0){
        dist[gridNum-1]=dist[gridNum]+1;
        edgeFrom[gridNum-1]=gridNum;
      }
      else if(dist[gridNum-1]>dist[gridNum]+1){
        dist[gridNum-1]=dist[gridNum]+1;
        edgeFrom[gridNum-1]=gridNum;
      }      
      que.addLast(gridNum-1);
      System.out.println("3--"+que);
    }
    // up
    if( (gridNum-columns > columns) && (grids[arr_i-1][arr_j] != 1) && !isVisited[gridNum-columns] ){
      if(dist[gridNum-columns]==0){
        dist[gridNum-columns]=dist[gridNum]+1;
        edgeFrom[gridNum-columns]=gridNum;
      }
      else if(dist[gridNum-columns]>dist[gridNum]+1){
        dist[gridNum-columns]=dist[gridNum]+1;
        edgeFrom[gridNum-columns]=gridNum;
      }
      que.addLast(gridNum-columns);
      System.out.println("4--"+que);
    }
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
            break;
            case 5: g.setColor( Color.green );
                  g.fillRect(j*width+init_gap,i*height+init_gap,width,height);
            break;
            case 7: g.setColor( Color.yellow );
                  g.fillRect(j*width+init_gap,i*height+init_gap,width,height);
            break;
            case 8: g.setColor( gray );
                  g.fillRect(j*width+init_gap,i*height+init_gap,width,height);
            break;
          }
          g.setColor( Color.black );
          g.drawString(String.valueOf(arrNumToGridNum(i,j)),j*width+init_gap,i*height+init_gap+height);
        }  
      }
       // draw grid lines
      g.setColor(Color.black);
      // int htOfRow = height / rows;
      for (int k = 0; k <= rows; k++) {
          g.drawLine(0+init_gap, k * height+init_gap, width*columns+init_gap, k * height+init_gap);
      }

      // int wdOfRow = width / columns;
      for (int k = 0; k <= columns; k++) {
          g.drawLine(k * width+init_gap, 0+init_gap, k * width+init_gap, height*rows+init_gap);
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

              endNum = arrNumToGridNum(arr_i,arr_j);

              g.setColor( Color.red );
              g.fillRect(arr_j*width+init_gap,arr_i*height+init_gap,width,height);
        break;
        case 5: g.setColor( Color.white );
              g.fillRect(startend[1][1]*width+init_gap,startend[1][0]*height+init_gap,width,height);
              grids[startend[1][0]][startend[1][1]]=0;

              startend[1][0]=arr_i;
              startend[1][1]=arr_j;  

              startNum = arrNumToGridNum(arr_i,arr_j);

              g.setColor( Color.green );
              g.fillRect(arr_j*width+init_gap,arr_i*height+init_gap,width,height);
        break;
        case 7: g.setColor( Color.yellow );
              g.fillRect(arr_j*width+init_gap,arr_i*height+init_gap,width,height);
        break;
        case 8: g.setColor( gray );
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
}







public class MazeThread implements Runnable {
  Maze objm;
  Thread t;
  public MazeThread(){
    // objm=new Maze();
    t=new Thread(this);
    t.start();
  }
  public void run() {
      // objm.display();
      new Maze();
    
  }

  public static void main(String args[]){
    new MazeThread();
  }
}
