package org.example.dsproject.restapi.api;


import org.example.dsproject.restapi.model.CurrentPercentage;
import org.example.dsproject.restapi.model.UsageData;
import org.example.dsproject.restapi.service.EnergyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class EnergyController {

    @GetMapping("/usage")
    public ResponseEntity<Map<String, Object>> getUsage() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Usage data");
        return ResponseEntity.ok(response);
    }


    @GetMapping("/current-percentage")
    public ResponseEntity<String> getCurrentPercentage() {
        return ResponseEntity.ok("Current percentage");
    }
}
