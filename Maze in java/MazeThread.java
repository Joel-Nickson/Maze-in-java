import java.util.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class Maze extends JFrame implements ActionListener,MouseListener,MouseMotionListener,Runnable{

  Thread t1,t2,t3;
  ArrayDeque<Integer> que, stack;
  int x ,y  ,arr_i ,arr_j,val,grid=30;
  JRadioButton start,end,block,white ;
  JButton retry,paint,find,rand;
  JTextField numText;
  int redraw,s_count;
  int y_gap,x_gap1,x_gap2;
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
  
  int width ,height ,rows ,columns ,endNum ,startNum ,max;

  int ij[], dist[], edgeFrom[];
  boolean isVisited[] ,found;

  Random r;
  Color gray= new Color(100,100,100);
  JComboBox cb;

  Maze(){
    que = new ArrayDeque<Integer>(50);
    stack = new ArrayDeque<Integer>(50);
    numText = new JTextField();
    r = new Random();
    redraw=0;
    s_count=20;
    val=0;
    y=y_gap = 120;
    x=x_gap1 = 470;
    // x_gap2 = 250+600;

    width=600/grid; //optimal = 30
    height=600/grid;  //optimal = 30
    rows = grids.length; 
    columns = grids[0].length;

    max= rows*(rows+1)+columns;

    ij = new int[2];
    dist = new int[max];
    edgeFrom = new int[max];
    isVisited = new boolean[max] ;
    found=false;

    // l= new JTextField("",13);
    start = new JRadioButton("start");
    end = new JRadioButton("end");
    block = new JRadioButton("Block");
    white = new JRadioButton("Eraser");
    retry = new JButton("Re-try");
    find = new JButton("Find-Path");
    rand = new JButton("Random");  

    block.setSelected(true);

    ButtonGroup grp = new ButtonGroup();
    grp.add(start);
    grp.add(end);
    grp.add(block);
    grp.add(white);

    JPanel pane = new JPanel();
    pane.add(start);
    pane.add(end);
    pane.add(block);
    pane.add(white);
    pane.add(retry);
    pane.add(find);
    pane.add(rand);
    pane.setBackground(Color.CYAN);
    pane.setForeground(Color.black);
    pane.setOpaque(true);

    String arrForPathFinder[]={"bfs","dfs"};//,"C#","Java","PHP"};        
    cb =new JComboBox(arrForPathFinder);

    JPanel tpane = new JPanel();
    paint = new JButton("Re-paint");
    tpane.setLayout(new FlowLayout());
    tpane.add(paint); 
    tpane.add(cb);
    tpane.add(numText);

    start.addActionListener(this);
    end.addActionListener(this);
    block.addActionListener(this);
    white.addActionListener(this);
    retry.addActionListener(this);
    paint.addActionListener(this);
    find.addActionListener(this);
    rand.addActionListener(this);

    addMouseMotionListener(this); 
    addMouseListener(this); 

    setBackground(new Color(150,150,150));
    setLayout(new BorderLayout());
    add(pane,BorderLayout.SOUTH);
    add(tpane,BorderLayout.NORTH);


    setSize(510,510);
    setExtendedState( getExtendedState()|JFrame.MAXIMIZED_BOTH );
    setVisible(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBorder();
  }
  public void run(){

    setInitialToVal(grids,0);
    setInitialToVal(dist,0);
    setInitialToVal(edgeFrom,0);
    setInitialToVal(isVisited,false);
    setBorder();
    // try{
    //     Thread.sleep(s_count);
    // }
    // catch(Exception e){ System.out.println(e); }
  }
  // public void run(){}
  public void actionPerformed(ActionEvent e){
    t1 = new Thread(this);
    if(e.getSource()==start){           //green
      val=5;
      redraw=1;
      // if()
      // grid=neumtext.getText();
      // redraw=0;
      // repaint();
    }
    else if(e.getSource()==end){        //red
      val=3;
      redraw=1;
    }
    else if(e.getSource()==block)       //black
      val=1;
    else if(e.getSource()==white)       //eraser
      val=0;
    else if(e.getSource()==retry){      //retry
      t1.start();
      found=false;
      startend=new int[3][2];
      block.setSelected(true);
      val=1;
      redraw=0;
      repaint();
    }
    else if(e.getSource()==find){     // path finder
      found=false;
      dist[startNum]=-1;
      edgeFrom[startNum]=-1;
      // int copy[][]=getCopy(grids);
      String cbtext=String.valueOf(cb.getItemAt(cb.getSelectedIndex()));
      // System.out.println("********************************* "+cbtext+" ****************************************");
      if(cbtext=="bfs")
        pathFinderBFS(startNum);
      else 
        pathFinderDFS(startNum);      

      // SwingUtilities.invokeLater(new Runnable() {
      //    public void run() {
      //       pathFinderBFS(startNum);         }
      // });
    }
    else if(e.getSource()==rand)      // reandom grid
      randomGridGenerator();
    else {                            // re-paint
      redraw=0;
      repaint();
      grids[0][0]=1;
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
    arr_i=(y- y_gap )/height;  //arr_i=(y-40)/40
    arr_j=(x- x_gap1 )/width;    //80/20 4
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
          System.out.print(val+"    ");
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
  
  void pathFinderDFS(int gridNum){
    grids[0][0]=1;
    stack.addLast(gridNum);

    ij = gridNumToArrNum(gridNum);
    arr_i = ij[0];
    arr_j = ij[1];
    adjElements(gridNum,stack);
    isVisited[gridNum]=true;

    while(!found){
      if(!isVisited[gridNum]){

        ij= gridNumToArrNum(gridNum);
        arr_i = ij[0];
        arr_j = ij[1];
        grids[ arr_i ][ arr_j ] = 8;
        
        adjElements(gridNum,stack);
        isVisited[gridNum]=true;
      }
      // System.out.println(gridNum+","+edgeFrom[gridNum]+","+dist[gridNum]+" "+found+" "+grids[ij[0]][ij[1]]);
      // System.out.println("**"+gridNum+","+edgeFrom[gridNum]+","+dist[gridNum]+" "+found+" "+grids[ij[0]][ij[1]]);
      // System.out.println("Head element remove : " + gridNum);//+" "+stack);

      if(gridNum == endNum)
        found=true;
      if(found && edgeFrom[endNum]!=0)
        break;    

      if(stack.isEmpty())
        break;
      gridNum=stack.removeLast();
    }
    // System.out.println("Head element remove : " + gridNum+" "+stack);
    stack.clear();
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
        // System.out.println("path="+shortpath+","+endNum+" "+Arrays.toString(gridNumToArrNum(endNum))+","+startNum+" "+Arrays.toString(gridNumToArrNum(startNum)));
        if(shortpath==0){
          // System.out.println( Arrays.toString(edgeFrom) );//+" "+stack);
          printArr(edgeFrom); 
          break;
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


  void pathFinderBFS(int gridNum){
    // t1 = new Thread(this);
    grids[0][0]=1;
    // System.out.println(gridNum+","+isVisited[gridNum]+" "+found);
    que.add(gridNum);

    ij = gridNumToArrNum(gridNum);
    arr_i = ij[0];
    arr_j = ij[1];
    adjElements(gridNum,que);
    isVisited[gridNum]=true;

    while(!found){// || edgeFrom[endNum]==0){// || edgeFrom[endNum]==0){   
      if(!isVisited[gridNum]){

        ij= gridNumToArrNum(gridNum);
        arr_i = ij[0];
        arr_j = ij[1];
        grids[ arr_i ][ arr_j ] = 8;
        
        adjElements(gridNum,que);
        isVisited[gridNum]=true;
      }
      // System.out.println(gridNum+","+edgeFrom[gridNum]+","+dist[gridNum]+" "+found+" "+grids[ij[0]][ij[1]]);
      // System.out.println("**"+gridNum+","+edgeFrom[gridNum]+","+dist[gridNum]+" "+found+" "+grids[ij[0]][ij[1]]);
      // System.out.println("Head element remove : " + gridNum);//+" "+que);

      // t1.start();
      // redraw=0;
      // repaint();
      // try{  Thread.sleep(s_count);  }
      // catch(Exception e){  System.out.println(e);  }

      if(gridNum == endNum)
        found=true;
      if(found && edgeFrom[endNum]!=0)
        break;    

      if(que.isEmpty())
        break;
      gridNum=que.removeFirst();
    }
    // System.out.println("Head element remove : " + gridNum+" "+que);
    que.clear();
    // System.out.println("out of while");
    if(found){
      // System.out.println("inside found");
      int shortpath=endNum;
      while(edgeFrom[shortpath]!=-1){
        shortpath=edgeFrom[shortpath];
        ij=gridNumToArrNum(shortpath);
        arr_i=ij[0];
        arr_j=ij[1];
        grids[arr_i][arr_j]=7; 
        // // only for dfs
        // if(nearStart()){
        //   edgeFrom[shortpath]=startNum;
        //   break;
        // }
        // System.out.println("path="+shortpath+","+endNum+" "+Arrays.toString(gridNumToArrNum(endNum))+","+startNum+" "+Arrays.toString(gridNumToArrNum(startNum)));
        if(shortpath==0){
          System.out.println("$BFS$");//+" "+que);
          printArr(edgeFrom); 
          break;
        }
      }
      // System.out.println("out of while again");
      repaint();
    }
    ij=gridNumToArrNum(startNum);
    grids[ij[0]][ij[1]]=5;
    ij=gridNumToArrNum(endNum);
    grids[ij[0]][ij[1]]=3;
    redraw=0;
    repaint();
  }

  void adjElements(int gridNum,ArrayDeque que){
    // right
    if( (arr_j+1 < columns-1) && (grids[arr_i][arr_j+1] != 1) && !isVisited[gridNum+1] ){ //checks if (its not a border) and (not a block)          
      if(dist[gridNum+1]==0){
        dist[gridNum+1]=dist[gridNum]+1;
        edgeFrom[gridNum+1]=gridNum;
      }
      else if(dist[gridNum+1]>=dist[gridNum]+1){
        dist[gridNum+1]=dist[gridNum]+1;
        edgeFrom[gridNum+1]=gridNum;
      }      
      que.addLast(gridNum+1);
      // System.out.println("1--"+que);
    }
    // down
    if( (gridNum+columns < max-columns) && (grids[arr_i+1][arr_j] != 1) && !isVisited[gridNum+columns] ){
      if(dist[gridNum+columns]==0){
        dist[gridNum+columns]=dist[gridNum]+1;
        edgeFrom[gridNum+columns]=gridNum;
      }
      else if(dist[gridNum+columns]>=dist[gridNum]+1){
        dist[gridNum+columns]=dist[gridNum]+1;
        edgeFrom[gridNum+columns]=gridNum;
      }
      que.addLast(gridNum+columns);
      // System.out.println("2--"+que);
    }
    // left
    if( (arr_j-1 > 0) && (grids[arr_i][arr_j-1] != 1) && !isVisited[gridNum-1] ){
      if(dist[gridNum-1]==0){
        dist[gridNum-1]=dist[gridNum]+1;
        edgeFrom[gridNum-1]=gridNum;
      }
      else if(dist[gridNum-1]>=dist[gridNum]+1){
        dist[gridNum-1]=dist[gridNum]+1;
        edgeFrom[gridNum-1]=gridNum;
      }      
      que.addLast(gridNum-1);
      // System.out.println("3--"+que);
    }
    // up
    if( (gridNum-columns > columns) && (grids[arr_i-1][arr_j] != 1) && !isVisited[gridNum-columns] ){
      if(dist[gridNum-columns]==0){
        dist[gridNum-columns]=dist[gridNum]+1;
        edgeFrom[gridNum-columns]=gridNum;
      }
      else if(dist[gridNum-columns]>=dist[gridNum]+1){
        dist[gridNum-columns]=dist[gridNum]+1;
        edgeFrom[gridNum-columns]=gridNum;
      }
      que.addLast(gridNum-columns);
      // System.out.println("4--"+que);
    }
  }


  boolean nearStart(){
    if(arrNumToGridNum( arr_i+1 ,arr_j )==startNum)
      return true;
    if(arrNumToGridNum( arr_i-1 ,arr_j )==startNum)
      return true;
    if(arrNumToGridNum( arr_i ,arr_j+1 )==startNum)
      return true;
    if(arrNumToGridNum( arr_i ,arr_j-1 )==startNum)
      return true;
    return false;
  }
  int [][] getCopy(int arr[][]){
    int rows = arr.length; 
    int columns = arr[0].length;
    int a[][]=new int[rows][columns];

    for(int i=0;i<rows;i++)
      for(int j=0;j<columns;j++)
        a[i][j]=arr[i][j];

    return a;
  }

          
  public void paint(Graphics g){
    if(redraw<1){
      for(int i=0;i<rows;i++){
        for(int j=0;j<columns;j++){
          g.drawRect(j*width+x_gap1,i*height+y_gap,width,height);   
          switch(grids[i][j]){
            case 0: g.setColor( Color.white );
            break;
            case 1: g.setColor( Color.black );
            break;
            case 3: g.setColor( Color.red );
            break;
            case 5: g.setColor( Color.green );
            break;
            case 7: g.setColor( Color.yellow );
            break;
            case 8: g.setColor( gray );
            break;
          }
          g.fillRect(j*width+x_gap1,i*height+y_gap,width,height);  
          
          //number
          // g.setColor( Color.black );
          // g.drawString(String.valueOf(arrNumToGridNum(i,j)),j*width+init_gap,i*height+init_gap+height);
        }  
      }
       // draw grid lines
      g.setColor(Color.black);
      for (int k = 0; k <= rows; k++) {
          g.drawLine(0+x_gap1, k * height+y_gap, width*columns+x_gap1, k * height+y_gap);
      }

      for (int k = 0; k <= columns; k++) {
          g.drawLine(k * width+x_gap1, 0+y_gap, k * width+x_gap1, height*rows+y_gap);
      }
      redraw++;
      val=1;
    }
    else{
      g.drawRect(arr_j*width+x_gap1,arr_i*height+y_gap,width,height);
      switch(val){
        case 0: g.setColor( Color.white );
        break;
        case 1: g.setColor( Color.black );
        break;
        case 3: g.setColor( Color.white );
              g.fillRect(startend[2][1]*width+x_gap1,startend[2][0]*height+y_gap,width,height);
              grids[startend[2][0]][startend[2][1]]=0;

              startend[2][0]=arr_i;
              startend[2][1]=arr_j;  

              endNum = arrNumToGridNum(arr_i,arr_j);

              g.setColor( Color.red );
        break;
        case 5: g.setColor( Color.white );
              g.fillRect(startend[1][1]*width+x_gap1,startend[1][0]*height+y_gap,width,height);
              grids[startend[1][0]][startend[1][1]]=0;

              startend[1][0]=arr_i;
              startend[1][1]=arr_j;  

              startNum = arrNumToGridNum(arr_i,arr_j);

              g.setColor( Color.green );
        break;
        case 7: g.setColor( Color.yellow );
        break;
        case 8: g.setColor( gray );
        break;
      }
      g.fillRect(arr_j*width+x_gap1 ,arr_i*height+y_gap ,width,height);
      
      //grid lines
      g.setColor(Color.black);

      int k= arr_i; //top & bottom
      g.drawLine(0+x_gap1, k * height+y_gap, width*columns+x_gap1, k * height+y_gap);
      g.drawLine(0+x_gap1, (k+1) * height+y_gap, width*columns+x_gap1, (k+1) * height+y_gap);

      k=arr_j; // right & left]
      g.drawLine(k * width+x_gap1, 0+y_gap, k * width+x_gap1, height*rows+y_gap);
      g.drawLine((k+1) * width+x_gap1, 0+y_gap, (k+1) * width+x_gap1, height*rows+y_gap);

      // setting 1st block again 
      g.fillRect(0+x_gap1,0+y_gap,width,height);

    }

     // 2nd grid

  //   if(redraw<1){
  //     for(int i=0;i<rows;i++){
  //       for(int j=0;j<columns;j++){
  //         g.drawRect(j*width+x_gap2,i*height+y_gap,width,height);  
  //         switch(grids[i][j]){
  //           case 0: g.setColor( Color.white );
  //           break;
  //           case 1: g.setColor( Color.black );
  //           break;
  //           case 3: g.setColor( Color.red );
  //           break;
  //           case 5: g.setColor( Color.green );
  //           break;
  //           case 7: g.setColor( Color.yellow );
  //           break;
  //           case 8: g.setColor( gray );
  //           break;
  //         }
  //         g.fillRect(j*width+x_gap2,i*height+y_gap,width,height);
          
  //         //number
  //         // g.setColor( Color.black );
  //         // g.drawString(String.valueOf(arrNumToGridNum(i,j)),j*width+init_gap,i*height+init_gap+height);
  //       }  
  //     }   

  //      // draw grid lines
  //     g.setColor(Color.black);
  //     for (int k = 0; k <= rows; k++) {
  //         g.drawLine(0+x_gap2, k * height+y_gap, width*columns+x_gap2, k * height+y_gap);
  //     }

  //     for (int k = 0; k <= columns; k++) {
  //         g.drawLine(k * width+x_gap2, 0+y_gap, k * width+x_gap2, height*rows+y_gap);
  //     }
  //     redraw++;
  //     val=1;
  //   }
  //   else{
  //     g.drawRect(arr_j*width+x_gap2,arr_i*height+y_gap,width,height);
  //     switch(val){
  //       case 0: g.setColor( Color.white );
  //       break;
  //       case 1: g.setColor( Color.black );
  //       break;
  //       case 3: g.setColor( Color.white );
  //             g.fillRect(startend[2][1]*width+x_gap2,startend[2][0]*height+y_gap,width,height);
  //             grids[startend[2][0]][startend[2][1]]=0;

  //             startend[2][0]=arr_i;
  //             startend[2][1]=arr_j;  

  //             endNum = arrNumToGridNum(arr_i,arr_j);

  //             g.setColor( Color.red );
  //       break;
  //       case 5: g.setColor( Color.white );
  //             g.fillRect(startend[1][1]*width+x_gap2,startend[1][0]*height+y_gap,width,height);
  //             grids[startend[1][0]][startend[1][1]]=0;

  //             startend[1][0]=arr_i;
  //             startend[1][1]=arr_j;  

  //             startNum = arrNumToGridNum(arr_i,arr_j);

  //             g.setColor( Color.green );
  //       break;
  //       case 7: g.setColor( Color.yellow );
  //       break;
  //       case 8: g.setColor( gray );
  //       break;
  //     }
  //     g.fillRect(arr_j*width+x_gap2 ,arr_i*height+y_gap ,width,height);

  //     //grid lines
  //     g.setColor(Color.black);

  //     int k= arr_i; //top & bottom
  //     g.drawLine(0+x_gap2, k * height+y_gap, width*columns+x_gap2, k * height+y_gap);
  //     g.drawLine(0+x_gap2, (k+1) * height+y_gap, width*columns+x_gap2, (k+1) * height+y_gap);

  //     k=arr_j; // right & left]
  //     g.drawLine(k * width+x_gap2, 0+y_gap, k * width+x_gap2, height*rows+y_gap);
  //     g.drawLine((k+1) * width+x_gap2, 0+y_gap, (k+1) * width+x_gap2, height*rows+y_gap);

  //     // setting 1st block again 
  //     g.fillRect(0+x_gap2,0+y_gap,width,height);

  //   } 
  }
}



public class MazeThread implements Runnable {
  Thread t;
  MazeThread(){
    t=new Thread(this);
    t.start();
  }
  public void run() {
      new Maze();
    
  }
  public static void main(String args[]){
    new MazeThread();
  }
}
