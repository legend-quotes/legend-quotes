# 🎓 레전드 명언록

교수님의 인상 깊은 명언을 기록하고 공유하는 웹 플랫폼

## 📋 프로젝트 개요

수업 중 교수님이 남긴 레전드 명언들을 교수/과목별로 기록하고, 사용자들과 공유할 수 있는 커뮤니티 플랫폼입니다.

## 🛠️ 기술 스택

- **Backend**: Java 21, Spring Boot 3, Spring MVC, JPA (Hibernate)
- **Frontend**: Thymeleaf, HTML5, CSS3, JavaScript
- **Database**: MySQL
- **Build Tool**: Gradle

## 🚀 주요 기능

### ✅ 구현 완료
- 회원가입 / 로그인 (쿠키 기반 인증)
- 게시글 CRUD (작성/조회/수정/삭제)
- 게시글 정렬 (최신순/인기순)
- 좋아요 기능
- 댓글 시스템 (엔티티 구현)
- 반응형 웹 디자인
- 마이페이지

### 🔄 개발 예정
- 검색 및 필터링
- 댓글 CRUD 기능 완성
- 알림 시스템

## 📁 프로젝트 구조

```
src/
├── main/
│   ├── java/com/gangneng/legend_quotes/
│   │   ├── post/           # 게시글 관련 (CRUD, 좋아요)
│   │   ├── user/           # 사용자 관련 (회원가입/로그인)
│   │   ├── comment/        # 댓글 관련
│   │   ├── like/           # 좋아요 관련
│   │   ├── search/         # 검색 관련 (예정)
│   │   └── web/            # 웹 컨트롤러 (페이지 라우팅)
│   └── resources/
│       ├── templates/      # Thymeleaf 템플릿
│       ├── static/css/     # CSS 파일
│       └── application.yml # 설정 파일
```

## 🔧 설치 및 실행

### 1. 프로젝트 클론
```bash
git clone https://github.com/your-username/legend-quotes.git
cd legend-quotes
```

### 2. 데이터베이스 설정

#### 도커를 사용한 빠른 설정 (권장)

**1) 도커 설치**
도커 데스크탑 설치: https://www.docker.com/products/docker-desktop/

**2) 도커 실행 & DB 생성**
도커 데스크탑 실행 후 터미널에 아래 명령어 입력:
```bash
docker run -d \
  --name mysql-local \
  -e MYSQL_ROOT_PASSWORD=my-secret-pw \
  -e MYSQL_DATABASE=legend_db \
  -p 3306:3306 \
  mysql:8.0
```

**3) 데이터베이스 접속 정보**
MySQL Workbench나 DataGrip 같은 DB 툴 사용:
```
host: localhost
port: 3306
username: root
password: my-secret-pw
database: legend_db
```

#### 직접 MySQL 설치 시
MySQL에서 데이터베이스 생성:
```sql
CREATE DATABASE legend_db;
```

`application.yml` 설정:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/legend_db
    username: your_username
    password: your_password
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

### 3. 애플리케이션 실행
```bash
./gradlew bootRun
```

### 4. 브라우저에서 접속
```
http://localhost:8080
```

## 📱 화면 구성

| 화면 | 경로 | 설명 |
|------|------|------|
| 홈 | `/` | 인기 명언 미리보기 |
| 로그인 | `/login` | 사용자 로그인 |
| 회원가입 | `/signup` | 새 사용자 등록 |
| 게시판 | `/posts` | 모든 게시글 조회 |
| 글쓰기 | `/posts/create` | 새 게시글 작성 |
| 게시글 상세 | `/posts/{id}` | 게시글 상세 조회 |
| 게시글 수정 | `/posts/{id}/edit` | 게시글 수정 |
| 마이페이지 | `/profile` | 사용자 프로필 |

## 🔌 API 엔드포인트

### 사용자 관리
- `POST /api/users/signup` - 회원가입
- `POST /api/users/signin` - 로그인
- `GET /api/users/profile` - 프로필 조회
- `PUT /api/users/profile` - 프로필 수정
- `DELETE /api/users/profile` - 회원 탈퇴

### 게시글 관리
- `GET /api/posts` - 모든 게시글 조회 (정렬 지원)
- `POST /api/posts` - 게시글 작성
- `GET /api/posts/{id}` - 게시글 상세 조회
- `PUT /api/posts/{id}` - 게시글 수정
- `DELETE /api/posts/{id}` - 게시글 삭제
- `POST /api/posts/{id}/like` - 좋아요 토글

### 댓글 관리
- `GET /api/posts/{postId}/comments` - 댓글 조회
- `POST /api/posts/{postId}/comments` - 댓글 작성
- `DELETE /api/posts/{postId}/comments/{commentId}` - 댓글 삭제

## 🎨 디자인 특징

- **그라디언트 컬러**: 보라색 계열 (#667eea → #764ba2)
- **반응형 디자인**: 모바일/데스크톱 지원
- **카드 기반 UI**: 게시글 카드 레이아웃
- **호버 효과**: 인터랙티브한 사용자 경험

## 👥 개발팀

- **게시판**: 현구
- **게시판 부가기능**: 소현  
- **회원관리**: 유빈
- **필터링**: 이레

## 📝 개발 일정

- **1주차**: 기본 CRUD, 인증 시스템
- **2주차**: 부가 기능, UI 개선, 테스트

## 🔒 보안 기능

- 쿠키 기반 사용자 인증
- HttpOnly 쿠키 설정
- 5시간 자동 로그아웃

## 📄 라이선스

MIT License