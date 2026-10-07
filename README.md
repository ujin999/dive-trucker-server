# Dive Trucker Server

부산 신항 컨테이너 트럭 기사를 위한 **운행 지원 백엔드 API 서버**입니다.

오더 관리, 최적 출발 시각 계산, 터미널 혼잡도·선박 일정 조회, 경로상 최저가 주유소 추천, 터미널 야드 내 길안내 기능을 제공합니다.

* **기간**: 2025.08 ~
* **인원**: 4명
* **역할**: Backend / Server
* **초기 커밋**: 2025.08.16
* **기본 API 구현**: 2025.08.17 ~ 2025.08.25

## My Role

* Spring Boot 기반 백엔드 API 개발
* 외부 API 연동 및 데이터 변환
* 최적 출발 시각 계산
* 경로 기반 주유소 추천
* 터미널 정보 수집 및 야드 내 길안내 기능 구현

## Tech Stack

| 구분                   | 내용                               |
| -------------------- | -------------------------------- |
| Language / Framework | Java 17, Spring Boot 3.5.4       |
| Data                 | Spring Data JPA, PostgreSQL      |
| HTTP Client          | WebClient(WebFlux), RestTemplate |
| Parsing              | Jsoup(HTML 스크래핑), Jackson XML    |
| 좌표 변환                | proj4j                           |
| Build                | Gradle 멀티 모듈                     |
| Test                 | JUnit 5                          |

## Architecture

Gradle 멀티 모듈 구조로 공통 기능과 애플리케이션의 책임을 분리했습니다.

```text
module-core        공통 예외, 응답 형식, BaseTimeEntity
module-common      공유 도메인 엔티티 (User, Role, UserRole)
module-application 실행 모듈 - controller / service / repository / dto / entity
```

## Domains & Features

| 도메인            | 주요 API                                             | 설명                              |
| -------------- | -------------------------------------------------- | ------------------------------- |
| user           | `/api/users`                                       | 회원 생성·조회                        |
| vehicle        | `/api/vehicles`                                    | 차량(차종, 연료) 등록·수정·조회             |
| location       | `/api/locations` 외                                 | 상·하차지 등록·조회                     |
| order          | `/api/orders`                                      | 오더 생성·조회·수정, 상태 전이, 최적 출발 시각 계산 |
| gas station    | `POST /api/gas-stations/by-path`                   | 이동 경로 위의 최저가 주유소 TOP 3 추천       |
| port terminal  | `/api/port-terminal/entry-info`, `/departure-info` | 터미널 야드 혼잡도, 선박 입출항 일정           |
| port geography | `GET /api/ports/navigate/{yardCode}`               | 야드 블록 코드로 터미널 내 길안내 경로 반환       |

## Highlights

### 최적 출발 시각 계산

카카오 모빌리티 길찾기 API로 이동시간을 확인하고, 예상 출발 시각 기준 미래 운행정보와 시간대별 터미널 대기시간, 여유 버퍼를 반영하여 목표 도착 시각에서 출발 시각을 역산합니다.

### 경로 기반 주유소 추천

경로 좌표별로 오피넷 주변 주유소 API를 `WebClient`로 병렬 호출하고, 중복 제거 후 가격순으로 정렬하여 최저가 주유소 TOP 3를 선정합니다. 이후 상세 정보(XML)를 조회해 최종 응답을 구성합니다.

### 터미널 정보 스크래핑

부산신항 터미널(PNITL) 웹페이지를 `Jsoup`으로 파싱해 야드 혼잡도와 선박 일정을 API로 제공합니다. 요청 간 지연과 파싱 실패에 대한 방어 처리를 적용했습니다.

### 야드 내 길안내

터미널 도로를 선(Line)과 정점(Vertex) 데이터로 저장하고, 블록 코드(예: `C3`)를 기반으로 터미널 내부 경로를 `NavigationStep`으로 변환합니다.

### 공통 응답 / 예외 처리

`ApiResponse`, `ErrorCode`, `GlobalExceptionHandler`를 구성하여 API 응답과 예외 처리 형식을 통일했습니다.

## Getting Started

```bash
# 환경: JDK 17, PostgreSQL

./gradlew :module-application:bootRun
```

`application-dev.yml`에 DB 접속 정보와 외부 API 키(오피넷, 카카오)를 설정해야 합니다.

기본 포트는 `8080`입니다.

## 개선 예정

* JWT 인증/인가
* 터미널 혼잡도 실데이터를 출발 시각 계산에 반영
* 스크래핑 결과 캐싱
* 외부 API 호출 실패 시 재시도 / 폴백
* 추가 테스트 케이스 작성 예정