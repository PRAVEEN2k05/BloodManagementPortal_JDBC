import java.sql.*;
import java.util.Scanner;

class BloodStock {

    public static void ShowDetails() throws Exception {
        String url = "jdbc:mysql://localhost:3306/blood_donation";
        String user = "root";
        String password = "root";

        Connection con = DriverManager.getConnection(url, user, password);
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM blood_stock");

        System.out.println("\nBlood_Type\tUnits_Available");
        while (rs.next()) {
            System.out.println(
                rs.getString("Blood_Type") + "\t\t" +
                rs.getInt("Units_Available")
            );
        }
        con.close();
    }

    public static void ShowDonor() throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Donor ID: ");
        String id = sc.nextLine();

        String url = "jdbc:mysql://localhost:3306/blood_donation";
        String user = "root";
        String password = "root";

        Connection con = DriverManager.getConnection(url, user, password);
        PreparedStatement ps =
            con.prepareStatement("SELECT * FROM donor WHERE D_ID = ?");
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();

        if (!rs.next()) {
            System.out.println("No donor found with ID: " + id);
        } else {
            System.out.println("D_ID\tName\tBlood\tUnit\tContact");
            do {
                System.out.println(
                    rs.getString("D_ID") + "\t" +
                    rs.getString("Donor_Name") + "\t" +
                    rs.getString("Blood_Type") + "\t" +
                    rs.getFloat("Unit") + "\t" +
                    rs.getString("Contact")
                );
            } while (rs.next());
        }
        con.close();
    }

    public static void UpdateDetails(String bloodType, float units) throws Exception {
        String url = "jdbc:mysql://localhost:3306/blood_donation";
        String user = "root";
        String password = "root";

        Connection con = DriverManager.getConnection(url, user, password);
        PreparedStatement ps =
            con.prepareStatement(
                "UPDATE blood_stock SET Units_Available = Units_Available + ? WHERE Blood_Type = ?"
            );
        ps.setFloat(1, units);
        ps.setString(2, bloodType);

        ps.executeUpdate();
        con.close();
    }
}
// *****************MAIN CLASS****************
public class BloodMain {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws Exception {

        char choice;
        System.out.println("Welcome to Blood Bank Donation System");

        do {
            System.out.println("\n1. Add New Donor");
            System.out.println("2. Get Blood");
            System.out.println("3. Delete Donor");
            System.out.println("4. Show Particular Donor");
            System.out.println("5. Show All Data");
            System.out.println("6. Exit");
            System.out.print("Choose option: ");

            int op = sc.nextInt();
            sc.nextLine(); 

            switch (op) {
                case 1:
                    InsertDetails();
                    break;
                case 2:
                    getBlood();
                    break;
                case 3:
                    DeleteDetails();
                    break;
                case 4:
                    BloodStock.ShowDonor();
                    break;
                case 5:
                    ShowDetails();
                    BloodStock.ShowDetails();
                    break;
                case 6:
                    System.out.println("Thank you!");
                    return;
                default:
                    System.out.println("Invalid option!");
            }

            System.out.print("\nContinue? (Y/N): ");
            choice = sc.next().charAt(0);
            sc.nextLine();

        } while (choice == 'Y' || choice == 'y');
    }

    public static void ShowDetails() throws Exception {
        String url = "jdbc:mysql://localhost:3306/blood_donation";
        String user = "root";
        String password = "root";

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(url, user, password);
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM donor");

        System.out.println("\nID\tName\tBlood\tUnit\tContact");
        while (rs.next()) {
            System.out.println(
                rs.getString("D_ID") + "\t" +
                rs.getString("Donor_Name") + "\t" +
                rs.getString("Blood_Type") + "\t" +
                rs.getFloat("Unit") + "\t" +
                rs.getString("Contact")
            );
        }
        con.close();
    }

    public static void InsertDetails() throws Exception {
        System.out.print("Donor ID: ");
        String id = sc.nextLine();

        System.out.print("Name: ");
        String name = sc.nextLine();

        System.out.print("Blood Type: ");
        String blood = sc.nextLine();

        System.out.print("Units: ");
        float unit = sc.nextFloat();
        sc.nextLine();

        System.out.print("Contact: ");
        String contact = sc.nextLine();

        BloodStock.UpdateDetails(blood, unit);

        String url = "jdbc:mysql://localhost:3306/blood_donation";
        String user = "root";
        String password = "root";

        Connection con = DriverManager.getConnection(url, user, password);
        PreparedStatement ps =
            con.prepareStatement("INSERT INTO donor VALUES (?, ?, ?, ?, ?)");

        ps.setString(1, id);
        ps.setString(2, name);
        ps.setString(3, blood);
        ps.setFloat(4, unit);
        ps.setString(5, contact);

        ps.executeUpdate();
        con.close();

        System.out.println("Donor added successfully!");
    }

    public static void getBlood() throws Exception {
        System.out.print("Blood Type: ");
        String blood = sc.nextLine();

        System.out.print("Units Needed: ");
        float units = sc.nextFloat();
        sc.nextLine();

        String url = "jdbc:mysql://localhost:3306/blood_donation";
        String user = "root";
        String password = "root";

        Connection con = DriverManager.getConnection(url, user, password);
        PreparedStatement ps =
            con.prepareStatement(
                "UPDATE blood_stock SET Units_Available = Units_Available - ? WHERE Blood_Type = ?"
            );
        ps.setFloat(1, units);
        ps.setString(2, blood);

        ps.executeUpdate();
        con.close();

        System.out.println("Blood issued successfully!");
    }

    public static void DeleteDetails() throws Exception {
        System.out.print("Enter Donor ID to delete: ");
        String id = sc.nextLine();

        String url = "jdbc:mysql://localhost:3306/blood_donation";
        String user = "root";
        String password = "root";

        Connection con = DriverManager.getConnection(url, user, password);
        PreparedStatement ps =
            con.prepareStatement("DELETE FROM donor WHERE D_ID = ?");
        ps.setString(1, id);

        ps.executeUpdate();
        con.close();

        System.out.println("Donor deleted successfully!");
    }
}
