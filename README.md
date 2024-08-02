# STAR ID
> 基于静态配置(XML文件)或动态配置(Zookeeper、Redis)的分布式id生成器。



## 名称由来

> 繁星璀璨，各自星河。



## 项目构建

```shell
git clone https://github.com/laohugit/star-id.git
cd star-id
mvn clean install -DskipTests -Dmaven.test.skip=true
```



## 接入示例

#### 项目列表

| 序号 | 接入方式              | 示例项目                              |
| ---- | --------------------- | ------------------------------------- |
| 1    | 基于xml配置方式       | star-id-spring-boot-example-xml       |
| 2    | 基于zookeeper配置方式 | star-id-spring-boot-example-zookeeper |

#### 访问接口

| 选项 | 取值                                                         |      |
| ---- | ------------------------------------------------------------ | ---- |
| 地址 | http://localhost:8081/id                                     |      |
| 响应 | {<br/>     "longValue": 1268834613816459264,<br/>     "stringValue": "1268834613816459265"<br/>} |      |


