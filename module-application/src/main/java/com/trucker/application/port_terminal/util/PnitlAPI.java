package com.trucker.application.port_terminal.util;

import com.trucker.application.port_terminal.dto.pnitl.VesselScheduleDto;
import com.trucker.application.port_terminal.dto.pnitl.YardCongestionDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Component
public class PnitlAPI {
    private static final String SCRAPING_URL_YARD = "https://www.pnitl.com/infoservice/main/mainPage.jsp";
    private static final String SCRAPING_URL_VESSEL = "https://www.pnitl.com/infoservice/vessel/vslScheduleList.jsp";
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
            "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36";
    private static final DateTimeFormatter VESSEL_DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public YardCongestionDto scrapeYardCongestion() {
        try {
            // random delay(0.5 ~ 1.5 second)
            int delay = 500 + new Random().nextInt(800);
            Thread.sleep(delay);

            Document doc = Jsoup.connect(SCRAPING_URL_YARD)
                    .userAgent(USER_AGENT)
                    .timeout(5000)
                    .get();

            Element table = doc.selectFirst("div.truckTurnTime table");

            if (table == null) {
                System.out.println("오류: '터미널야드 혼잡 현황' 테이블을 찾을 수 없습니다.");
                return null;
            }

            Elements rows = table.select("tbody tr");

            if (rows.size() < 4) {
                System.out.println("오류: 테이블의 데이터 행 개수가 예상과 다릅니다.");
                return null;
            }

            String yardVehicleImport = rows.get(1).select("td").get(1).text().trim();
            String yardVehicleExport = rows.get(1).select("td").get(2).text().trim();
            String truckTurnImport = rows.get(2).select("td").get(1).text().trim();
            String truckTurnExport = rows.get(2).select("td").get(2).text().trim();
            String yardStatus = rows.get(3).select("td").text().trim();

            return YardCongestionDto.builder()
                    .yardVehicleImport(yardVehicleImport)
                    .yardVehicleExport(yardVehicleExport)
                    .truckTurnTimeImport(truckTurnImport)
                    .truckTurnTimeExport(truckTurnExport)
                    .yardStatus(yardStatus)
                    .build();

        } catch (IOException e) {
            System.out.println("HTTP 요청 중 오류가 발생했습니다: " + e.getMessage());
            return null;
        } catch (InterruptedException e) {
            System.out.println("스레드 오류: " + e.getMessage());
            Thread.currentThread().interrupt();
            return null;
        } catch (Exception e) {
            System.out.println("데이터 처리 중 오류가 발생했습니다: " + e.getMessage());
            return null;
        }
    }

    public VesselScheduleDto scrapeVesselSchedule(String vesselName) {
        try {
            int delay = 500 + new Random().nextInt(800);
            Thread.sleep(delay);

            Document doc = Jsoup.connect(SCRAPING_URL_VESSEL)
                    .userAgent(USER_AGENT)
                    .timeout(5000)
                    .get();

            // NOTE: 태그 수정시 수정 필요
            Element table = doc.selectFirst("div.tblType_08 table");
            if (table == null) {
                System.out.println("오류: '선석 배정현황' 테이블을 찾을 수 없습니다.");
                return null;
            }

            Elements rows = table.select("tbody tr");

            for (Element row : rows) {
                Elements cells = row.select("td");

                if (cells.size() > 5 && cells.get(5).text().trim().equalsIgnoreCase(vesselName)) {

                    LocalDateTime closingTime = parseVesselDateTime(cells.get(7).text().trim());
                    LocalDateTime atbEtb = parseVesselDateTime(cells.get(8).text().trim());
                    LocalDateTime atdEtd = parseVesselDateTime(cells.get(9).text().trim());

                    return VesselScheduleDto.builder()
                            .berth(cells.get(0).text().trim())
                            .opr(cells.get(1).text().trim())
                            .vvd(cells.get(2).text().trim())
                            .lineVvd(cells.get(3).text().trim())
                            .headBridgeStern(cells.get(4).text().trim())
                            .vesselName(cells.get(5).text().trim())
                            .route(cells.get(6).text().trim())
                            .closingTime(closingTime)
                            .atbEtb(atbEtb)
                            .atdEtd(atdEtd)
                            .dis(cells.get(10).text().trim())
                            .load(cells.get(11).text().trim())
                            .shift(cells.get(12).text().trim())
                            .amp(cells.get(13).text().trim())
                            .status(cells.get(14).text().trim())
                            .build();
                }
            }

            System.out.println("'" + vesselName + "' 선박 정보를 찾을 수 없습니다.");
            return null;

        } catch (IOException e) {
            System.out.println("HTTP 요청 중 오류가 발생했습니다: " + e.getMessage());
            return null;
        } catch (InterruptedException e) {
            System.out.println("스레드 오류: " + e.getMessage());
            Thread.currentThread().interrupt();
            return null;
        } catch (Exception e) {
            System.out.println("데이터 처리 중 오류가 발생했습니다: " + e.getMessage());
            return null;
        }
    }

    private LocalDateTime parseVesselDateTime(String dateTimeString) {
        if (dateTimeString == null || dateTimeString.isBlank()) {
            return null;
        }
        try {
            return LocalDateTime.parse(dateTimeString, VESSEL_DATE_TIME_FORMATTER);
        } catch (Exception e) {
            System.out.println("날짜 파싱 오류: '" + dateTimeString + "' - " + e.getMessage());
            return null;
        }
    }
}
