public class Order {
    private int orderId;
    private String customerName;
    private double total;
    private String status;

    public Order(int orderId, String customerName, double total, String status) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.total = total;
        this.status = status;
    }

    public Order() {
        this.orderId = 0;
        this.customerName = "Unknown";
        this.total = 0.0;
        this.status = "Pending";
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void complete() {
        this.status = "Completed";
    }

    public void cancel() {
        this.status = "Cancelled";
    }

    @Override
    public String toString() {
        return "Order{orderId=" + orderId + ", customerName='" + customerName + "', total=" + total + ", status='" + status + "'}";
    }
}