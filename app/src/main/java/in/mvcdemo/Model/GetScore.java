package in.mvcdemo.Model;

import java.util.List;

public class GetScore {
    private List<GetScoreGuest> guest;
    private String status;

    public List<GetScoreGuest> getGuest() {
        return guest;
    }

    public void setGuest(List<GetScoreGuest> guest) {
        this.guest = guest;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
