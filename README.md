Aircraft Allocation App
====

SCM
---

Github is used for code management. The repository can be found here:
https://github.com/sherdt/reservation.git


Continuous Integration
---

As build server the free project travis-ci is used:
https://travis-ci.org/sherdt/reservation

Issu tracking
---

Issues are tracked in github:
https://github.com/sherdt/reservation/issues

IDE
---
Eclipse IDE for Java EE Developers (4.3. - Kepler Service Release 1 -  20130919-0819)
**Plugins**
 - eGit (3.2.0.201312181205-r): http://download.eclipse.org/egit/updates
 - m2e (1.4.20130601-0317) integrated into EE package
 - EclEmma (2.3.0.201403192240) http://update.eclemma.org/

Encoding
---

File encoding: `UTF-8`, 
Line delimiter: `Unix`

Formating
---

Code formatter: codeformatter.xml
Code templates:  codetemplates.xml
Cleanup: cleanup.xml

Naming conventions
---

**Eclipse project names convention**

| Name                  | Comment                                                                           |
|-----------------------|-----------------------------------------------------------------------------------|
| aaa                   | pom projects as parent for all projects                                           |
| aaa-common            | common code: utilities, constants, exceptions, ...                                |
| aaa-*-client          | Entities, service interfaces                                                      |
| aaa-*-service         | EJB's, Service implementation                                                     |
| aaa-web               | REST services and web-client                                                      |
| aaa-integration-test  | All Arquillian integration tests should be implemented in this project            |
| aaa-distribution      | POM project creating the zip containing the deployable, release notes, schema ... |

**DB Tables**
All tables names must use lowercase letters only with underscores for concatenation (e.g. aircraft_type, reservation_status, ...)
Every table has to start with the prefix `aaa_`


Project structure and build
---

Maven is used to build the project. Default maven folder structure is used for each archetype accordingly:

**jar / ejb**:

        ├── pom.xml
        ├── src
        │   ├── main
        │   │   ├── java
        │   │   │   └── com
        │   │   │       └── prodyna
        │   │   │           └── pac
        │   │   │               └── aaa
        │   │   └── resources
        │   └── test
        │       ├── java
        │       │   └── com
        │       │       └── prodyna
        │       │           └── pac
        │       │               └── aaa
        │       └── resources
        └── target
        
**ear**:

        ├── pom.xml
        ├── src
        │   └── main
        │       ├── application
        │       │   └── META-INF
        │       │       └── persistence.xml
        │       └── resources
        └── target
        
**war**:

    	├── pom.xml
    	├── src
    	│   ├── main
    	│   │   ├── java
    	│   │   │   └── com
    	│   │   │       └── prodyna
    	│   │   │           └── pac
    	│   │   │               └── aaa
    	│   │   │                   └── rest
    	│   │   │                       ├── RESTActivator.java
    	│   │   │                       └── VersionRESTService.java
    	│   │   ├── resources
    	│   │   └── webapp
    	│   │       ├── index.html
    	│   │       ├── css
    	│   │       ├── img
    	│   │       ├── js
    	│   │       ├── views
    	│   │       └── WEB-INF
    	│   │           ├── beans.xml
    	│   │           └── web.xml
    	│   └── test
    	│       ├── java
    	│       └── resources
    	└── target
		

Modeling
---

Topcased 5.3.1

Technologies (JavaEE7)
---

**Frontend**
 - Angular.js 1.2.18

**Backend**
 - EJB 3.2
 - CDI 1.1
 - JAX-RS 2.0
 - JPA 2.1
 - JMX 2.0
 - slf4j 1.7.2

**Testing**
 - JUnit 4.11
 - Power Mock 1.5.4
 - EasyMock 3.2
 - Equalsverifier 1.4.1
 - Arquillian 1.1.3.Final (for integration tests)
 - JaCoCo (for code coverage)


Development environment
---

**Application server**:	wildfly-8.0.0.Final, 
**Database**:	MySQL

The application uses the following ***persistence.xml*** definition, which can be found in folder `aaa/aaa-ear/src/main/application/META-INF`:

    <?xml version="1.0" encoding="UTF-8"?>
    <persistence version="2.1"
    	xmlns="http://xmlns.jcp.org/xml/ns/persistence" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    	xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence http://xmlns.jcp.org/xml/ns/persistence/persistence_2_1.xsd">
    	<persistence-unit name="aaaPU">
    		<jta-data-source>java:/MysqlDS</jta-data-source>
    		<properties>
    			<property name="hibernate.hbm2ddl.auto" value="create" />
    		</properties>
    	</persistence-unit>
    </persistence>

In the ***standanlone.xml*** file you have to provide the datasource information. The important part is the jndi-name matching. Here is an example:

    <subsystem xmlns="urn:jboss:domain:datasources:2.0">
        <datasources>
                ...
                <datasource jndi-name="java:/MysqlDS" pool-name="MysqlDS" enabled="true">
                    <connection-url>jdbc:mysql://localhost:3306/pac</connection-url>
                    <driver>mysql</driver>
                    <pool>
                        <max-pool-size>30</max-pool-size>
                    </pool>
                    <security>
                        <user-name>pac_wildfly</user-name>
                        <password>*******</password>
                    </security>
                </datasource>
                ...
        </datasources>
    </subsystem>

Using the above ***persistence.xml*** generates the DB schema during deployment of the artifact. If you prefer to setup your DB manually remove the follwong part:

    <properties>
    	<property name="hibernate.hbm2ddl.auto" value="create" />
    </properties>

and use the provided schema creation script `aaa/aaa-distribution/sql/schema/aaa-mysql-schema.sql`.

Tests in directory `aaa/aaa-integration-tests` are all JUnit integration tests and are executed with Arquillian inside an automatically downloaded wildfly-8.0.0.Final.
If you are going to start the JUnit from Eclipse, you will have to provide the following VM arguments:

    -Dwildfly.home=cargo/extracts/wildfly-8.0.0.Final/wildfly-8.0.0.Final
    -Djava.util.logging.manager=org.jboss.logmanager.LogManager

As you can see the wildfly-8.0.0.Final is going to be downloaded to the directory `cargo/extracts/wildfly-8.0.0.Final/wildfly-8.0.0.Final`. This is done automatically in phase `clean` if you trigger the maven build. It means, you have to execute maven at least once to get the wildfly downloaded.

Release handling
---

**Version numbers**

|                 | Major       | Minor                        | Bugfix                            |
------------------|-------------|------------------------------|-----------------------------------|
| Description     | API changes | features without API changes | only bugfixes without API changes |
| Example: 1.3.7  | 1           | 3                            | 7                                 |
 
Tags for releases are named in the following format: r&lt;MAJOR&gt;&lt;MAJOR&gt;&lt;MINOR&gt;&lt;MINOR&gt;&lt;BUGFIX&gt;&lt;BUGFIX&gt; e.g. r010307 for version 1.3.7

Each features has to be implemented on a separate branch once the first version is released. The feature branch must not be merged to the trunk as long as the feature is not finished and tested by QA. The name of the branch should be **b&lt;ISSUE_NR&gt;** from https://github.com/sherdt/reservation/issues e.g. b12 for issue number 12.
Feature branches will be used as soon as first version is tagged and released.


Deployment artifact
---

Content of the resulting ear file `aaa-ear-1.0.0-SNAPSHOT.ear`:

    ├── aaa-authentication-service-1.0.0-SNAPSHOT.jar
    ├── aaa-common-service-1.0.0-SNAPSHOT.jar
    ├── aaa-pilot-service-1.0.0-SNAPSHOT.jar
    ├── aaa-reservation-service-1.0.0-SNAPSHOT.jar
    ├── aaa-web-1.0.0-SNAPSHOT.war
    ├── lib
    │   ├── aaa-aircraft-client-1.0.0-SNAPSHOT.jar
    │   ├── aaa-authentication-client-1.0.0-SNAPSHOT.jar
    │   ├── aaa-common-client-1.0.0-SNAPSHOT.jar
    │   ├── aaa-pilot-client-1.0.0-SNAPSHOT.jar
    │   ├── aaa-reservation-client-1.0.0-SNAPSHOT.jar
    │   ├── activation-1.1.1.jar
    │   ├── bcprov-jdk15on-1.49.jar
    │   ├── commons-codec-1.9.jar
    │   ├── jcip-annotations-1.0.jar
    │   ├── json-smart-1.1.1.jar
    │   ├── mail-1.4.7.jar
    │   └── nimbus-jose-jwt-2.22.1.jar
    └── META-INF
        ├── application.xml
        ├── MANIFEST.MF
        ├── maven
        │   └── com.prodyna.pac.reservation
        │       └── aaa-ear
        │           ├── pom.properties
        │           └── pom.xml
        └── persistence.xml
        
Once the deployment is perfomed, the application is accessible at (if you use the default wildfly configuration, otherwise you have to adjust the port):

    http://localhost:8080/aaa-web
    
Creadentials for administrator:

    Username: admin
    Password: Test123!

Administrator is allowed to create pilots. The default password for created pilots is: `changeit`


