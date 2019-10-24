package DataStructure;

import Building.GenericBank;

/**
 * DataStructure
 *
 * @author Jan Frantisek Sedlacek
 * @version 1.0
 * @since 22.10.19
 */
public class Order {
private double time=0;
private int moneyAmount;
private GenericBank sender;

    public Order(int moneyAmount, GenericBank sender) {
        this.moneyAmount = moneyAmount;
        this.sender = sender;
    }

    public int getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(int moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public GenericBank getSender() {
        return sender;
    }

    public void setSender(GenericBank sender) {
        this.sender = sender;
    }


}
