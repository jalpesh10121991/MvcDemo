package in.mvcdemo.Model;

import java.util.List;

public class AllGuest {
    private List<AllGuestGuest> guest;
    private String status;

    public List<AllGuestGuest> getGuest() {
        return guest;
    }

    public void setGuest(List<AllGuestGuest> guest) {
        this.guest = guest;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
