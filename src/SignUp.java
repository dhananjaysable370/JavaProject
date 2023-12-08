import com.mysql.cj.jdbc.Driver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class SignUp extends JFrame implements ActionListener {
    private static final String url = "jdbc:mysql://localhost:3306/userauthentication";
    private static final String userName ="root";
    private static final String passwd = "password";
    String confirmPassword;
    JTextField userNameField;
    JPasswordField createPasswordField,confirmPasswordField;
    JButton login,clear,signUp;

    JCheckBox showPasswordCheckbox,confirmPasswordCheckbox;
    public SignUp(){
        setLayout(null);
        JLabel title = new JLabel("USER REGISTRATION");
        title.setBounds(250,30,300,40);
        title.setFont(new Font("Roboto",Font.BOLD,22));
        title.setForeground(Color.BLACK);
        add(title);

        JLabel username = new JLabel("Create Username");
        username.setBounds(100,140,150,30);
        username.setFont(new Font("Roboto",Font.BOLD,14));
        username.setForeground(Color.BLACK);
        add(username);

        userNameField = new PlaceholderTextField("Enter your username");
        userNameField.setBounds(250,140,300,30);
        add(userNameField);

        JLabel createPassword = new JLabel("Create Password");
        createPassword.setBounds(100,220,150,30);
        createPassword.setFont(new Font("Roboto",Font.BOLD,14));
        createPassword.setForeground(Color.BLACK);
        add(createPassword);

        createPasswordField = new JPasswordField();
        createPasswordField.setBounds(250,220,300,30);
        add(createPasswordField);

        showPasswordCheckbox = new JCheckBox("");
        showPasswordCheckbox.setBounds(550,215,150,40);
        showPasswordCheckbox.addActionListener(this);
        add(showPasswordCheckbox);

        JLabel confirmPassword = new JLabel("Confirm Password");
        confirmPassword.setBounds(100,300,150,30);
        confirmPassword.setFont(new Font("Roboto",Font.BOLD,14));
        confirmPassword.setForeground(Color.BLACK);
        add(confirmPassword);

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(250,300,300,30);
        add(confirmPasswordField);

        confirmPasswordCheckbox = new JCheckBox("");
        confirmPasswordCheckbox.setBounds(550,295,150,40);
        confirmPasswordCheckbox.addActionListener(this);
        add(confirmPasswordCheckbox);

        login = new JButton("Login");
        login.setBounds(240,390,90,40);
        login.setBackground(Color.WHITE);
        login.setForeground(Color.BLACK);
        login.addActionListener(this);
        add(login);

        signUp = new JButton("Sign Up");
        signUp.setBounds(375,390,90,40);
        signUp.setBackground(Color.WHITE);
        signUp.setForeground(Color.BLACK);
        signUp.addActionListener(this);
        add(signUp);

        clear = new JButton("CLEAR");
        clear.setBounds(240,450,224,40);
        clear.setBackground(Color.WHITE);
        clear.setForeground(Color.BLACK);
        clear.addActionListener(this);
        add(clear);


        setTitle("Registration Form");
        setLocation(350,50);
        setSize(700,600);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == clear){
            userNameField.setText("");
            createPasswordField.setText("");
            confirmPasswordField.setText("");
        }else if(ae.getSource() == signUp){
            confirmPassword = createPasswordField.getText();
            if(confirmPassword.equals(confirmPasswordField.getText())){
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                }catch (Exception e){
                    e.printStackTrace();
                }
                try {
                    String usr = userNameField.getText();
                    Connection con = DriverManager.getConnection(url,userName,passwd);
                    String query = "insert into register(username,password) values(?,?);";
                    PreparedStatement ps = con.prepareStatement(query);
                    ps.setString(1,usr);
                    ps.setString(2,confirmPassword);
                    int rowsAffected = ps.executeUpdate();
                    if(rowsAffected>0){
                        JOptionPane.showMessageDialog(null,"Registered Successfully.");
                        con.close();
                        ps.close();
                        setVisible(false);
                        new Login();
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"Registration Unsuccessful!");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else{
                JOptionPane.showMessageDialog(null,"Password Mismatch!");
            }
        }else if(ae.getSource() == login){
            setVisible(false);
            new Login();
        }
        else if(ae.getSource() == showPasswordCheckbox || ae.getSource() == confirmPasswordCheckbox){
            if(showPasswordCheckbox.isSelected()){
                createPasswordField.setEchoChar((char) 0);
            }else{
                createPasswordField.setEchoChar('*');
            }
            if(confirmPasswordCheckbox.isSelected()){
                confirmPasswordField.setEchoChar((char) 0); //show password
            }else{
                confirmPasswordField.setEchoChar('*'); //Hide password
            }
        }
    }

    public static void main(String[] args) {
        new SignUp();
    }
}

