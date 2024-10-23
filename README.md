[# Delivery-System

AI를 활용한 대규모 배달 시스템

## 🐶 구성원

| 이름                                    | 역할 분담         |
|---------------------------------------|---------------|
| [김수봉](https://github.com/bongbongbon) | 주문, 배달        |
| [배지원](https://github.com/Bae-Ji-Won)  | 인증, 유저, 게이트웨이 |
| [김형철](https://github.com/shurona)     | 쿠폰, 라이더, 인프라  |
| [유수인](https://github.com/jjong52)     | 가게, 상품, 카테고리  |

## 💻 기술 스택

- Language: Java 17
- Framework: Spring boot 3.x
- cloud: Spring Eureka
- Repository: MySql, Redis
- Messaging: Kafka
- Build Tool: Gradle 8.x
- Monitoring: Grafana, Prometheus
- CI: GitHub Action

## ✈️ 실행 방법

### 인프라 실행

```shell
docker compose -f infra-compose.yml up -d
```

### 서비스 실행

```shell
./build_service.sh
docker compose up -d
```

### 환경 변수

.env.example 파일을 확인해주세요

## 💾 인프라 설계도

링크 페이지에서 추가

## 🛵 주요 기능

### SAGA 패턴을 이용한 분산 트랜잭션 구현

- 배달이 30분 내에 배차되지 않으면 실패한 트랜잭션으로 간주하고 배달 취소 이벤트를
  `delivery-rollback` 토픽에 발행합니다.
- 매 1분마다 스케줄러가 실행되어 30분이 지난 배달 건을 조회하고 이를 취소 처리합니다.
- 이 이벤트는 주문 서비스의 `deliveryConsumer`가 받아 롤백을 처리하며, 다른 서비스에 이 사실을 알릴 수 있도록 합니다.

### Redis Geo를 이용한 위치 기반 가게 검색 시스템

- Redis Geo는 공간 데이터(위도 및 경도)를 효율적으로 저장하고 검색할 수 있도록 설계되어 있으므로 이를 이용해서 유저의 위치 일정 반경 내의 가게를 신속하게 조회함으로써
  포장 주문 가게를 조회하는 기능으로 이용

### 주문 생성 시 배달 생성

- CQRS 패턴에서는 명령(Command)과 조회(Query)를 분리하는데, MongoDB는 조회 성능을 최적화하기 위해 사용됩니다.
- MongoDB는 데이터를 읽을 때 높은 성능을 제공해, 관계형 DB에서 발생하는 복잡한 조인이나 N+1 문제를 피할 수 있어 대규모 데이터를 처리할 때도 성능을 유지합니다.
- MongoDB는 단순한 키-값 조회뿐만 아니라 복잡한 질의도 Criteria를 사용해 다양한 조건으로 필터링할 수 있으며, 복잡한 조회 작업에도 성능 저하 없이 처리가
  가능합니다.

### Sorted Set 및 Kafka를 사용해서 쿠폰 비동기 발급 기능

- Redis의 Sorted Set 기능을 이용해서 입력을 받은 시간을 기준으로 정렬을 진행함으로써 대기번호를 구현
- Sorted Set에 값을 넣기 전에 미리 확인해서 이미 대기 중인 유저의 진입을 방지
- 쿠폰 발급 가능 여부 확인 후 요청들은 모두 Kafka로 데이터를 전달

### 분산락을 이용한 쿠폰 개수 일관성 유지

- 쿠폰 발급 메서드에 AOP로 분산 락을 적용하여 동시에 여러 요청이 들어와도 필드 값의 일관성을 유지하도록 구성

## 기술적 의사 결정

<details> <summary>멀티 모듈에서 공통 주소 처리</summary>
<br>

### 고민 했던 사항

서비스 모듈 간 주소 선택 기준으로 정부에서 제공하는 법정동 ID를 사용하기로 결정했습니다. 이에 따라 주소 정보를 각 모듈 간에 어떻게 관리할 지 고민을 하였습니다.

- **별도의 서버 생성**: 다른 모듈에서 사용하는 주소를 위해 서버를 생성
- **CSV 파일 개별 관리**: 각 모듈에서 별도로 CSV파일을 관리

그러나 이번 프로젝트를 MSA로 구성을 하면서 이미 여러 서버가 존재하는 상황에서 공통 주소 처리를 위한 서버를 하나 더 추가하는 것은 부담이 될 것으로 판단 되었고 CSV 파일이
변경될 때마다 모든 서비스에서 동일한 작업을 반복해야 하는 것은 비효율적이라 판단되었습니다.

CSV파일이 5MB 정도이고 전체 라인도 5만라인으로 한 번 순회하는 데도 0.1초도 안걸리기 때문에 이에 대한 대안으로 멀티 모듈 공통 라이브러리를 사용해서 CSV 파일을 통합
관리하는 방안으로 결정하였습니다.

### 결정 사항

openCSV를 사용해서 저장된 CSV파일을 메모리로 읽어 온다.

```java
private final ResourceLoader resourceLoader;

Resource resource = resourceLoader.getResource("classpath:" + ADDRESS_CSV);

CSVReader reader = new CSVReader(
    new InputStreamReader(resource.getInputStream(), Charset.forName("EUC-KR")));

List<String[]> strings = reader.readAll();
this.addressSetList =strings.

subList(1,strings.size());
```

이후 주소 목록의 저장방식을 `code: 주소정보` 의 Map 형식으로 보관하여서 code로도 접근할 수 있고 `values` method를 이용한 목록 접근으로도 가능하게
하였다.
</details>

## 👽 참가자

| 이름                                    | 역할 분담                                                                                                                                                                                                                                                                                                                                           |
|---------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [김수봉](https://github.com/bongbongbon) | **주문**: CQRS 패턴으로 N+1 문제 해결 <br> Kafka로 MongoDB와 MySQL 동기화 <br> **배달**: SAGA 패턴으로 30분 이상 배차 실패 시 롤백 메시지 전송                                                                                                                                                                                                                                      |
| [배지원](https://github.com/Bae-Ji-Won)  | ▶ Auth <br>- 이메일 인증 기능 구현(유저 인증 고도화) <br>- Refresh Token 사용(토큰 탈취 위험 보완)  <br>- Redis를 활용 블랙리스트 기능 구현(로그아웃 기능 구현) <br>▶ User <br>- GeoCoding을 통한 유저 주소 위/경도 추출 <br>- Redis & Kafka를 통한 장바구니 기능 구현                                                                                                                                               |
| [김형철](https://github.com/shurona)     | ▶ 공통 모듈 구성 <br>- 멀티 모듈 구조로 기본 레포 구성 <br>- 공통 주소 정보 처리 위한 csv 리더 라이브러리 구성 <br>▶ 쿠폰 모듈 <br>-  Kafka를 사용한 대규모 트래픽 처리 <br>- Sorted Set을 사용해서 클라이언트 대기 번호 조회 <br>▶ 라이더 모듈  <br>- 라이더 CRUD <br>▶ 배포 설정 <br>- Docker-compose 및 CI 설정 <br>- EC2 및 ECR을 사용한 배포 및 인프라 설정 <br>- coupon모듈에 grafana 및 prometheus 설정 <br>▶ 로그 설정 <br>- ELK를 이용해서 로그를 검색하도록 설정 |
| [유수인](https://github.com/jjong52)     | ▶ STORE (상점) <br>- 배달 주문 가능 가게 조회 <br>법정동 코드로 가게 주인이 배달 가능 구역을 등록 / 유저의 주소가 배달 가능 구역에 포함되는 경우에만 가게가 조회되도록 구현 <br>- 포장 주문 가게 조회 <br>Redis Geo로 유저의 위치에서 일정 반경 내 가게만 조회되도록 구현 <br>▶ PRODUCT (상품) <br>- 가게주인이 상품을 등록하고 수정하는 기능 <br>▶ CATEGORY (카테고리) <br>- 관리자가 카테고리를 등록하고 수정하는 기능 <br>- 카테고리별 가게 조회 기능                                            |


