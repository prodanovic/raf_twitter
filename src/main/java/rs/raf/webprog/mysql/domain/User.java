package rs.raf.webprog.mysql.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

/**
 * Created by srdjanp@ballab.com on 2/6/2015.
 */
@Entity
@Table(name = "user")
public class User implements java.io.Serializable  {
    private static final long serialVersionUID = -4863040932014032728L;

    @Id
    private String id;
//    @Range(min = 0, max = 1)
    @Column
    private Integer purchaseRequestCount ;


    @Column//(name = "firstPurchaseRequestDate")
    private Date firstPurchaseRequestDate;



    public User() { }

    public User(String id, Integer purchaseRequestCount, Date firstPurchaseRequestDate) {
        super();
        this.id = id;
        this.purchaseRequestCount = purchaseRequestCount;
        this.firstPurchaseRequestDate = firstPurchaseRequestDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPurchaseRequestCount() {
        return purchaseRequestCount;
    }

    public void setPurchaseRequestCount(Integer purchaseRequestCount) {
        this.purchaseRequestCount = purchaseRequestCount;
    }

    public Date getFirstPurchaseRequestDate() {
        return firstPurchaseRequestDate;
    }

    public void setFirstPurchaseRequestDate(Date firstPurchaseRequestDate) {
        this.firstPurchaseRequestDate = firstPurchaseRequestDate;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", purchaseRequestCount=" + purchaseRequestCount +
                ", firstPurchaseRequestDate="+firstPurchaseRequestDate+"]";
    }
}
