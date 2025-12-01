package use_case.dashboard;

public class DashboardInputData {
    private String username;

    public DashboardInputData(String name){
        this.username = name;
    }

    public String getUsername() {
        return username;
    }
}
