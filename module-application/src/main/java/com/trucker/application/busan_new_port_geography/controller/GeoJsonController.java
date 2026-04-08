package com.trucker.application.busan_new_port_geography.controller;

import com.trucker.application.busan_new_port_geography.entity.NavigationStepDto;
import com.trucker.application.busan_new_port_geography.service.GeoJsonSaveService;
import com.trucker.core.response.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ports")
@AllArgsConstructor
public class GeoJsonController {
    private final GeoJsonSaveService geoJsonSaveService;

    @GetMapping("/navigate/{yardCode}")
    public ResponseEntity<ApiResponse<List<NavigationStepDto>>> getPath(@PathVariable String yardCode) {
        try {
            List<NavigationStepDto> path = geoJsonSaveService.getNavigationPath(yardCode);
            return ResponseEntity.ok(ApiResponse.success(path));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
