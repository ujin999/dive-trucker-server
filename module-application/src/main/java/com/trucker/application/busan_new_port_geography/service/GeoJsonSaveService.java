package com.trucker.application.busan_new_port_geography.service;

import com.trucker.application.busan_new_port_geography.entity.CoordinateDto;
import com.trucker.application.busan_new_port_geography.entity.NavigationStepDto;
import com.trucker.application.busan_new_port_geography.entity.PnitLine;
import com.trucker.application.busan_new_port_geography.entity.PnitVertex;
import com.trucker.application.busan_new_port_geography.repository.PnitLineRepository;
import com.trucker.application.busan_new_port_geography.repository.PnitVertexRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;

@Service
@RequiredArgsConstructor
public class GeoJsonSaveService {
    private static final Map<Character, Integer> CODE_TO_AISLE = Map.ofEntries(
            Map.entry('H', 0), Map.entry('G', 1), Map.entry('F', 2), Map.entry('E', 3),
            Map.entry('D', 4), Map.entry('C', 5), Map.entry('B', 6), Map.entry('A', 7)
    );
    private final PnitLineRepository pnitLineRepository;
    private final PnitVertexRepository pnitVertexRepository;

    @Transactional(readOnly = true)
    public List<NavigationStepDto> getNavigationPath(String yardCode) {
        if (yardCode.length() != 2) {
            throw new IllegalArgumentException();
        }

        String alphabetCode = yardCode.substring(0, 1);

        List<NavigationStepDto> path = new ArrayList<>();
        path.addAll(getNavigationPathByAlphabetCode(alphabetCode));

        int numCode = Integer.parseInt(yardCode.substring(1, 2));
        path.addAll(getNavigationPathByNumber(alphabetCode, numCode));

        return path;
    }

    // helper
    @Transactional(readOnly = true)
    public List<NavigationStepDto> getNavigationPathByAlphabetCode(String destinationCode) {
        char code = destinationCode.toUpperCase().charAt(0);

        if (!CODE_TO_AISLE.containsKey(code)) {
            throw new IllegalArgumentException("잘못된 코드입니다. A-H 사이의 문자를 입력해주세요.");
        }

        int targetAisleNumber = CODE_TO_AISLE.get(code);

        List<NavigationStepDto> path = new ArrayList<>();

        if (targetAisleNumber == 0) {
            return path;
        }

        List<PnitLine> lines = pnitLineRepository.findPnitLinesWithVerticesByRoadNameStartingWith("vertical_line1-");
        if (lines.isEmpty()) {
            throw new EntityNotFoundException("Road not found: vertical_line1-");
        }

        lines.sort(Comparator.comparing(PnitLine::getRoadName));

        CoordinateDto firstCoordinate = getStartPointCoordinate(lines.get(0));
        path.add(new NavigationStepDto(0, firstCoordinate));

        for (int i = 1; i < targetAisleNumber; i++) {
            PnitLine line = lines.get(i);
            CoordinateDto coordinate = getEndPointCoordinate(line);
            path.add(new NavigationStepDto(1, coordinate));
        }

        PnitLine line = lines.get(targetAisleNumber);
        CoordinateDto coordinate = getEndPointCoordinate(line);
        path.add(new NavigationStepDto(2, coordinate));

        return path;
    }

    @Transactional(readOnly = true)
    public List<NavigationStepDto> getNavigationPathByNumber(String destinationCode, int destinationNumber) {
        if (destinationNumber <= 0) {
            throw new IllegalArgumentException("목표 숫자는 1 이상이어야 합니다.");
        }
        char code = destinationCode.toUpperCase().charAt(0);
        if (!CODE_TO_AISLE.containsKey(code)) {
            throw new IllegalArgumentException("잘못된 코드입니다. A-H 사이의 문자를 입력해주세요.");
        }

        int lineGroupNumber = CODE_TO_AISLE.get(code) + 1;
        String roadNamePrefix = String.format("horizon_line%d-", lineGroupNumber);

        List<NavigationStepDto> path = new ArrayList<>();

        List<PnitLine> lines = pnitLineRepository.findPnitLinesWithVerticesByRoadNameStartingWith(roadNamePrefix);
        if (lines.isEmpty()) {
            throw new EntityNotFoundException("Road not found: " + roadNamePrefix);
        }
        lines.sort(Comparator.comparing(PnitLine::getRoadName));

        for (int i = 0; i < destinationNumber - 1; i++) {
            PnitLine line = lines.get(i);

            path.add(new NavigationStepDto(1, getEndPointCoordinate(line)));
        }

        PnitLine finalLine = lines.get(destinationNumber - 1);

        path.add(new NavigationStepDto(1, getMidPointCoordinate(finalLine)));

        return path;
    }

    private CoordinateDto getStartPointCoordinate(PnitLine line) {
        PnitVertex firstVertex = line.getVertices().stream().findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Start vertex not found for line: " + line.getRoadName()));
        return new CoordinateDto(firstVertex.getLongitude(), firstVertex.getLatitude());
    }

    private CoordinateDto getEndPointCoordinate(PnitLine line) {
        List<PnitVertex> vertices = new ArrayList<>(line.getVertices());
        if (vertices.isEmpty()) {
            throw new EntityNotFoundException("End vertex not found for line: " + line.getRoadName());
        }
        PnitVertex lastVertex = vertices.get(vertices.size() - 1);
        return new CoordinateDto(lastVertex.getLongitude(), lastVertex.getLatitude());
    }


    private CoordinateDto getMidPointCoordinate(PnitLine line) {
        CoordinateDto start = getStartPointCoordinate(line);
        CoordinateDto end = getEndPointCoordinate(line);

        double midLon = (start.getLongitude() + end.getLongitude()) / 2.0;
        double midLat = (start.getLatitude() + end.getLatitude()) / 2.0;

        return new CoordinateDto(midLon, midLat);
    }
}
