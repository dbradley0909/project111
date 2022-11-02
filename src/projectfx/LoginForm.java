package projectfx;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

//import java.awt.Dimension;
import javax.swing.*;




public class LoginForm  extends JFrame{
    final private Font mainFont = new Font("Segoe print", Font.BOLD, 18);
    JTextField tfEmail;
    JPasswordField pfPassword;
    public void initialize(){
        JLabel lbLoginForm = new JLabel("Register", SwingConstants.CENTER);
        lbLoginForm.setFont(mainFont);

        JLabel lbEmail = new JLabel("Email");
        lbEmail.setFont(mainFont);
        tfEmail = new JTextField();
        tfEmail.setFont(mainFont);

        JLabel lbPassword = new JLabel("Password");
        lbPassword.setFont(mainFont);
        pfPassword = new JPasswordField();
        pfPassword.setFont(mainFont);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(0, 1, 10,10));
        formPanel.add(lbLoginForm); 
        formPanel.add(lbEmail);  
        formPanel.add(tfEmail);  
        formPanel.add(lbPassword);   
        formPanel.add(pfPassword);  
        //BUTTONS
        JButton btnLogin = new JButton("Enter");
        btnLogin.setFont(mainFont);
        btnLogin.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String email = tfEmail.getText();
                String password = String.valueOf(pfPassword.getPassword());

                User user = getAutheticatedUser(email, password);

                if(user != null){
                    MainFrame mainFrame = new MainFrame();
                    mainFrame.initialize(user);
                    dispose();
                }else{
                    JOptionPane.showMessageDialog(LoginForm.this, " Email or password invalid", "Please try again", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        JButton btnCancel = new JButton("Cancel");
        btnCancel .setFont(mainFont);
        btnCancel.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
               
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1,2,10,0));
                buttonPanel.add(btnLogin);
                buttonPanel.add(btnCancel);
    }
        });


       //frame 
        add(formPanel, BorderLayout.NORTH);
        setTitle("Login");
        //setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(475, 575);
        setMinimumSize(new Dimension(375,475));
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private User getAutheticatedUser(String email, String password){
        User user = null;
        final String DB_URL = "jdbc:mysql://localhost:3306/";
        final String USERNAME = "root";
        final String PASSWORD = "";// PASSWORD YOU USED FOR SQL
        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "SELECT * FROM user Where email=? AND password=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, sql);
            preparedStatement.setString(2, password);

            ResultSet reultSet = preparedStatement.executeQuery();
            
            if(reultSet.next()){
                user = new User();
                user.name = reultSet.getString("name");
                user.email = reultSet.getString("email");
                user.phone = reultSet.getString("phone");
                user.address = reultSet.getString("address");
                user.password = reultSet.getString("password");
            }
            preparedStatement.close();
            conn.close();
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Database is not connected");
        }
        return user;
    }
        public static void main(String[] args) throws Exception{
            LoginForm loginForm = new LoginForm();
            loginForm.initialize();
        }

}
