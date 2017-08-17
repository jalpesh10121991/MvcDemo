package in.mvcdemo.Model;

import java.util.List;

public class GetAllEvent {
    public List<GetAllEventGuest> getGuest() {
        return guest;
    }

    public void setGuest(List<GetAllEventGuest> guest) {
        this.guest = guest;
    }

    private List<GetAllEventGuest> guest;
    private String status;



    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
