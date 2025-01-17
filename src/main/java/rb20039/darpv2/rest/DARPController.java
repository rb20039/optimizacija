package rb20039.darpv2.rest;


import ai.timefold.solver.core.api.solver.SolverManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rb20039.darpv2.domain.RequestPlanning;
import rb20039.darpv2.domain.Request;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("api/darp")
public class DARPController {

    @Autowired
    private final SolverManager<RequestPlanning, UUID> solverManager;

    public DARPController(SolverManager<RequestPlanning, UUID> solverManager) {
        this.solverManager = solverManager;
    }

    @PostMapping("/solve")
    public ResponseEntity<String> solveDARP(@RequestBody RequestPlanning problem) throws ExecutionException, InterruptedException {
        UUID problemId = UUID.randomUUID();
        System.out.println("Received problem ID: " + problemId);

        var solution = solverManager.solve(problemId, problem).getFinalBestSolution();

        if (solution == null || solution.getRequests() == null) {
            return ResponseEntity.status(500).body("No solution found.");
        }

        StringBuilder responseBuilder = new StringBuilder("Optimal Route:\n");

        List<Request> sortedRequests = new ArrayList<>(solution.getRequests());
        sortedRequests.sort(Comparator.comparing(Request::getVisitSequence));

        for (Request request : sortedRequests) {
            responseBuilder.append(String.format(
                    "Visit Sequence: %d, Pickup: %s, Drop-off: %s\n",
                    request.getVisitSequence(),
                    request.getPickupLocation().getName(),
                    request.getDropOffLocation().getName()
            ));
        }

        return ResponseEntity.ok(responseBuilder.toString());
    }
}
