package bgu.spl.net.srv;

public abstract class User {
    protected String userName;
    protected String password;
    protected boolean isActive;
    protected boolean isAdmin;
    protected Database database=Database.getInstance();
    public User(String userName,String password){
        this.userName=userName;
        this.password=password;
        this.isActive=false;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
    public boolean getIsActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }
}

