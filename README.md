#README

##Tujuan
Tujuan utama dari contoh proyek ini adalah pengenalan dasar dari servlet technology, sebagai pengantar ke bagian berikutnya tentang mebuat aplikasi web menggunakan Spring Framework, jadi penekanan utamanya bukan pada bagaimana menulis komponen-komponen kastem tetapi pemahaman tentang masing-masing komponen serta hubungan satu komponen dengan yang lainnya. Tujuan selanjutnya adalah secara implisit mengenalkan maven dalam membantu *manage* proyek java.

##Prerequisite

1. Java 7+
2. 

##Menjalankan Contoh


##Aplikasi Web berbasis Servlet
Perlu diingat bahwa contoh ini menggunakan deployment descriptor 'tradisional' yaitu menggunakan file `web.xml`, walaupun project ini menggunakan servlet versi 3 sudah mendukung deployment dengan annotation sebagai deployment descriptor. Pada saat Anda paham deployment model menggunakan descriptor `web.xml`, menggunakan annotation tidak akan ada masalah.

###Proyek Maven

Buat proyek maven baru  untuk aplikasi web (dengan jenis `packaging` war), dengan `groupId` dan `artifactId` terserah Anda (dalam contoh ini saya menggunakan masing-masing `com.innovez.samples` dan `basic-servlet`) kemudian edit file `pom.xml` sehingga menjadi seperti berikut ini.

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<groupId>com.innovez.samples</groupId>
	<artifactId>basic-servlet</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>war</packaging>
	<name>Basic Servlet Technology Sample</name>

	<dependencies>
		<dependency>
			<groupId>org.slf4j</groupId>
			<artifactId>slf4j-log4j12</artifactId>
			<version>1.7.10</version>
		</dependency>
		<dependency>
			<groupId>javax.servlet</groupId>
			<artifactId>javax.servlet-api</artifactId>
			<version>3.0.1</version>
			<scope>provided</scope>
		</dependency>
	</dependencies>
	<build>
		<plugins>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-compiler-plugin</artifactId>
				<version>3.2</version>
				<configuration>
					<source>7</source>
					<target>7</target>
					<encoding>UTF-8</encoding>
				</configuration>
			</plugin>
			<plugin>
				<groupId>org.apache.tomcat.maven</groupId>
				<artifactId>tomcat7-maven-plugin</artifactId>
				<version>2.2</version>
				<configuration>
					<path>/</path>
				</configuration>
			</plugin>
			<plugin>
				<groupId>org.eclipse.jetty</groupId>
				<artifactId>jetty-maven-plugin</artifactId>
				<version>9.2.7.v20150116</version>
			</plugin>
		</plugins>
	</build>
</project>
```

###Komponen Penting Teknologi Servlet
Berikut akan dijelaskan sekilas tentang komponen-komponen dalam teknologi servlet. Seperti telah disebutkan semelumnya, contoh proyek sederhana ini sebagai pengantar sebelum memulai pengembangan aplikasi web dengan Spring Framework.

####`javax.servlet.ServletContext`
####`javax.servlet.ServletContextListener`
####`javax.servlet.Filter`
####`javax.servlet.Servlet` dan `javax.servlet.http.HttpServlet`
####`javax.servlet.HttpSession`

###Deployment Descriptor

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns="http://java.sun.com/xml/ns/javaee"
	xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd"
	version="3.0">
	<display-name>basic-servlet</display-name>
	
	<context-param>
		<param-name>contohParameterPertama</param-name>
		<param-value>Nilai dari contoh parameter parameter pertama</param-value>
	</context-param>
	<context-param>
		<param-name>contohParameterKedua</param-name>
		<param-value>Nilai dari contoh parameter parameter kedua</param-value>
	</context-param>
	
	<listener>
		<description>Just a simple dummy servlet context lister, please look at the class for details</description>
		<display-name>sampleDummyListener</display-name>
		<listener-class>com.innovez.sample.context.DummySampleContextListener</listener-class>
	</listener>
	
	<filter>
		<filter-name>simpleDummyFilter</filter-name>
		<filter-class>com.innovez.sample.filter.DummySampleFilter</filter-class>
		<init-param>
			<param-name>blabla</param-name>
			<param-value>blibli</param-value>
		</init-param>
	</filter>
	<filter>
		<filter-name>randomCookieFilter</filter-name>
		<filter-class>com.innovez.sample.filter.GenerateRandomCookieFilter</filter-class>
		<init-param>
			<param-name>enableFilter</param-name>
			<param-value>false</param-value>
		</init-param>
	</filter>
	<filter-mapping>
		<filter-name>simpleDummyFilter</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>
	<filter-mapping>
		<filter-name>randomCookieFilter</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>
	
	<servlet>
		<servlet-name>simpleDummyServlet</servlet-name>
		<servlet-class>com.innovez.sample.servlet.DummySampleServlet</servlet-class>
		<init-param>
			<param-name>initParamPertama</param-name>
			<param-value>Nilai pararmeter inisiasi servlet pertama</param-value>
		</init-param>
		<load-on-startup>1</load-on-startup>
	</servlet>
	<servlet-mapping>
		<servlet-name>simpleDummyServlet</servlet-name>
		<url-pattern>/sample</url-pattern>
	</servlet-mapping>
</web-app>
```

##Sumber