public class ParkingLot {

    private int twoWheelers;
    private int fourWheelers;
    private final int twoCap;
    private final int fourCap;
    private static long revenue = 0;

    public ParkingLot(int twoCap, int fourCap) {
        this.twoCap = twoCap;
        this.fourCap = fourCap;
    }

    public boolean park(String type) {
        if ("two".equals(type)) {
            if (twoWheelers < twoCap) {
                twoWheelers++;
                revenue += 20;
                return true;
            }
            System.out.println("Full");
            return false;
        } else if ("four".equals(type)) {
            if (fourWheelers < fourCap) {
                fourWheelers++;
                revenue += 40;
                return true;
            }
            System.out.println("Full");
            return false;
        }
        return false;
    }

    public void leave(String type) {
        if ("two".equals(type)) {
            twoWheelers = Math.max(0, twoWheelers - 1);
        } else if ("four".equals(type)) {
            fourWheelers = Math.max(0, fourWheelers - 1);
        }
    }

    public int getTwoWheelers() {
        return twoWheelers;
    }

    public int getFourWheelers() {
        return fourWheelers;
    }

    public static long getRevenue() {
        return revenue;
    }

    public static void main(String[] args) {
        ParkingLot lot = new ParkingLot(2, 2);

        System.out.println(lot.park("two"));
        System.out.println(lot.park("four"));
        System.out.println(lot.park("two"));
        System.out.println(lot.park("four"));
        System.out.println(lot.park("two"));   // Full — two-wheeler section full

        lot.leave("two");
        System.out.println(lot.park("two"));

        lot.leave("four");
        lot.leave("four");
        lot.leave("four");                     // already 0, stays at 0

        System.out.println(lot.park("four"));
        System.out.println(lot.park("four"));
        System.out.println(lot.park("four"));  // Full — four-wheeler section full

        System.out.println("Two-wheelers parked: " + lot.getTwoWheelers());
        System.out.println("Four-wheelers parked: " + lot.getFourWheelers());
        System.out.println("Revenue: " + ParkingLot.getRevenue());
    }
}
