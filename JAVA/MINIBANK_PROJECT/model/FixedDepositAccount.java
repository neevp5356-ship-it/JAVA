package model;

public class FixedDepositAccount extends Account {
    private static final long serialVersionUID = 1L;
    private boolean matured;

    public FixedDepositAccount(String ownerName, long balance, boolean matured) {
        super(ownerName, balance);
        this.matured = matured;
    }

    @Override
    public double interestRate() { return 7.0; }

    @Override
    public boolean canWithdraw(long amount) {
        return matured && balance >= amount;
    }

    public void setMatured(boolean matured) { this.matured = matured; }

    @Override
    public String toString() {
        return "FD[" + super.toString() + ", Matured: " + matured + "]";
    }
}
