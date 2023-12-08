import javax.swing.*;
import java.awt.*;

public class LoggedIN extends JFrame {
    LoggedIN(){
        setLayout(null);

        JLabel loggedIn = new JLabel("Logged in successful.");
        loggedIn.setBounds(150,70,250,50);
        loggedIn.setFont(new Font("Roboto",Font.BOLD,18));
        add(loggedIn);

        setTitle("Login Successful.");
        setSize(500,500);
        setLocation(370,50);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new LoggedIN();
    }
}
