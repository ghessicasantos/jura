import controller.Controller;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
        Controller app = new Controller();
        app.start();
    }
}