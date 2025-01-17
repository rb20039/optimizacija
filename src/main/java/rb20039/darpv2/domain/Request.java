package rb20039.darpv2.domain;

import ai.timefold.solver.core.api.domain.entity.PlanningEntity;
import ai.timefold.solver.core.api.domain.lookup.PlanningId;
import ai.timefold.solver.core.api.domain.variable.PlanningVariable;

import java.time.LocalTime;

@PlanningEntity
public class Request {
    @PlanningId
    private String id;

    private int passengerCount;

    private Location pickupLocation;
    private Location dropOffLocation;

    private LocalTime timeWindowStart;
    private LocalTime timeWindowEnd;


    private Vehicle assignedVehicle;

    @PlanningVariable(valueRangeProviderRefs = "visitSequenceRange")
    private Integer visitSequence;

    public Integer getVisitSequence() {
        return visitSequence;
    }

    public void setVisitSequence(Integer visitSequence) {
        this.visitSequence = visitSequence;
    }

    public Request(String id, Location pickupLocation, Location dropOffLocation, int passengerCount, String timeWindowStart, String timeWindowEnd, Integer visitSequence, Vehicle assignedVehicle) {
        this.id = id;
        this.pickupLocation = pickupLocation;
        this.dropOffLocation = dropOffLocation;
        this.passengerCount = passengerCount;
        this.timeWindowStart = LocalTime.parse(timeWindowStart);
        this.timeWindowEnd = LocalTime.parse(timeWindowEnd);
        this.assignedVehicle = assignedVehicle;
    }

    public Request() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Location getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(Location pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public Location getDropOffLocation() {
        return dropOffLocation;
    }

    public void setDropOffLocation(Location dropOffLocation) {
        this.dropOffLocation = dropOffLocation;
    }

    public int getPassengerCount() {
        return passengerCount;
    }

    public void setPassengerCount(int passengerCount) {
        this.passengerCount = passengerCount;
    }

    public LocalTime getTimeWindowStart() {
        return timeWindowStart;
    }

    public void setTimeWindowStart(String timeWindowStart) {
        this.timeWindowStart = LocalTime.parse(timeWindowStart);
    }

    public LocalTime getTimeWindowEnd() {
        return timeWindowEnd;
    }

    public void setTimeWindowEnd(String timeWindowEnd) {
        this.timeWindowEnd = LocalTime.parse(timeWindowEnd);
    }

    public Vehicle getAssignedVehicle() {
        return assignedVehicle;
    }
    public void setAssignedVehicle(Vehicle assignedVehicle) {
        this.assignedVehicle = assignedVehicle;
    }

}
