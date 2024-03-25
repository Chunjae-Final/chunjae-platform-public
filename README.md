
#  📫 온라인 학업성취도 평가 📫



<!--![256컬러](https://github.com/NovTeamProject/Team_Project/assets/145524959/3298851f-7534-4304-99b3-55106605b887)-->


<div>


| **![awsaws](https://github.com/NovTeamProject/Team_Project/assets/145963611/c33a2433-81d8-4137-88d6-c7c7c350a030)**  | **![notion2](https://github.com/NovTeamProject/Team_Project/assets/145963611/8e1e720e-e202-4ad8-9767-0befe6dcf529)** |
| :------: |  :------: |
|  [@AWS](https://midd.genia-academy.net)  |   [@Notion](https://www.notion.so/3-Spring-Boot-5bcada94280648b5934c06c567be67ff)  |  


</div>

## 📚 목차 📚

01. [📖 프로젝트 소개](#-프로젝트-소개)
02. [🙋‍♀️ Team_Member](#%EF%B8%8F-team_member-%EF%B8%8F)
03. [📹 기능 설명](#-기능-설명-)
04. [🔨개발 환경](#-개발-환경-)
05. [🤐개선 사항](#-개선-사항-)


## 📖 프로젝트 소개📖

 기존의 천재교육 온라인 학업성취도 평가의 화면을 참고하여 기능 구현 <br><br>

## 🙋‍♀️ Team_Member 🙋‍♀️

<div>

| **변재혁** | **신수진** | **이무현** | **최경락** | **최재혁** | 
| :------: |  :------: | :------: | :------: | :------: | 
| [<img src="https://avatars.githubusercontent.com/u/145942491?v=4" height=150 width=150> <br/> @jaehyukpyon2](https://github.com/jaehyukpyon2) |[<img src="https://avatars.githubusercontent.com/u/106226864?v=4" height=150 width=150> <br/> @sxzuzv](https://github.com/sxzuzv)|[<img src="https://avatars.githubusercontent.com/u/145963633?v=4" height=150 width=150> <br/> @LMH9999](https://github.com/LMH9999)|[<img src="https://avatars.githubusercontent.com/u/140072536?v=4" height=150 width=150> <br/> @raknrak](https://github.com/raknrak)| [<img src="https://avatars.githubusercontent.com/u/145963663?v=4" height=150 width=150> <br/> @Jaehyuk-96](https://github.com/Jaehyuk-96) |
| 프로젝트 총괄<br>ERD 구성<br>실전시험 구현<br>내 주변 중학교 구현 | ERD 구성<br>실전시험 구현 | ERD 구성<br>Report 구현<br>통계 차트 구현 | ERD 구성<br>로그인/회원가입 구현<br>Spring Security/유효성 검사 적용 | ERD 구성<br>실전시험 구현 | 

</div>



## 📹 기능 설명 📹

<details><summary>Presigned Url</summary> 
<br/>
AWS S3의 PresignedUrl을 통해서 파일을 업로드 및 다운로드 진행

 [code](https://github.com/DeumE-Project/DeumE-Archive/blob/9e66eb6506d1faf696851550387723ff64e013a1/src/main/java/kr/co/chunjae/aws/controller/PresignedUrlController.java#L19C1-L40C2)
 
</details>

<details><summary>Spring security</summary>
<br/>
Sprign security를 SecurityConfig java로 설정하여 database에 관여하는 행동은 로그인이 필요하도록 보안 
 
 [code](https://github.com/DeumE-Project/DeumE-Archive/blob/9e66eb6506d1faf696851550387723ff64e013a1/src/main/java/kr/co/chunjae/security/config/SecurityConfig.java#L11)
</details>
 
<details><summary>REST API 활용</summary>
<br/>
REST API 를 활용하기 위해서 ajax 사용 
 
 [code](https://github.com/DeumE-Project/DeumE-Archive/blob/9e66eb6506d1faf696851550387723ff64e013a1/src/main/webapp/WEB-INF/views/contents/write.jsp#L529C7-L561C11)
</details>


<details><summary>이미지 관련 기능</summary>
<br/>
1. 이미지 등록

https://github.com/DeumE-Project/DeumE-Archive-public/assets/145942491/5b15de41-f63d-4a68-9a57-13f781e227d3

2. 이미지 수정

https://github.com/DeumE-Project/DeumE-Archive-public/assets/145942491/fa44536c-6305-40a5-9f48-e7cb387d41fa

3. 이미지 상세 정보 및 다운로드

https://github.com/DeumE-Project/DeumE-Archive-public/assets/145942491/04820427-9a35-493c-a802-889d9a784411

4. 이미지 리스트 및 다운로드

https://github.com/DeumE-Project/DeumE-Archive-public/assets/145942491/5ddfef75-4491-4d98-83d1-037567d37fdb

</details>


<details><summary>움직이는 이미지 관련 기능</summary>
<br/>
1. 움직이는 이미지 등록

https://github.com/DeumE-Project/DeumE-Archive-public/assets/145524959/72b81f17-d78e-409d-bd4a-d56838a7cdb9


2. 움직이는 이미지 수정


https://github.com/DeumE-Project/DeumE-Archive-public/assets/145524959/decbd84a-8c9b-4480-82bc-858682c31f71


3. 움직이는 이미지 상세 정보 및 다운로드


https://github.com/DeumE-Project/DeumE-Archive-public/assets/145524959/71523439-107d-4c00-980d-6e755473b3ab



</details>
<details><summary>비디오 관련 기능</summary>
<br/>
1. 비디오 등록

https://github.com/DeumE-Project/DeumE-Archive-public/assets/140072536/1ac7249c-598a-4b68-a602-fab2a3149fd7

2. 비디오 수정

https://github.com/DeumE-Project/DeumE-Archive-public/assets/140072536/527ed6f4-f00d-4055-9cf2-04c1d68f7067

3. 비디오 상세 정보 및 다운로드

https://github.com/DeumE-Project/DeumE-Archive-public/assets/140072536/168a2716-8740-428c-a5cc-319c43df336c

4. 비디오 리스트 및 다운로드

https://github.com/DeumE-Project/DeumE-Archive-public/assets/140072536/ea2e4c75-df74-4c61-93f9-985b71c1394d


5. 비디오 상세보기 및 수정 

https://github.com/DeumE-Project/DeumE-Archive-public/assets/140072536/5ac303fd-4c0c-447c-ba44-383cf20a0175

</details>

<details><summary>템플릿 관련 기능</summary>
<br/>
1. 템플릿 등록

https://github.com/LMH9999/Front_Study/assets/145963633/53ac1298-3689-45d8-989d-e227c4368d59

2. 템플릿 수정

https://github.com/LMH9999/Front_Study/assets/145963633/645189ca-4073-444c-b0cf-505727f93a59

3. 템플릿 목록 / 상세보기 / 다운로드

https://github.com/LMH9999/Front_Study/assets/145963633/32b9cfa2-2da9-41b1-9496-f2081fdccb19

</details>

<details><summary>꾸러미 관련 기능</summary>
<br/>
1. 꾸러미 등록

https://github.com/DeumE-Project/DeumE-Archive-public/assets/145963790/508d77cd-9c01-4d87-b8f2-9c27e33cbabd

2. 꾸러미 수정

https://github.com/DeumE-Project/DeumE-Archive-public/assets/145963790/b8140471-943b-4378-9b1f-750bd35bb16f

3. 꾸러미 목록 / 상세보기 / 다운로드

https://github.com/DeumE-Project/DeumE-Archive-public/assets/145963790/ce33ffde-0056-4959-b09a-bd8de763251b

</details>



## 🤐 개선 사항 🤐

<details><summary>개선사항</summary>
<br/>
PresignedUrl을 백서버말고 클라이언트에서 한 이유


</details>

## 🔨 개발 환경 🔨
<div>
<img src="https://img.shields.io/badge/JAVA-C01818?style=flat-square&logo=coffeescript&logoColor=white" />
<img src="https://img.shields.io/badge/HTML5-E34F26?style=flat-square&logo=HTML5&logoColor=fff"/>
<img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=flat-square&logo=JavaScript&logoColor=000"/>
<img src="https://img.shields.io/badge/spring-6DB33F?style=flat&logo=spring&logoColor=white" />
<img src="https://img.shields.io/badge/springsecurity-6DB33F?style=flat&logo=springsecurity&logoColor=white" />
<img src="https://img.shields.io/badge/Springboot-6DB33F?style=flat&logo=springboot&logoColor=white"/>	
<br>  
<img src="https://img.shields.io/badge/amazonaws-232F3E?style=flat-square&logo=amazonaws&logoColor=white" />
<img src="https://img.shields.io/badge/amazonrds-527FFF?style=flat-square&logo=amazonrds&logoColor=white" />
<img src="https://img.shields.io/badge/amazons3-569A31?style=flat-square&logo=amazons3&logoColor=white" />
<img src="https://img.shields.io/badge/amazonec2-FF9900?style=flat-square&logo=amazonec2&logoColor=white" />
<img src="https://img.shields.io/badge/awslambda-FF9900?style=flat-square&logo=awslambda&logoColor=white" />

<br>
<img src="https://img.shields.io/badge/jquery-0769AD?style=flat&logo=jquery&logoColor=white"/>
<img src="https://img.shields.io/badge/CSS3-1572B6?style=flat-square&logo=CSS3&logoColor=fff"/>
<img src="https://img.shields.io/badge/MariaDB-003545?style=flat&logo=MariaDB&logoColor=white" />
<img src="https://img.shields.io/badge/Mybatis-000000?style=flat&logo=Fluentd&logoColor=white"/>
<br>

<img src="https://img.shields.io/badge/IntelliJ-000000?style=flat-square&logo=intellijidea&logoColor=white" />
<img src="https://img.shields.io/badge/Slack-4A154B?style=flat-square&logo=slack&logoColor=white" />
<img src="https://img.shields.io/badge/notion-000000?style=flat-square&logo=notion&logoColor=blue" />  
<img src="https://img.shields.io/badge/GitHub-181717?style=flat-square&logo=GitHub&logoColor=white" />
<img src="https://img.shields.io/badge/Git-F05032?style=flat-square&logo=git&logoColor=white" />

</div>



