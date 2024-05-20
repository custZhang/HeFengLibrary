### 项目体验
上线地址：[https://library.zsp2024.cn](https://library.zsp2024.cn)，点击右上角登录后，使用默认的账号密码登录后，即可使用所有功能。
![image.png](https://cdn.nlark.com/yuque/0/2024/png/43145378/1716215227262-01a53009-96c4-4bb8-9d7c-2cb211aaae2a.png#averageHue=%23fefdfd&clientId=u799b9a95-36ba-4&from=paste&height=739&id=uab707bdf&originHeight=1109&originWidth=2240&originalType=binary&ratio=1.5&rotation=0&showTitle=false&size=139192&status=done&style=none&taskId=uae63b3f4-94cc-4102-b0d2-1fe7f31e4f8&title=&width=1493.3333333333333)
### 项目介绍
该项目是“图书馆管理系统”，管理员可以添加、修改、下架图书，普通用户可以借阅、归还图书。
该仓库代码只是后端代码，技术选型SpringBoot、MyBatis-Plus、MySQL，**需要配合前端代码使用：**[**https://github.com/custZhang/HeFengLibrary-frontend**](https://github.com/custZhang/HeFengLibrary-frontend)
以下是完整项目展示：

1. 主页/浏览图书页

![image.png](https://cdn.nlark.com/yuque/0/2024/png/43145378/1716212581029-3649e5b8-d7e5-494e-952f-98d6306c6d5a.png#averageHue=%23fdfdfc&clientId=u3ebafe45-fa4c-4&from=paste&height=892&id=u108f55a6&originHeight=1338&originWidth=2238&originalType=binary&ratio=1.5&rotation=0&showTitle=false&size=156695&status=done&style=none&taskId=uf9867a5f-4cde-4a83-a057-712d1176d56&title=&width=1492)

2. 借阅记录页

![image.png](https://cdn.nlark.com/yuque/0/2024/png/43145378/1716212646629-3db32ef2-fba0-40b6-873a-9ffd90bbe035.png#averageHue=%23fdfcfc&clientId=u3ebafe45-fa4c-4&from=paste&height=892&id=u2dcb7ea3&originHeight=1338&originWidth=2238&originalType=binary&ratio=1.5&rotation=0&showTitle=false&size=172923&status=done&style=none&taskId=u38d7ab1b-b9cf-423f-9151-01ca1769d31&title=&width=1492)

3. 修改图书页

![image.png](https://cdn.nlark.com/yuque/0/2024/png/43145378/1716212875992-b2f5f651-9860-4400-ba58-a6d822bce3fb.png#averageHue=%23fdfdfd&clientId=u3ebafe45-fa4c-4&from=paste&height=892&id=ub2c1bbe9&originHeight=1338&originWidth=2238&originalType=binary&ratio=1.5&rotation=0&showTitle=false&size=120793&status=done&style=none&taskId=u966711f7-0c45-43ff-bec0-fa8a34122d8&title=&width=1492)

4. 添加图书页

![image.png](https://cdn.nlark.com/yuque/0/2024/png/43145378/1716212892133-793a8bf5-f204-452d-b272-916412419d4b.png#averageHue=%23fdfdfd&clientId=u3ebafe45-fa4c-4&from=paste&height=892&id=u42a74211&originHeight=1338&originWidth=2238&originalType=binary&ratio=1.5&rotation=0&showTitle=false&size=117244&status=done&style=none&taskId=u9d6c041c-b81e-4222-89ec-bf3decacf5e&title=&width=1492)
### 本地运行

1. 拉取代码
2. 在本地数据库中，运行该工程下/sql/create_table.sql，创建数据库、数据表。这里准备了一些测试数据，可以顺带插入
```sql
INSERT INTO book (id, title, author, publisher, isbn, publishDate, price, quantity, category, isDelete) VALUES
(10001, '活着', '余华', '作家出版社', '9787506365437', '2005-01-01', 39.80, 100, '小说', 0),
(10002, '人类简史', '尤瓦尔·赫拉利', '中信出版社', '9787508699694', '2013-01-01', 45.00, 80, '人文', 0),
(10003, 'Python编程从入门到实践', 'Eric Matthes', '人民邮电出版社', '9787115478239', '2016-01-01', 89.00, 120, '教育', 0),
(10004, '百年孤独', '加西亚·马尔克斯', '南海出版公司', '9787544258604', '2010-01-01', 49.80, 90, '艺术', 0),
(10005, '幸福之路', '陈鹏', '江西人民出版社', '9787213075937', '2018-01-01', 36.00, 110, '生活', 0),
(10006, '时间简史', '斯蒂芬·霍金', '湖南科学技术出版社', '9787532755471', '2001-01-01', 55.00, 70, '科普', 0),
(10007, '小王子', '安托万·德·圣埃克苏佩里', '人民文学出版社', '9787020026486', '2003-01-01', 29.80, 150, '儿童', 0),
(10008, '哈利·波特与魔法石', 'J.K.罗琳', '人民文学出版社', '9787020033439', '2016-01-01', 45.00, 100, '小说', 0);
```
3. 修改application.yml的spring-datasource中数据库的url、username、password，即可运行
```yaml
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/library
    username: your_username
    password: your_password
```

4. 部署后，访问[http://localhost:8201/api/doc.html](http://localhost:8201/api/doc.html)，访问到该聚合文档，则说明后端部署成功了

![image.png](https://cdn.nlark.com/yuque/0/2024/png/43145378/1716213868749-e571f2ed-40c4-446a-bc25-33185e295ef0.png#averageHue=%23fdfdfd&clientId=u652fa99b-3fda-4&from=paste&height=615&id=u41214de6&originHeight=922&originWidth=2230&originalType=binary&ratio=1.5&rotation=0&showTitle=false&size=94443&status=done&style=none&taskId=ufd5a30cd-6a38-404c-b61f-fc9b657a675&title=&width=1486.6666666666667)

5. 访问前端仓库[https://github.com/custZhang/HeFengLibrary-frontend](https://github.com/custZhang/HeFengLibrary-frontend)的readme.md，部署前端项目
