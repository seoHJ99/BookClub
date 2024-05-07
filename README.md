
### Purpose
화상 채팅, redis를 통한 인기 글 순위, ISBN을 통한 책 검색, 일반 채팅 기능이 있는 모임 커뮤니티 게시판

개인프로젝트

### Development Environment

|              |                                                   |
| ------------ | ------------------------------------------------- |
| OS           | Windows 10                                        |
| Language     | Java, JavaScript, HTML/CSS                        |
| Tool         | VS Code, SpringBoot, IntelliJ, Bootstrap5, Github |
| Api          | 네이버 검색                                        |
| Server       | Tomcat 9                                          |
| DB           | ORACLE, Redis                                     |
### Database
![image](https://github.com/seoHJ99/BookClub/blob/main/%EB%B6%81%ED%81%B4%EB%9F%BD%20erd.png)


### Functional Decomposition

| 페이지           | 세부기능             | 설명                                                       |
| ---------------- | -------------------- | ---------------------------------------------------------- |
| 메인화면          | 인기 책             | redis를 이용해 당일 가장 많은 리뷰가 작성된 책을 찾는다.                    |
| 모임 페이지       | 가입           | 모임에 가입한다 |
|                  | 일정         | 일정을 만들고 참가/불참을 정한다.                                           |
|                  | 게시판       | 모임 전용 게시판                                      |
|                  | 화상 채팅            | 일정이 온라인 화상채팅일 경우 참여 가능                 |
|                  | 일반 채팅            | 모임원간의 일반 채팅                 |
| 리뷰 게시판       | 책 검색          | 네이버 api를 이용한 책 검색                   |
|                  | 등록       | 모든 회원이 작성 가능한 리뷰 게시판                                 |
|                  | 댓글    | 모든 회원이 작성 가능한 댓글                               |
| 회원가입         | 중복 확인 | 기존 등록된 id와 중복하는지 확인해준다. redis를 통해 이중 체크한다. |
|                 | 비밀번호 확인             | 본인이 등록하고자 하는 비밀번호와 일치하는지 확인해준다.                     |
| 마이 페이지      | 내정보            | 로그인한 회원의 비밀번호를 재입력한 후, 내정보 조회, 수정이 가능하다.              |
|                 | 나의 댓글 내역            | 내가 등록한 댓글들만 모아서 정렬한다. 게시판 글을 볼수 있다.                             |
|                 | 나의 게시글 내역            |  내가 작성한 게시글들만 모아서 정렬한다. 모임 게시글과 일반 리뷰 게시글이 구분된다.       |

### api 명세서
![image](https://github.com/seoHJ99/BookClub/assets/121778872/4cd5919b-54a7-4af4-a9de-3a059f2f7866)



### Demonstration


### Report


### Team Members

서호준
