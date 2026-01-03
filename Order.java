public class Order {
    private int orderId;
    private String customerName;
    private double total;
    private String status;

    public Order(int orderId, String customerName, double total, String status) {
        setOrderId(orderId);
        setCustomerName(customerName);
        setTotal(total);
        setStatus(status);
    }

    public Order() {
        this.orderId = 0;
        this.customerName = "Unknown";
        this.total = 0.0;
        this.status = "Pending";
    }

        //GETTERS
    public int getOrderId() {return orderId;}
    public String getCustomerName() {return customerName;}
    public double getTotal() {return total;}
    public String getStatus() {return status;}
    public String getFormattedTotal() {return String.format("%.2f KZT", total);}
    public void complete() {this.status = "Completed";}
    public void cancel() {this.status = "Cancelled";}


    //SETTERS
    public void setOrderId(int orderId){
        if (orderId >=1) {this.orderId = orderId;}
        else {System.out.println("WARNING: Incorrect order ID!");}
    }
    public void setCustomerName(String customerName) {
        if (customerName != null && !customerName.trim().isEmpty()) {this.customerName = customerName;}
        else {System.out.println("ALERT: customer's name is empty!");}
    }
    public void setTotal(double total) {
        if (total >= 0){this.total = total;}
        else {System.out.println("WARNING: total cannot be negative!");}
    }
    public void setStatus(String status) {
        if (status != null && !status.trim().isEmpty()){this.status = status;}
        else {System.out.println("WARNING: status cannot be empty!");}
    }


    @Override
    public String toString() {
        return "Order{orderId=" + orderId + ", customerName='" + customerName + "', total=" + total + ", status='" + status + "'}";
    }
}