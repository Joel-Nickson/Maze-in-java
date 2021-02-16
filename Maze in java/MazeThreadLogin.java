import java.sql.*;
import java.awt.*; 
import java.awt.event.*;
import javax.swing.*;

public class MazeThreadLogin implements ActionListener{

	String insert="insert into logintable(name,username,password) ";//(32,'cini',60)";
	String update="update logintable ";//set
	String select="select * from logintable ";
	String set="";
	String del="";
	String n,n_table ,u,u_table ,p,p_table;
	boolean found;

	JFrame f,f_reg;
	JButton login ,register ,regist ,signup ,supbutton ,retry;
	JTextField name ,user;
	JPasswordField pass;
	JLabel login_header ,username,password,realname;
	JPanel s_up ,s_uppage ,s_upbutton ,s_in ,s_inpage ,s_inbutton ,s_login ,s_loginbutton ,s_frame;
	ResultSet rs;


	MazeThreadLogin(){	

		f = new JFrame("Path Finding Visualizer");
		user = new JTextField(13);		
		pass = new JPasswordField(13);
        login = new JButton("Login");
        register = new JButton("Register");
        username = new JLabel("Username : ");
        password = new JLabel("Password : ");

        login_header = new JLabel("PATH FINDER", SwingConstants.CENTER);
        login_header.setFont(new Font("San-Serif", Font.PLAIN, 50));
        login_header.setForeground(Color.white);
        //f.getContentPane().setBackground(Color.CYAN);

        username.setForeground(Color.white);
        password.setForeground(Color.white);

        login_header.setBounds(210, 5, 550, 200);
        username.setBounds(300, 240, 100, 40);
        user.setBounds(400, 240, 250, 40);
        password.setBounds(300, 310, 100, 40);
        pass.setBounds(400, 310, 250, 40);
        login.setBounds(300, 390, 100, 40);
        register.setBounds(550, 390, 100, 40);

        login.addActionListener(this);
        register.addActionListener(this);

        f.setLayout(null);  
        f.setContentPane(new JLabel(new ImageIcon("maze.jpg")));

        f.add(login_header);
        f.add(login); 
        f.add(register);
        f.add(username);
        f.add(user);
        f.add(password);
        f.add(pass);

        f.setSize(1000, 600);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

	public void actionPerformed(ActionEvent e){

		if(e.getSource()==login){			
			u= new String(user.getText()).trim();
			p= new String(pass.getPassword()).trim();
			jdbcQuery(select+" where username='"+u+"';");
			
				// 	System.out.println("login success "+u+" "+p);
			if(found){
			    JFrame f=new JFrame();  
			    JOptionPane.showMessageDialog(f,"Successfully Logged-in"); 
			    this.f.dispose();
				new MazeThread();
			}
			else{
				System.out.println("sorry..."+u+" "+p);
			    JFrame f=new JFrame();  
			    JOptionPane.showMessageDialog(f,"Wrong Username or Password"); 
			} 
		}
		else if(e.getSource()==regist){

			n= new String(name.getText()).trim();
			u= new String(user.getText()).trim();
			p= new String(pass.getPassword()).trim();
			jdbcUpdate(insert+ new String(" values('"+n+"','"+u+"','"+p+"');")); 

		    JFrame f=new JFrame();  
		    JOptionPane.showMessageDialog(f,"Registration Successful"); 

			System.out.println("regist success"+n+" "+u+" "+p);
			f_reg.dispose();
		}
		else{
			f_reg = new JFrame("Registration page");
			name = new JTextField(13);
			user = new JTextField(13);		
			pass = new JPasswordField(13);
	        realname = new JLabel("Name : ");
	        username = new JLabel("Username : ");
	        password = new JLabel("Password : ");
	        regist = new JButton("Register");

	        login_header = new JLabel("PATH FINDER", SwingConstants.CENTER);
	        f_reg.setContentPane(new JLabel(new ImageIcon("maze2.jpg")));
	        login_header.setFont(new Font("San-Serif", Font.PLAIN, 50));
	        login_header.setForeground(Color.white);
	        //f.getContentPane().setBackground(Color.CYAN);

	        realname.setForeground(Color.white);
	        username.setForeground(Color.white);
	        password.setForeground(Color.white);

	        login_header.setBounds(210, 5, 550, 200);
	        realname.setBounds(300, 250, 100, 30);
	        name.setBounds(400, 250, 250, 30);
	        username.setBounds(300, 300, 100, 30);
	        user.setBounds(400, 300, 250, 30);
	        password.setBounds(300, 350, 100, 30);
	        pass.setBounds(400, 350, 250, 30);	        
        	regist.setBounds(550, 390, 100, 40);

        	regist.addActionListener(this);

	        f_reg.add(login_header);
	        f_reg.add(realname);
	        f_reg.add(name);
	        f_reg.add(username);
	        f_reg.add(user);
	        f_reg.add(password);
	        f_reg.add(pass);
	        f_reg.add(regist);

	        f_reg.setSize(1000, 600);
	        f_reg.setLocationRelativeTo(null);
	        f_reg.setLayout(null);
	        f_reg.setVisible(true);
		}


	}
	void jdbcQuery(String query){
		try {
			found=false;
            Class.forName("org.postgresql.Driver");
            Connection c = DriverManager.getConnection("jdbc:postgresql://satao.db.elephantsql.com:5432/mkdqeluv" , "mkdqeluv", "5KnjTGDyXPpyliFd8UExf1I2BR2p1M3Y");

            Statement st = c.createStatement();
            rs = st.executeQuery(query);
            while(rs.next())
            {
                n_table = new String(rs.getString("name")).trim();
                u_table = new String(rs.getString("username")).trim();
	            p_table = new String(rs.getString("password")).trim();
	            found =checkUser();
	            if(found){
		            break;
	            }
            }
            c.close();
        }
         catch (Exception e) {
           System.err.println(e.getMessage());
        }
	}	

	void jdbcUpdate(String query){
		try {
            Class.forName("org.postgresql.Driver");
            Connection c = DriverManager.getConnection("jdbc:postgresql://satao.db.elephantsql.com:5432/mkdqeluv" , "mkdqeluv", "5KnjTGDyXPpyliFd8UExf1I2BR2p1M3Y");

            Statement st = c.createStatement();
            st.executeUpdate(query);
            c.close();
        }
         catch (Exception e) {
           System.err.println(e.getMessage());
        }

	}	

	boolean checkUser(){
		int c=0;
		if(!(u_table.equals(u))){
			c++;
		    JOptionPane.showMessageDialog(null,"Username is Incorrectt"); 
		}
		else if(!(p_table.equals(p))){
			c++;
		    JOptionPane.showMessageDialog(null,"Username is Incorrectt"); 
		}
		if(c==0)
			return true;
		else
			return false;
	}

	public static void main(String args[]){
		new MazeThreadLogin();
	}
}