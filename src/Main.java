package src;

import src.menu.MainMenu;   // MainMenu class in src.menu package

import java.util.Scanner;   // Scanner class from java.util library

public class Main {
    // instantiate global (static) Scanner object
    public static Scanner sc = new Scanner(System.in);
    // instantiate a MainMenu object
    public static MainMenu mainMenuObject = new MainMenu();

    public static void main(String[] args) {
        // invoke display method of MainMenu (the front menu of app)
        mainMenuObject.display();
    }
}
