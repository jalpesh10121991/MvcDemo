package in.mvcdemo.Model;

import java.util.List;

public class GetPlayerProfile {
    private List<GetPlayerProfileGuest> guest;
    private String status;


    public List<GetPlayerProfileGuest> getGuest() {
        return guest;
    }

    public void setGuest(List<GetPlayerProfileGuest> guest) {
        this.guest = guest;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
