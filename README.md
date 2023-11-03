# application

The `application` project is a library that can be used as the solution by creating a business abstraction or
may extend an existed implementation to provide such software solutions as: CRUD services, REST, DI.

Architecture
This is a high-level abstraction based on the low-level API. It has been written without frameworks and delivered with one dependency:

&#8658; JSON simple (https://cliftonlabs.github.io/json-simple/) <br/>
&#8658; Reflections (https://github.com/ronmamo/reflections) <br/>

## Configuration and Usage

```java
@Configuration
public class BDConfiguration {
    @ComponentFactory
    public DataSource getDataSource() {
        final HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setMaximumPoolSize(Runtime.getRuntime().availableProcessors() * 2 + 1);
        dataSource.addDataSourceProperty("user", user);
        dataSource.addDataSourceProperty("password", password);
        return dataSource;
    }
}

@Configuration
public class ServerConfiguration {
    @ComponentFactory
    public ServerConfig getServerConfig() {
        return new com.spg.applicationTask.engine.web.ServerConfig.Builder()
                .property(Property.HOST, host)
                .property(Property.PORT, port)
                .build();
    }
}
```

### REST Usage

The REST API is available via the `http` protocol:

**`POST localhost:8000/user`** - return list of created or updated users. <br/>
**`GET localhost:8000/user?id=5`** - returns a user by id. <br/>
**`GET localhost:8000/user`** - returns a list of users <br/>
**`DELETE localhost:8000/user?id=5`** - returns an empty body and 204 status <br/>

**`POST localhost:8000/project`** - return list of created or updated projects. <br/>
**`GET localhost:8000/project?id=5`** - returns a project by id. <br/>
**`GET localhost:8000/project`** - returns a list of projects <br/>
**`DELETE localhost:8000/project?id=5`** - returns an empty body and 204 status <br/>

**`POST localhost:8000/task`** - return list of created or updated tasks. <br/>
**`DELETE localhost:8000/task?id=5`** - returns an empty body and 204 status <br/>


### DATA BASE

![app.png](app.png)