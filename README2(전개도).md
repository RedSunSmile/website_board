# website_board
```처음만들다 보니 쇼핑몰이 생각보다 커져서 내부전개도를 첨부 추가하였습니다.
```너무크다보니 프로젝트의 전체 구조입니다

├── build.gradle
├── gradle
│   └── wrapper
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradlew
├── gradlew.bat
├── settings.gradle
└── src
    ├── main
    │   ├── java
    │   │   └── kr
    │   │       ├── Portfolio07Application.java
    │   │       ├── admin
    │   │       │   ├── AdminController.java
    │   │       │   ├── AdminMapper.java
    │   │       │   ├── AdminService.java
    │   │       │   └── dto
    │   │       │       ├── AdminDto.java
    │   │       │       ├── IdCheckResDto.java
    │   │       │       ├── ReplyDto.java
    │   │       │       ├── SearchDto.java
    │   │       │       └── SignupResDto.java
    │   │       ├── complain
    │   │       │   ├── ComplainController.java
    │   │       │   ├── ComplainMapper.java
    │   │       │   ├── ComplainService.java
    │   │       │   └── dto
    │   │       │       ├── ComplainDto.java
    │   │       │       ├── ReplyDto.java
    │   │       │       └── SearchDto.java
    │   │       ├── config
    │   │       │   └── WebConfig.java
    │   │       ├── core
    │   │       │   └── common
    │   │       │       ├── CommonCode.java
    │   │       │       └── CommonResponse.java
    │   │       ├── error
    │   │       │   ├── ErrorCode.java
    │   │       │   └── exception
    │   │       │       └── CustomRestValidException.java
    │   │       ├── exchangestop
    │   │       │   ├── ExchangeStopController.java
    │   │       │   ├── ExchangeStopMapper.java
    │   │       │   ├── ExchangeStopService.java
    │   │       │   ├── ExchangeStopUploadController.java
    │   │       │   └── dto
    │   │       │       ├── ExchangeStopDto.java
    │   │       │       ├── ReplyDto.java
    │   │       │       └── SearchDto.java
    │   │       ├── fix
    │   │       │   ├── FixController.java
    │   │       │   ├── FixMapper.java
    │   │       │   ├── FixService.java
    │   │       │   ├── FixUploadController.java
    │   │       │   └── dto
    │   │       │       ├── FixDto.java
    │   │       │       ├── ReplyDto.java
    │   │       │       └── SearchDto.java
    │   │       ├── main
    │   │       │   └── MainController.java
    │   │       ├── member
    │   │       │   ├── MemberController.java
    │   │       │   ├── MemberMapper.java
    │   │       │   ├── MemberService.java
    │   │       │   └── dto
    │   │       │       ├── IdCheckResDto.java
    │   │       │       ├── MemberDto.java
    │   │       │       ├── ReplyDto.java
    │   │       │       ├── SearchDto.java
    │   │       │       └── SignupResDto.java
    │   │       ├── notice
    │   │       │   ├── NoticeController.java
    │   │       │   ├── NoticeMapper.java
    │   │       │   ├── NoticeService.java
    │   │       │   └── dto
    │   │       │       ├── NoticeDto.java
    │   │       │       ├── ReplyDto.java
    │   │       │       └── SearchDto.java
    │   │       ├── pergola
    │   │       │   └── dto
    │   │       │       ├── PergolaDto.java
    │   │       │       ├── ReplyDto.java
    │   │       │       └── SearchDto.java
    │   │       ├── shop
    │   │       │   ├── cart
    │   │       │   │   ├── CartMapper.java
    │   │       │   │   ├── CartService.java
    │   │       │   │   └── dto
    │   │       │   └── product
    │   │       │       ├── CartController.java
    │   │       │       ├── ProductController.java
    │   │       │       ├── ProductMapper.java
    │   │       │       ├── ProductModel.java
    │   │       │       ├── ProductService.java
    │   │       │       └── dto
    │   │       └── util
    │   │           ├── MsgDto.java
    │   │           ├── Page.java
    │   │           ├── SessionUtil.java
    │   │           └── StringUtil.java
    │   └── resources
    │       ├── application.properties
    │       ├── mapper
    │       │   ├── AdminMapper.xml
    │       │   ├── ComplainMapper.xml
    │       │   ├── ExchangeStopMapper.xml
    │       │   ├── FixMapper.xml
    │       │   ├── MemberMapper.xml
    │       │   ├── NoticeMapper.xml
    │       │   ├── PergolaMapper.xml
    │       │   └── ProductMapper.xml
    │       ├── static
    │       │   ├── resources
    │       │   │   ├── css
    │       │   │   │   ├── 10-11.css
    │       │   │   │   ├── all.min.css
    │       │   │   │   ├── list.css
    │       │   │   │   ├── product-result01.css
    │       │   │   │   ├── productmenu.css
    │       │   │   │   ├── read.css
    │       │   │   │   ├── reg.css
    │       │   │   │   ├── sb-admin-2.min.css
    │       │   │   │   ├── slide.css
    │       │   │   │   ├── spring.css
    │       │   │   │   ├── style.css
    │       │   │   │   └── totalproduct.css
    │       │   │   ├── img
    │       │   │   │   ├── 652-400.jpg
    │       │   │   │   ├── ADB-109-300.jpg
    │       │   │   │   ├── ADB-109.jpg
    │       │   │   │   ├── ADB-120-300.jpg
    │       │   │   │   ├── ADB-사슴벌레-300.jpg
    │       │   │   │   ├── ADSB-04-300.jpg
    │       │   │   │   ├── ADSB-05-300.jpg
    │       │   │   │   ├── ADSB-06-300.jpg
    │       │   │   │   ├── Adsb-09-300.jpg
    │       │   │   │   ├── Arc_400-3.jpg
    │       │   │   │   ├── Arc_400.jpg
    │       │   │   │   ├── Arc_fence400-1.jpg
    │       │   │   │   ├── Arc_fence400-2.jpg
    │       │   │   │   ├── GL-720-400.jpg
    │       │   │   │   ├── absb-09-300.jpg
    │       │   │   │   ├── gl-719b-400.jpg
    │       │   │   │   ├── gl-825b-400.jpg
    │       │   │   │   └── gpl-901-400.jpg
    │       │   │   ├── js
    │       │   │   │   ├── 10-11.js
    │       │   │   │   ├── 10-12.js
    │       │   │   │   ├── basket.js
    │       │   │   │   ├── cell.js
    │       │   │   │   ├── mainimage.js
    │       │   │   │   └── slidestyle.js
    │       │   │   ├── menu
    │       │   │   │   ├── Del.jpg
    │       │   │   │   ├── Title.png
    │       │   │   │   ├── background.jpg
    │       │   │   │   ├── chat_FILL0_wght400_GRAD0_gray.png
    │       │   │   │   ├── construction_FILL0_wght400_opsz48_gray.png
    │       │   │   │   ├── fiber_new_FILL0_wght400_GRAD0_opsz48-1.png
    │       │   │   │   ├── fix.jpg
    │       │   │   │   ├── mainpicture.css
    │       │   │   │   └── temp_preferences_custom_FILL0_opsz48_gray.png
    │       │   │   ├── pergola
    │       │   │   │   ├── ADS-116-1.jpg
    │       │   │   │   ├── ADSB-05-2020.jpg
    │       │   │   │   ├── ADSB-06-(2).jpg
    │       │   │   │   ├── ADSB-09(설명).jpg
    │       │   │   │   ├── adsb-07-001.jpg
    │       │   │   │   ├── adsb-07-004.jpg
    │       │   │   │   └── adsb-09-200.jpg
    │       │   │   ├── play
    │       │   │   │   ├── AP-40-1.jpg
    │       │   │   │   ├── AP-40-2.jpg
    │       │   │   │   ├── AP-53(하베)1.jpg
    │       │   │   │   ├── AP-75(주노).jpg
    │       │   │   │   └── AP-92(노보)1.jpg
    │       │   │   ├── spring
    │       │   │   │   ├── Autumn Day.jpg
    │       │   │   │   ├── SAM_0098.JPG
    │       │   │   │   ├── SAM_0100.JPG
    │       │   │   │   ├── SAM_0107.JPG
    │       │   │   │   ├── SAM_0109.JPG
    │       │   │   │   ├── SAM_0136.JPG
    │       │   │   │   ├── Summer.jpg
    │       │   │   │   ├── spring.jpg
    │       │   │   │   └── spring01.jpg
    │       │   │   └── summer
    │       │   │       ├── SAM_0122.JPG
    │       │   │       ├── SAM_0181.JPG
    │       │   │       ├── SAM_0190.JPG
    │       │   │       ├── SAM_0206.JPG
    │       │   │       ├── SAM_0219.JPG
    │       │   │       └── SAM_0257.JPG
    │       │   └── source
    │       │       ├── bg0.jpg
    │       │       ├── bg1.jpg
    │       │       ├── bg2.jpg
    │       │       ├── bg3.jpg
    │       │       ├── bg5.jpg
    │       │       ├── cup-1.jpg
    │       │       ├── cup-2.jpg
    │       │       ├── cup-3.jpg
    │       │       ├── cup-4.jpg
    │       │       ├── cup-5.jpg
    │       │       ├── cup-6.jpg
    │       │       ├── cup-7.jpg
    │       │       ├── cup-8.jpg
    │       │       ├── flower.jpg
    │       │       ├── prod1.jpg
    │       │       ├── prod2.jpg
    │       │       └── prod3.jpg
    │       └── templates
    │           ├── admin
    │           │   ├── adminlogin.html
    │           │   └── index.html
    │           ├── cart
    │           │   └── index.html
    │           ├── category
    │           │   ├── productlist.html
    │           │   ├── totalgallery.html
    │           │   ├── totalnotice.html
    │           │   └── totalproduct.html
    │           ├── complain
    │           │   ├── complainModify.html
    │           │   ├── complainModifyForm.html
    │           │   ├── complainRead.html
    │           │   ├── complainReg.html
    │           │   ├── complainSaveProc.html
    │           │   └── complainUpdateProc.html
    │           ├── complain.html
    │           ├── exchangestop
    │           │   ├── exchangestopModify.html
    │           │   ├── exchangestopModifyForm.html
    │           │   ├── exchangestopRead.html
    │           │   ├── exchangestopReg.html
    │           │   ├── exchangestopSaveProc.html
    │           │   ├── exchangestopUpdateProc.html
    │           │   └── exupload.html
    │           ├── exchangestop.html
    │           ├── fix
    │           │   ├── fixModify.html
    │           │   ├── fixModifyForm.html
    │           │   ├── fixRead.html
    │           │   ├── fixReg.html
    │           │   ├── fixSaveProc.html
    │           │   ├── fixUpdateProc.html
    │           │   └── upload.html
    │           ├── fix.html
    │           ├── fragments
    │           │   ├── QuestionAnswer.html
    │           │   ├── footer.html
    │           │   ├── meta.html
    │           │   ├── modal.html
    │           │   ├── script.html
    │           │   └── style.html
    │           ├── gallery
    │           │   ├── spring.html
    │           │   └── summer.html
    │           ├── member
    │           │   ├── index.html
    │           │   ├── login.html
    │           │   ├── memberModify.html
    │           │   ├── memberModifyForm.html
    │           │   ├── memberRead.html
    │           │   ├── memberUpdateProc.html
    │           │   └── signup.html
    │           ├── member.html
    │           ├── notice
    │           │   ├── noticeModify.html
    │           │   ├── noticeModifyForm.html
    │           │   ├── noticeRead.html
    │           │   ├── noticeReg.html
    │           │   ├── noticeSaveProc.html
    │           │   └── noticeUpdateProc.html
    │           ├── notice.html
    │           ├── pergola
    │           │   └── pergola.html
    │           ├── product
    │           │   ├── best.html
    │           │   ├── box.html
    │           │   ├── chair.html
    │           │   ├── index.html
    │           │   ├── new.html
    │           │   ├── playground.html
    │           │   └── tree.html
    │           ├── review
    │           │   ├── 10-11.html
    │           │   ├── basket.html
    │           │   └── reviewSaveProc.html
    │           ├── review.html
    │           └── shop
    │               ├── cart
    │               │   └── cartList.html
    │               ├── cart1.html
    │               ├── index.html
    │               ├── product
    │               │   ├── Page.html
    │               │   ├── cartDisplay.html
    │               │   ├── cartDisplayshow.html
    │               │   ├── productDetail.html
    │               │   └── productList.html
    │               └── sum.html
    └── test
        └── java
            └── com
                └── co
                    └── kr
                        └── Portfolio07ApplicationTests.java
