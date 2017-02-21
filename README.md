# BOJ-SRC-DOWNLOADER

## BOJ(Beakjoon Online Judge, acmicpc.net) Source Downloader

### BOJ에 제출한 자신의 코드를 다운로드 받아주는 스크래핑 프로그램입니다.

### 의존성 
1. JSoup 1.10.2 (프로젝트에 포함되어 있음)
2. jdk 1.8.0_40

### 실행 방법
1. BOJ 아이디와 비밀번호를 Crawl.java 23, 24번째 줄에 입력합니다.
2. 프로젝트를 빌드하고, 실행합니다.

### Output
1. 프로그램이 워크스페이스에 폴더를 생성합니다.
   result -- problem
          -- source   
2. 자신이 해결한 문제의 소스코드와 문제 명세를 크롤링해서 각 폴더에 출력합니다.


제작 : jcdgods@gmail.com

비상업용, 무료, 출처표기, 발생하는 모든 책임은 사용자에게 있음.