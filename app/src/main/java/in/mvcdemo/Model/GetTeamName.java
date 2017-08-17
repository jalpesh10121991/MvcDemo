package in.mvcdemo.Model;

import java.util.List;

public class GetTeamName {
    private List<GetTeamNameGuest> guest;
    private String status;

    public List<GetTeamNameGuest> getGuest() {
        return guest;
    }

    public void setGuest(List<GetTeamNameGuest> guest) {
        this.guest = guest;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
