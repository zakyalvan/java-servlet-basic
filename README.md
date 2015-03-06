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

####*Deployment Descriptor*

####`javax.servlet.ServletContext`

####`javax.servlet.ServletContextListener`
Interface ini merupakan kontrak untuk yang harus diimplemantasi oleh developer untuk memperoleh notifikasi untuk dua jenis event terkait siklus hidup dari `javax.servlet.ServletContext` yaitu event inisialiasi dan event destruksi. Masing event di handle oleh method `ServletContextListener.contextInitialized(ServletContextEvent)` dan `ServletContextListener.contextDestroyed(ServletContextEvent)`. Method pertama akan dipanggil oleh *servlet container* pada saat inisiasi sebuat aplikasi web, setelah `javax.servlet.ServletContext` diinisiasi dan sebelum komponen lain yaitu `javax.servlet.Filter` dan `javax.servlet.Servlet` di inisiasi sementara method kedua sebaliknya, dipanggil setelah seluruh komponen tersebut didestruksi. 

Hal penting dari informasi di atas adalah developer dapat menyiapkan seluruh komponen yang kemudian akan dapat digunakan bersama oleh komponen lain (`Filter` dan `Servlet`) pada saat `ServletContext` sudah diinisiasi (komponen-komponen tersebut didaftarkan sebagai atribut-atribut dari `ServletContext`). Selain itu developer dapat melakukan tugas-tugas *housekeeping*, menutup dan 'membersihkan' resource sebelum `ServletContext` didestruksi.

```java
package javax.servlet;

import java.util.EventListener;

public interface ServletContextListener extends EventListener {
	public void contextInitialized(ServletContextEvent sce);
	public void contextDestroyed(ServletContextEvent sce);
}
```

Seluruh implementasi `javax.servlet.ServletContextListener` harus di daftarkan dalam *deployment descriptor* (`web.xml`), agar dapat menerima notifikasi tersebut, dan masingmasing method dipanggil berdasarkan urutan dari daftar listener dalam *deployment descriptor*.

####`javax.servlet.Filter`
Interface `javax.servlet.Filter` adalah kontrak dari komponen yang harus diimplementasi oleh developer untuk dapat mem-filter baik *request* yang diterima dan/atau *response* yang akan diberikan, sebelum resource lain dalam kontainer dipanggil. Berikut ini adalah interface tersebut

```java
package javax.servlet;

import java.io.IOException;

public interface Filter {
	public void init(FilterConfig filterConfig) throws ServletException;
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException;
	public void destroy();
}
```

Method `Filter.init(filterConfig)` dan `Filter.destroy()` adalah dua method yang akan dipanggil masing-masing hanya satu kali oleh *servlet container* dalam siklus hidup setiap object `Filter`, sesuai dengan namanya, method ini akan dipanggil setelah object filter dibuat dan `destroy` pada saat object `Filter` akan didestruksi. Parameter type `javax.servlet.FilterConfig` yang diterima oleh method `init` membungkus (*encapsule*) object `javax.servlet.ServletContext` serta nama `Filter` dan parameter-parameter inisiasi `Filter` (jika ada) yang diberikan dalam *deployment descriptor*.

Seluruh implementasi `javax.servlet.Filter` harus didaftarkan dalam *deployment descriptor* sebelum digunakan. Urutan inisiasi dan destruksi `Filter` tersebut berdasarkan urutan daftar dalam *deployment descriptor*.

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