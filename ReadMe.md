# Message over socket POW application 

Packaging
```sh
mvn clean package
```

Execution Client

```sh
java -jar ./byte-thrower-client/target/byte-thrower-client-1.0-SNAPSHOT.jar [options]
```
where options:
```sh
#ip adress or DNS name of target server
--host=127.0.0.1
#host port
--port=40001
```

Execution Server

```sh
java -jar ./byte-thrower-server/target/byte-thrower-server-1.0-SNAPSHOT [options]
```

where options:
```sh
#listening port
--port=40001
#property file contain map of allocated services
--service.prop.file=server.properties
```


