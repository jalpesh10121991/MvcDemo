package in.mvcdemo.Model;

import java.util.List;

public class GetCity {
    public List<GetCityGuest> getGuest() {
        return guest;
    }

    public void setGuest(List<GetCityGuest> guest) {
        this.guest = guest;
    }

    private List<GetCityGuest> guest;
    private String status;


    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
