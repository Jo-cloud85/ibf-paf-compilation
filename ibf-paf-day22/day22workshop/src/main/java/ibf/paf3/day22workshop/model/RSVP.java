package ibf.paf3.day22workshop.model;

import java.util.Date;

public class RSVP {
    private int id;
    private String fullName;
    private String email;
    private String phone;
    private Date confirmationDate;
    private String comment;

    public RSVP() {
    }

    public RSVP(int id, String fullName, String email, String phone, Date confirmationDate, String comment) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.confirmationDate = confirmationDate;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getConfirmationDate() {
        return confirmationDate;
    }

    public void setConfirmationDate(Date confirmationDate) {
        this.confirmationDate = confirmationDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    
}
