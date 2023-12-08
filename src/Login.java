import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

class PlaceholderTextField extends JTextField {
    private String placeholder;

    public PlaceholderTextField(String placeholder) {
        this.placeholder = placeholder;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw placeholder text if the field is empty
        if (getText().isEmpty()) {
            g.setColor(Color.GRAY);
            g.drawString(placeholder, getInsets().left, g.getFontMetrics().getMaxAscent() + getInsets().top);
        }
    }
}

public class Login extends JFrame implements ActionListener {
    private static final String url = "jdbc:mysql://localhost:3306/userauthentication";
    private static final String userName ="root";
    private static final String passwd = "password";
    JTextField usernameField;
    JPasswordField passwordField;
    JButton clear,login,signUp;
    JLabel loginFailed;
    JCheckBox showPasswordCheckbox;
    Login(){
        setLayout(null);
        JLabel title = new JLabel("LOGIN");
        title.setBounds(220,10,200,90);
        title.setFont(new Font("Arial",Font.BOLD,18));
        add(title);

        JLabel userNameLabel = new JLabel("Username");
        userNameLabel.setBounds(100,90,100,40);
        add(userNameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(100,130,300,40);
        usernameField.setFont(new Font("Roboto",Font.BOLD,16));
        add(usernameField);


        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(100,180,100,40);
        add(passwordLabel);

        passwordField = new JPasswordField(20);
        passwordField.setBounds(100,220,300,40);
        passwordField.setFont(new Font("Roboto",Font.BOLD,16));
        add(passwordField);

        showPasswordCheckbox = new JCheckBox("");
        showPasswordCheckbox.setBounds(400,220,150,40);
        showPasswordCheckbox.addActionListener(this);
        add(showPasswordCheckbox);

        // Buttons
        login = new JButton("Login");
        login.setBounds(140,350,90,40);
        login.setBackground(Color.WHITE);
        login.setForeground(Color.BLACK);
        login.addActionListener(this);
        add(login);

        signUp = new JButton("Sign Up");
        signUp.setBounds(245,350,90,40);
        signUp.setBackground(Color.WHITE);
        signUp.setForeground(Color.BLACK);
        signUp.addActionListener(this);
        add(signUp);

        clear = new JButton("CLEAR");
        clear.setBounds(140,400,195,40);
        clear.setBackground(Color.WHITE);
        clear.setForeground(Color.BLACK);
        clear.addActionListener(this);
        add(clear);

        loginFailed = new JLabel("Invalid username and password!");
        loginFailed.setBounds(138,280,250,40);
        loginFailed.setFont(new Font("Roboto",Font.BOLD,14));
        loginFailed.setForeground(Color.red);
        loginFailed.setVisible(false);
        add(loginFailed);

        setTitle("Login");
        setSize(500,600);
        setLocationRelativeTo(null);
        setLocation(370,50);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    public void actionPerformed(ActionEvent e){

        if(e.getSource() == clear){
            usernameField.setText("");
            passwordField.setText("");
        }
        else if(e.getSource() == login){
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            }catch (Exception exe){
                exe.printStackTrace();
            }
            try {
                Connection con = DriverManager.getConnection(url,userName,passwd);
                String name = usernameField.getText();
                String pass = passwordField.getText();

                String query = "select * from register where username = '"+name+"' and password = '"+pass+"';";

                PreparedStatement ps = con.prepareStatement(query);
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    setVisible(false);
                    con.close();
                    ps.close();
                    rs.close();
                    new LoggedIN();
                }
                else{
                    loginFailed.setVisible(true);
                }
            }catch (Exception exe){
                exe.printStackTrace();
            }

        }
        else if(e.getSource() == signUp){
            setVisible(false);
            new SignUp();
        }
        else {
            if(showPasswordCheckbox.isSelected()){
                passwordField.setEchoChar((char)0);
            }
            else {
                passwordField.setEchoChar('*');
            }
        }
    }
    public static void main(String[] args) {
        new Login();
    }
}
