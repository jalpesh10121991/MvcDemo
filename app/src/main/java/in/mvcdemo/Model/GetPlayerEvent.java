package in.mvcdemo.Model;

import java.util.List;

public class GetPlayerEvent {
    public List<GetPlayerEventGuest> getGuest() {
        return guest;
    }

    public void setGuest(List<GetPlayerEventGuest> guest) {
        this.guest = guest;
    }

    private List<GetPlayerEventGuest> guest;
    private String status;


    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
