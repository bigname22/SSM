# Maven_SSM
maven整合Spring+SpringMVC+Mybatis

   啊哈，终于到了用Maven整合SSM这个扑街含家产了。弄了整整一天才跑通。Mybatis的配置有些繁琐，跟之前学习的那个有点出入，加上Eclipse的Spring工具没有弄，配置的时候没有提示被搞蒙圈了。不过万幸，最终还是凭借我高超的智慧完成了！！哈哈哈

[csdn地址](http://blog.csdn.net/bigname22/article/details/79380238)
[掘金地址](https://juejin.im/post/5a93db9f6fb9a0634912c2a3)

路过的朋友帮忙点个star☆  ^_^

#### 一：准备材料
Eclipse+Maven+jdk+Tomcat，安装不多说了。

#### 二：Eclipse新建Maven项目
File->New->MavenProject->maven-archetype-webapp

```
Group Id: com.bigname
Artifacrt Id: MavenDemo01
```
#### 三：构建目录结构
创建自己喜欢的目录结构，体现架构思想
![这里写图片描述](https://user-gold-cdn.xitu.io/2018/2/26/161d191e3228df05?w=501&h=443&f=png&s=99807)

#### 四：添加依赖
在pom.xml声明依赖,利用该网站查找配置写法(http://mvnrepository.com/)
##### 1：依赖SpringMVC
a.声明依赖，此时jar包会自动下载
```
<dependency>
	<groupId>org.springframework</groupId>
	<artifactId>spring-webmvc</artifactId>
	<version>4.3.14.RELEASE</version>
</dependency>
```
b.创建配置文件：
在resource下创建spring-mvc.xml,内容如下

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:p="http://www.springframework.org/schema/p"
	xmlns:mvc="http://www.springframework.org/schema/mvc" xmlns:context="http://www.springframework.org/schema/context"
	xmlns:util="http://www.springframework.org/schema/util"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd  
            http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-3.0.xsd  
            http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc-3.0.xsd  
            http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util-3.0.xsd">

	<!-- 开启注解 -->
	<mvc:annotation-driven />
	
	<!-- 让扫描spring扫描这个包下所有的类，让标注spring注解的类生效 -->
	<context:component-scan base-package="com.bigname.demo03.controller"></context:component-scan>
	
	<!-- 视图解析器 -->
	<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
		<property name="prefix" value="/views/" />
		<property name="suffix" value=".jsp"></property>
	</bean>
</beans>
```
c.在web.xml中添加配置
```
 <!-- 定义前端控制器 -->
  <servlet>
  	<servlet-name>spring-mvc</servlet-name>
  	<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
  	<!-- 指定路径 -->
  	<init-param>
  		<param-name>contextConfigLocation</param-name>
  		<param-value>classpath:spring-mvc.xml</param-value>
  	</init-param>
  	<!-- 随spring启动而启动 -->
  	<load-on-startup>1</load-on-startup>
  </servlet>
  
  <servlet-mapping>
  	<servlet-name>spring-mvc</servlet-name>
  	<url-pattern>*.do</url-pattern>
  </servlet-mapping>
```
这个时候可以新建一个controller来检验一下请求是否能走通，成功了再执行下一步。
##### 2：依赖spring
由于在依赖springmvc的时候已经添加了许多spring相关包了，所以此时不需要添加额外的包，可以直接创建配置文件了。

a.创建配置文件spring-context.xml
内容啥的暂时不用写
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:p="http://www.springframework.org/schema/p"
	xmlns:mvc="http://www.springframework.org/schema/mvc" xmlns:context="http://www.springframework.org/schema/context"
	xmlns:util="http://www.springframework.org/schema/util"
	xmlns:aop="http://www.springframework.org/schema/aop"
	xmlns:tx="http://www.springframework.org/schema/tx"
	xsi:schemaLocation=
			"
			http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd  
            http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-3.0.xsd  
            http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc-3.0.xsd  
            http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util-3.0.xsd
            http://www.springframework.org/schema/tx 
            http://www.springframework.org/schema/tx/spring-tx.xsd 
            http://www.springframework.org/schema/aop 
            http://www.springframework.org/schema/aop/spring-aop.xsd
            "
            > 

</beans>  
```
b.在web.xml中配置spring

```
<!-- 配置适配器spring -->
  <listener>
  	<description>启动spring容器</description>
  	<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
  </listener>
  
  <context-param>
  	<param-name>contextConfigLocation</param-name>
  	<param-value>classpath:spring-context.xml</param-value>
  </context-param>  
```
此时spring已经配置完成

##### 3.依赖c3p0
需要依赖jar包

- c3p0
- jdbc-mysql

a.在pom.xml中添加依赖声明

```
<!-- c3p0 数据库连接池 -->
		<dependency>
			<groupId>com.mchange</groupId>
			<artifactId>c3p0</artifactId>
			<version>0.9.5.2</version>
		</dependency>

		<!-- 数据库 -->
		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
			<version>6.0.6</version>
		</dependency>
```

b.在spring-context.xml配置c3p0

```
<!-- 配置c3p0 -->
	<!-- 连接池 -->
	<bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
		<property name="driverClass" value="com.mysql.jdbc.Driver"></property>
		<property name="jdbcUrl" value="jdbc:mysql://localhost/test?characterEncoding=utf8&amp;serverTimezone=UTC"></property>
		<property name="user" value="root"></property>
		<property name="password" value="root"></property>
		<property name="minPoolSize" value="1"></property>
		<property name="maxPoolSize" value="5"></property>
		<property name="initialPoolSize" value="1"></property>
		<property name="acquireIncrement" value="1"></property>
	</bean>
```
##### 4.配置spring声明式事务管理
需要依赖jar包

- spring-tx
- spring-jdbc

a.在pom.xml中生命依赖
```
<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-tx</artifactId>
			<version>4.3.14.RELEASE</version>
		</dependency>

		
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-jdbc</artifactId>
			<version>4.3.14.RELEASE</version>
		</dependency>
```

b.在spring-context.xml配置
如果tx这些爆红，则需要检查该文件头部信息是否完整。可以往上翻查看spring-context.xml
```
<!-- 配置事务管理器 -->
	<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
		<property name="dataSource" ref="dataSource"></property>
	</bean>
	
	<!-- 使用注解来控制事务 -->
	<tx:annotation-driven transaction-manager="transactionManager"></tx:annotation-driven>
```

##### 5:依赖Mybatis
这里共依赖四个jar包

- mybatis、
- mybatis-spring整合
- pagehelper分页、
- cglib代理

a.在pom.xml中添加依赖声明

```
		<dependency>
			<groupId>org.mybatis</groupId>
			<artifactId>mybatis</artifactId>
			<version>3.4.1</version>
		</dependency>

		<dependency>
			<groupId>org.mybatis</groupId>
			<artifactId>mybatis-spring</artifactId>
			<version>1.3.0</version>
		</dependency>

		<!--  分页助手 -->
		<dependency>
			<groupId>com.github.pagehelper</groupId>
			<artifactId>pagehelper</artifactId>
			<version>5.1.2</version>
		</dependency>
		
		<!-- 代理 -->
		<dependency>
			<groupId>cglib</groupId>
			<artifactId>cglib</artifactId>
			<version>3.2.2</version>
		</dependency>
```
b.新建mybatis配置文件mybatis-config.xml
```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
  PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>

	<settings>
		<setting name="cacheEnabled" value="true"/>
		<setting name="defaultStatementTimeout" value="3000"/>
		<setting name="mapUnderscoreToCamelCase" value="true"/>
		<!-- 代理 -->
		<setting name="proxyFactory" value="CGLIB"/>
		<setting name="lazyLoadingEnabled" value="true"/>
	</settings>
	
	<!-- 分页插件 -->
	<plugins>
		<plugin interceptor="com.github.pagehelper.PageInterceptor">
			<!-- 该参数默认为false
			设置为true时，会将RowBounds第一个参数offset当成pageNum页码使用
			和startPage中的pageNum效果一样 -->
			<property name="offsetAsPageNum" value="true"/>
			<!-- 该参数默认为false
			设置为true是，使用RowBounds分页会进行count查询 -->
			<property name="rowBoundsWithCount" value="true"/>
			<!-- 设置为true时，如果pageSize=0或者ROwRounds.limit=0就会查询出全部的结果
			（相当于每一偶执行分页查询，但是返回结果仍然是page类型） -->
			<property name="pageSizeZero" value="true"/>
		</plugin>
	</plugins>

</configuration>
```
这次曾出现一个问题，在程序运行的时候检验不通过，因为之前按照教程的写法是：
```
		<plugin interceptor="com.github.pagehelper.PageHelper">
		<property name="dialect" value="mysql"/>
```

然后报了类型转换的异常，最后查网上的资料改成现在的样子就成功了。

c.在spring-context.xml中配置mybatis
这里要绑定数据源、指明配置文件位置、mapper位置、扫描dao层
```
<!-- 配置mybatis, 绑定c3p0-->
	<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
		<property name="dataSource" ref="dataSource"></property>
		<property name="configLocation" value="classpath:mybatis-config.xml"></property>
		<property name="mapperLocations">
			<list>
				<value>classpath:mapper/*.xml</value>
			</list>
		</property>
	</bean>
	
	<!-- 扫描生成所有dao层 -->
	<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
		<property name="basePackage" value="com.bigname.demo03.dao"></property>
		<property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"></property>
	</bean>
```


----
----


**至此maven已经成功整合了spring+springmvc+mybatis,接下来整合测试一遍**

1.创建数据库test、表格Member

| id      |    name | password|
| :-------| :-------|    :--  |
| 1       |   梁世杰 |     123 |
| 2       |   刘德华 |     456 |
| 3       |   周润发 |    789  |
| 4       |   shijie|    123  |
2.core层创建实体类
要与数据库字段对应
```
package com.bigname.demo03.core;

public class Member {
	private int id;
	private String name;
	private String password;
	public Member(){}
	public Member(int id, String name, String password) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "Member [id=" + id + ", name=" + name + ", password=" + password
				+ "]";
	}
	
}

```
3.dao层创建数据访问类
a.创建接口
```
public interface MemberDao {
	Member selectMemberByName(@Param("name")String name)throws Exception;
}
```
b.创建MemberDaoMapper.xml
这里出现过一个问题，当传入中文时发生过异常，应为其中的#号之前携程$符号了，另外a中需要增加@Param的注解来说明是属性的意思
```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
  PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.bigname.demo03.dao.MemberDao">
	<select id="selectMemberByName" resultType="com.bigname.demo03.core.Member" parameterType="string">
		select * from Member where name = #{name}
	</select>
</mapper>
```
然后只要得到MemberDao的对象就能够访问数据库啦

4.function创建业务类
a.创建Member业务接口

```
package com.bigname.demo03.function;

@Service
public interface IMemberFunction {
	Member login(String name, String passsword) throws Exception;
}

```

b.创建Member业务接口实现类

```
package com.bigname.demo03.function;

@Service
public class MemberFunctionImpl implements IMemberFunction{
	@Autowired
	MemberDao mDao;
	
	public Member login(String name, String passsword) throws Exception {
		System.out.println(name + passsword);
		if(StringUtil.isNullOrZero(name)){
			System.out.println("用戶名不能為空");
			return null;
		}
		if(StringUtil.isNullOrZero(passsword)){
			System.out.println("密碼不能為空");
			return null;
		}
		Member member = mDao.selectMemberByName(name);
		return member;
	}

}

```

5.创建LoginController,定义接口
```
package com.bigname.demo03.controller;
@Controller
public class LoginController {
	@Autowired
	IMemberFunction iMemberFunc;
	
	@RequestMapping(value = "/hello")
	public String hello(){
		System.out.println("接收到请求 ，Hello");
		return "hi";
	}
	
	@RequestMapping(value = "/login")
	public String login(String name, String password){
		try {
			Member member = iMemberFunc.login(name, password);
			if(member == null){
				System.out.println("登陆失败");
			}else {
				System.out.println("登陆成功");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
			System.out.println("登录失败");
		}
		return null;
	}
}

```
在配置springmvc的时候如果成功了，那么hello.do这个接口就能正常使用的了。

访问：
http://localhost:8201/MavenDemo03/login.do?name=shijie&password=145
如果没问题的话就是成功啦。

----

另外：spring要扫描function层，springmvc扫描controller层，mybatis扫描dao层，需要被扫描的类要增加组件注解，例如@Controller。还有一些细节问题可能会遗漏记录下来，但有事找度娘总能解决de~

到此为止，搭建Maven+Spring+SpringMVC+Mybatis的项目已经搭建完成，虽然很简陋，出现过很多问题，但总算是成功了，可以安心睡个小觉觉 。

坚持不懈，学海无涯。
