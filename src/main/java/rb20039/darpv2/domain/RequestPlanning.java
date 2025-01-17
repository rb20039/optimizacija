package rb20039.darpv2.domain;

import ai.timefold.solver.core.api.domain.solution.PlanningEntityCollectionProperty;
import ai.timefold.solver.core.api.domain.solution.PlanningScore;
import ai.timefold.solver.core.api.domain.solution.PlanningSolution;
import ai.timefold.solver.core.api.domain.solution.ProblemFactCollectionProperty;
import ai.timefold.solver.core.api.domain.valuerange.ValueRangeProvider;
import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@PlanningSolution
public class RequestPlanning {
    @PlanningEntityCollectionProperty
    private List<Request> requests;

    @ProblemFactCollectionProperty
    private List<Vehicle> vehicles;

    @ProblemFactCollectionProperty
    private List<Location> locationList;

    @ValueRangeProvider(id = "visitSequenceRange")
    public List<Integer> getVisitSequenceRange() {
        return IntStream.rangeClosed(1, requests.size())
                .boxed()
                .collect(Collectors.toList());
    }

    @PlanningScore
    private HardSoftScore score;

    public RequestPlanning() {
    }

    public RequestPlanning(List<Location> locationList, List<Vehicle> vehicles, List<Request> requests) {
        this.locationList = locationList;
        this.vehicles = vehicles;
        this.requests = requests;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public List<Location> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<Location> locationList) {
        this.locationList = locationList;
    }
}
