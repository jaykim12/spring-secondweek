|기능|URL|METHOD|Request|Response|
|------|---|---|---|---|
|회원가입|/api/user/signup|POST|{"username":String, "password":String}|{"msg":String, "status":HttpStatus}|
|로그인|/api/user/login|POST|{"username":String, "password":String}|header:{"Authorization":"Bearer JWT"}, body:{"msg":String, "status":HttpStatus}|
|게시글 작성|/api/memos|POST|header:{"Authorization":"Bearer JWT"}, body:{"title":String, "content":String}|{"title":String, "name":String, "content":String, "modifiedAt":String}|
|게시글 목록 조회|/api/memos|GET||[{"title":String, "name":String, "content":String, "modifiedAt":String},...]|
|게시글 상세 조회|/api/memos/{id}|GET||{"title":String, "name":String, "content":String, "modifiedAt":String}|
|게시글 수정|/api/memos/{id}|PUT|header:{"Authorization":"Bearer JWT"}, body:{"title":String, "content":String}|{"title":String, "name":String, "content":String, "modifiedAt":String}|
|게시글 삭제|/api/memos/{id}|DELETE|header:{"Authorization":"Bearer JWT"}|{"msg":String, "status":HttpStatus}|
