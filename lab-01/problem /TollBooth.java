import java.util.*;

public class TollBooth {
    record Vehicle(String number, String type){};
    public static void main(String[] args){
        int total=0;
        int bike=0;
        int car=0;
        int truck=0;
        Scanner s=new Scanner(System.in);
        while(true){
            System.out.print("Enter type:");
            String type=s.nextLine().toLowerCase();
            
            if(type.equalsIgnoreCase("done")){
                break;
            }
            System.out.println("Enter vehical number:");
            String no=s.nextLine();
            Vehicle v=new Vehicle(no, type);
            int toll=0;
            
            switch (type) {
                case "car":
                    toll=50;
                    car++;
                    break;
                case "bike":
                    toll=20;
                    bike++;
                    break;
                case "truck":
                    toll=150;
                    truck++;
                    break;
                default:
                    break;
            }
            total+=toll;
            System.out.println("Total toll:"+total);
            

        }
        String m=(bike>=car & bike>=truck)?"bike":(car>=truck)?"car":"truck";
            System.out.println("Most type with highest count:"+m);
       



    }
}

 
