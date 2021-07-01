
package transactionhistory;


public class History {
    public String name;
    public String account;
    public String amount;
    public String genric;
    public String date;
    public String time;

    public History(String name,String account, String amount, String genric, String date, String time) {
        this.account = account;
        this.name = name;
        this.amount = amount;
        this.genric = genric;
        this.date = date;
        this.time = time;
    }

    public void setAccount(String account) {
        this.account = account;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setGenric(String genric) {
        this.genric = genric;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAccount() {
        return account;
    }
    
    public String getName() {
        return name;
    }

    public String getAmount() {
        return amount;
    }

    public String getGenric() {
        return genric;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
    
}
