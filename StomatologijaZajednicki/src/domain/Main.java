/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

/**
 *
 * @author Asus
 */
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class Main {

    public static void main(String[] args) {

        try {

            Student s =
                    new Student("Marko", "Markovic", 9.25);

            ObjectOutputStream out =
                    new ObjectOutputStream(
                            new FileOutputStream("student.dat"));

            out.writeObject(s);
            out.close();

            System.out.println("Objekat je uspesno sacuvan.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
