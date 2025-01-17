package rb20039.darpv2.constraint;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.score.stream.Constraint;
import ai.timefold.solver.core.api.score.stream.ConstraintFactory;
import ai.timefold.solver.core.api.score.stream.ConstraintProvider;
import ai.timefold.solver.core.api.score.stream.Joiners;
import rb20039.darpv2.domain.Request;
import rb20039.darpv2.domain.Vehicle;

public class DARPConstraints implements ConstraintProvider {

    @Override
    public Constraint[] defineConstraints(ConstraintFactory factory) {

        return new Constraint[]{
                vehicleCapacityConstraint(factory),
                //        missedRequestConstraint(factory),
                //        timeWindowConstraint(factory),
                //        minimizeTravelDistance(factory),
                //        minimizeIdleVehicle(factory),
                passengerRequestQuality(factory)
        };
    }

    private Constraint vehicleCapacityConstraint(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(Request.class)
                .join(Request.class,
                        Joiners.equal(Request::getAssignedVehicle),
                        Joiners.filtering((req1, req2) ->
                                isActiveOverlap(req1, req2)
                        )
                )
                .groupBy(
                        (req1, req2) -> req1.getAssignedVehicle(),
                        (req1, req2) -> req1.getPassengerCount() + req2.getPassengerCount()
                )
                .filter((vehicle, totalPassengers) -> totalPassengers > vehicle.getCapacity())
                .penalize(HardSoftScore.ONE_HARD).asConstraint("Vehicle capacity exceeded");
    }

    /*private Constraint missedRequestConstraint(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(Request.class)
                .ifExists(Vehicle.class, (request, vehicle) -> vehicleHasRequest(request, vehicle))
                .penalize(HardSoftScore.ONE_HARD).asConstraint("MissedRequest");
    }

    private Constraint timeWindowConstraint(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(RideRequest.class)
                .filter(request -> request.getPickupTime() < request.getTimeWindowStart() ||
                        request.getDropOffTime() > request.getTimeWindowEnd())
                .penalize("Time window violated", HardSoftScore.ONE_HARD);
    }*/

    private Constraint passengerRequestQuality(ConstraintFactory factory) {
        return factory.forEach(Request.class)
                .join(Request.class,
                        Joiners.equal(Request::getAssignedVehicle))
                .filter((request1, request2) -> !request1.equals(request2)
                        && isOverlapping(request1, request2))
                .penalize(HardSoftScore.ONE_SOFT).asConstraint("Multiple request at one time");
    }

    private boolean isActiveOverlap(Request req1, Request req2) {
        int req1Pickup = req1.getVisitSequence();
        int req1Dropoff = req1Pickup + 1;
        int req2Pickup = req2.getVisitSequence();
        int req2Dropoff = req2Pickup + 1;

        return (req1Pickup <= req2Dropoff && req2Pickup <= req1Dropoff);
    }

    private boolean vehicleHasRequest(Request request, Vehicle vehicle) {
        return request.getVisitSequence() != null && vehicleHasValidSequence(request);
    }

    private boolean vehicleHasValidSequence(Request request) {
        return request.getVisitSequence() > 0;
    }

    private boolean isOverlapping(Request request1, Request request2) {
        return request1.getTimeWindowEnd().isAfter(request2.getTimeWindowStart())
                && request1.getTimeWindowStart().isBefore(request2.getTimeWindowEnd());
    }
}

