package in.mvcdemo.Model;

import java.util.List;

public class GetTeamWise {
    public List<GetTeamWiseGuest> getGuest() {
        return guest;
    }

    public void setGuest(List<GetTeamWiseGuest> guest) {
        this.guest = guest;
    }

    private List<GetTeamWiseGuest> guest;
    private String status;



    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
