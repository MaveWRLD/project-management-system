import models.Project;
import models.services.ProjectService;

public class Main {
    static void main(String[] args) {
        ProjectService.createSoftwareProject("P001", "Jakes Project",  "Testing Out", 2000, 3);
        ProjectService.createHardwareProject("P002", "Jakes Project",  "Testing Out", 2000, 3);
        ProjectService.filterProjectsBudgetRange(1000, 1999);
    }
}
